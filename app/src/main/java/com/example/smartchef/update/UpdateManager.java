package com.example.smartchef.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.example.smartchef.utils.UpdateConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class UpdateManager {

    private static final String TAG = "UpdateManager";
    private static UpdateManager instance;

    private UpdateInfo pendingUpdateInfo;

    private UpdateManager() {}

    public static synchronized UpdateManager getInstance() {
        if (instance == null) {
            instance = new UpdateManager();
        }
        return instance;
    }

    /**
     * Asynchronously checks for an app update from the online JSON configuration URL.
     */
    public void checkForUpdate(Context context, OnUpdateCheckListener listener) {
        if (context == null) return;

        Executors.newSingleThreadExecutor().execute(() -> {
            HttpURLConnection conn = null;
            try {
                String targetUrl = UpdateConstants.UPDATE_JSON_URL;
                int redirects = 0;

                while (redirects < 5) {
                    URL url = new URL(targetUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestProperty("User-Agent", "SmartChef-AI/" + getInstalledVersionName(context));
                    conn.setRequestProperty("Accept", "application/json");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
                            || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                            || responseCode == 307
                            || responseCode == 308) {
                        String newUrl = conn.getHeaderField("Location");
                        if (!TextUtils.isEmpty(newUrl)) {
                            targetUrl = newUrl;
                            redirects++;
                            conn.disconnect();
                            continue;
                        }
                    }

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        reader.close();

                        JSONObject json = new JSONObject(builder.toString());
                        int latestVersionCode = json.optInt("versionCode", 1);
                        String latestVersionName = json.optString("versionName", "1.0.0");
                        String apkUrl = json.optString("apkUrl", "");
                        boolean forceUpdate = json.optBoolean("forceUpdate", false);

                        List<String> releaseNotes = new ArrayList<>();
                        JSONArray notesArr = json.optJSONArray("releaseNotes");
                        if (notesArr != null) {
                            for (int i = 0; i < notesArr.length(); i++) {
                                releaseNotes.add(notesArr.getString(i));
                            }
                        }

                        UpdateInfo updateInfo = new UpdateInfo(latestVersionCode, latestVersionName, apkUrl, forceUpdate, releaseNotes);
                        long installedVersionCode = getInstalledVersionCode(context);
                        String installedVersionName = getInstalledVersionName(context);

                        boolean isUpdateAvailable = (latestVersionCode > installedVersionCode)
                                || isVersionNewer(latestVersionName, installedVersionName);

                        new Handler(Looper.getMainLooper()).post(() -> {
                            if (isUpdateAvailable) {
                                if (listener != null) listener.onUpdateAvailable(updateInfo);
                            } else {
                                if (listener != null) listener.onNoUpdateAvailable();
                            }
                        });
                        return;
                    } else {
                        Log.e(TAG, "HTTP response error: " + responseCode);
                        notifyNoUpdate(listener);
                        return;
                    }
                }
                notifyNoUpdate(listener);
            } catch (Exception e) {
                Log.e(TAG, "Failed to check for update", e);
                notifyNoUpdate(listener);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }

    private boolean isVersionNewer(String latest, String current) {
        try {
            String[] latestParts = latest.split("\\.");
            String[] currentParts = current.split("\\.");
            int length = Math.max(latestParts.length, currentParts.length);
            for (int i = 0; i < length; i++) {
                int l = i < latestParts.length ? Integer.parseInt(latestParts[i].replaceAll("[^0-9]", "")) : 0;
                int c = i < currentParts.length ? Integer.parseInt(currentParts[i].replaceAll("[^0-9]", "")) : 0;
                if (l > c) return true;
                if (l < c) return false;
            }
        } catch (Exception ignored) {}
        return false;
    }

    private void notifyNoUpdate(OnUpdateCheckListener listener) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (listener != null) listener.onNoUpdateAvailable();
        });
    }

    public long getInstalledVersionCode(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return pInfo.getLongVersionCode();
            } else {
                return pInfo.versionCode;
            }
        } catch (Exception e) {
            return 1;
        }
    }

    public String getInstalledVersionName(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName != null ? pInfo.versionName : "1.0.0";
        } catch (Exception e) {
            return "1.0.0";
        }
    }

    /**
     * Shows a clean Material Design 3 Update Available dialog.
     */
    public void showUpdateDialog(Activity activity, UpdateInfo updateInfo, Runnable onDismissRunnable) {
        if (activity == null || activity.isFinishing()) return;

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Current Version: v").append(getInstalledVersionName(activity)).append("\n");
        messageBuilder.append("Latest Version: v").append(updateInfo.getVersionName()).append("\n\n");

        if (updateInfo.getReleaseNotes() != null && !updateInfo.getReleaseNotes().isEmpty()) {
            messageBuilder.append("What's New:\n");
            for (String note : updateInfo.getReleaseNotes()) {
                messageBuilder.append("• ").append(note).append("\n");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("🎉 Update Available")
                .setMessage(messageBuilder.toString().trim())
                .setPositiveButton("Update Now", (dialog, which) -> {
                    dialog.dismiss();
                    downloadAndInstallApk(activity, updateInfo);
                    if (onDismissRunnable != null) onDismissRunnable.run();
                });

        if (!updateInfo.isForceUpdate()) {
            builder.setNegativeButton("Later", (dialog, which) -> {
                dialog.dismiss();
                if (onDismissRunnable != null) onDismissRunnable.run();
            });
        }

        AlertDialog dialog = builder.create();
        if (updateInfo.isForceUpdate()) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }

        dialog.show();
    }

    /**
     * Downloads APK directly using HttpURLConnection with redirect support, progress feedback,
     * and guarantees the downloaded file exists in app's external files directory.
     */
    public void downloadAndInstallApk(Activity activity, UpdateInfo updateInfo) {
        if (activity == null || activity.isFinishing()) return;
        if (TextUtils.isEmpty(updateInfo.getApkUrl())) {
            Toast.makeText(activity, "Invalid update download URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check Android 8.0+ Unknown App Sources Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!activity.getPackageManager().canRequestPackageInstalls()) {
                this.pendingUpdateInfo = updateInfo;
                new AlertDialog.Builder(activity)
                        .setTitle("Permission Required")
                        .setMessage("To install updates outside of Google Play Store, please allow SmartChef AI to install unknown apps in Android Settings.")
                        .setPositiveButton("Open Settings", (dialog, which) -> {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                        Uri.parse("package:" + activity.getPackageName()));
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                                activity.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return;
            }
        }

        // Show horizontal Progress Dialog
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Downloading SmartChef AI v" + updateInfo.getVersionName());
        progressDialog.setMessage("Please wait while downloading update...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Executors.newSingleThreadExecutor().execute(() -> {
            HttpURLConnection conn = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                File downloadDir = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                if (downloadDir == null) {
                    downloadDir = activity.getCacheDir();
                }
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs();
                }

                File apkFile = new File(downloadDir, "SmartChef_AI_v" + updateInfo.getVersionName() + ".apk");
                if (apkFile.exists()) {
                    apkFile.delete();
                }

                String currentUrl = updateInfo.getApkUrl();
                int redirects = 0;

                while (redirects < 5) {
                    URL url = new URL(currentUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(12000);
                    conn.setReadTimeout(12000);
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Android; SmartChef-AI)");
                    conn.setRequestProperty("Accept", "*/*");

                    int code = conn.getResponseCode();
                    if (code == HttpURLConnection.HTTP_MOVED_PERM
                            || code == HttpURLConnection.HTTP_MOVED_TEMP
                            || code == 307
                            || code == 308) {
                        String loc = conn.getHeaderField("Location");
                        if (!TextUtils.isEmpty(loc)) {
                            currentUrl = loc;
                            redirects++;
                            conn.disconnect();
                            continue;
                        }
                    }
                    break;
                }

                int fileLength = conn.getContentLength();
                input = conn.getInputStream();
                output = new FileOutputStream(apkFile);

                byte[] data = new byte[8192];
                long total = 0;
                int count;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    if (fileLength > 0) {
                        int progress = (int) (total * 100 / fileLength);
                        new Handler(Looper.getMainLooper()).post(() -> progressDialog.setProgress(progress));
                    }
                    output.write(data, 0, count);
                }
                output.flush();

                File finalApkFile = apkFile;
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (!activity.isFinishing()) {
                        progressDialog.dismiss();
                    }
                    if (finalApkFile.exists() && finalApkFile.length() > 0) {
                        openPackageInstaller(activity, finalApkFile);
                    } else {
                        Toast.makeText(activity, "Download failed: Empty APK file received", Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Log.e(TAG, "Direct download failed", e);
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (!activity.isFinishing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(activity, "Download failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            } finally {
                try {
                    if (output != null) output.close();
                    if (input != null) input.close();
                } catch (Exception ignored) {}
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }

    /**
     * Checks if there is a pending update waiting for installation permission.
     */
    public void resumePendingUpdateIfAny(Activity activity) {
        if (pendingUpdateInfo != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (activity.getPackageManager().canRequestPackageInstalls()) {
                    UpdateInfo infoToRun = pendingUpdateInfo;
                    pendingUpdateInfo = null;
                    downloadAndInstallApk(activity, infoToRun);
                }
            }
        }
    }

    /**
     * Opens native Android package installer using FileProvider content:// URI.
     */
    private void openPackageInstaller(Context context, File apkFile) {
        if (apkFile == null || !apkFile.exists() || apkFile.length() == 0) {
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Downloaded update file not found", Toast.LENGTH_SHORT).show());
            return;
        }

        try {
            Uri contentUri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".fileprovider",
                    apkFile
            );

            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(installIntent);
        } catch (Exception e) {
            Log.e(TAG, "Error opening package installer", e);
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error launching installer: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }

    public interface OnUpdateCheckListener {
        void onUpdateAvailable(UpdateInfo updateInfo);
        void onNoUpdateAvailable();
    }
}
