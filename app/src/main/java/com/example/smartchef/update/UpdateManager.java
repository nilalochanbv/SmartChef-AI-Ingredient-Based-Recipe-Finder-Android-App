package com.example.smartchef.update;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.smartchef.utils.UpdateConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class UpdateManager {

    public interface OnUpdateCheckListener {
        void onUpdateAvailable(UpdateInfo updateInfo);
        void onNoUpdateAvailable();
    }

    private static UpdateManager instance;

    private UpdateManager() {}

    public static synchronized UpdateManager getInstance() {
        if (instance == null) {
            instance = new UpdateManager();
        }
        return instance;
    }

    /**
     * Asynchronously checks for an app update from the online JSON configuration URL.
     * Does not block the main UI thread.
     */
    public void checkForUpdate(Activity activity, OnUpdateCheckListener listener) {
        if (activity == null || activity.isFinishing()) return;

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                URL url = new URL(UpdateConstants.UPDATE_JSON_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);

                int responseCode = conn.getResponseCode();
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

                    long installedVersionCode = getInstalledVersionCode(activity);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (latestVersionCode > installedVersionCode) {
                            if (listener != null) listener.onUpdateAvailable(updateInfo);
                        } else {
                            if (listener != null) listener.onNoUpdateAvailable();
                        }
                    });
                } else {
                    notifyNoUpdate(listener);
                }
            } catch (Exception e) {
                notifyNoUpdate(listener);
            }
        });
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
        messageBuilder.append("Current Version: ").append(getInstalledVersionName(activity)).append("\n");
        messageBuilder.append("Latest Version: ").append(updateInfo.getVersionName()).append("\n\n");
        
        if (updateInfo.getReleaseNotes() != null && !updateInfo.getReleaseNotes().isEmpty()) {
            messageBuilder.append("What's New:\n");
            for (String note : updateInfo.getReleaseNotes()) {
                messageBuilder.append("✓ ").append(note).append("\n");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("🎉 Update Available")
                .setMessage(messageBuilder.toString().trim())
                .setPositiveButton("Update Now", (dialog, which) -> {
                    downloadAndInstallApk(activity, updateInfo);
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
     * Downloads APK via DownloadManager and opens package installer upon completion.
     */
    public void downloadAndInstallApk(Activity activity, UpdateInfo updateInfo) {
        if (activity == null || TextUtils.isEmpty(updateInfo.getApkUrl())) {
            Toast.makeText(activity, "Invalid update download URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check Android 8.0+ Unknown App Sources Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!activity.getPackageManager().canRequestPackageInstalls()) {
                new AlertDialog.Builder(activity)
                        .setTitle("Permission Required")
                        .setMessage("To install updates outside of Google Play Store, please allow SmartChef AI to install unknown apps in Android Settings.")
                        .setPositiveButton("Open Settings", (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                    Uri.parse("package:" + activity.getPackageName()));
                            activity.startActivity(intent);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return;
            }
        }

        Toast.makeText(activity, "Downloading latest update...", Toast.LENGTH_SHORT).show();

        try {
            File destinationFile = new File(activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "SmartChef_AI_Update.apk");
            if (destinationFile.exists()) {
                destinationFile.delete();
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateInfo.getApkUrl()));
            request.setTitle("SmartChef AI Update v" + updateInfo.getVersionName());
            request.setDescription("Downloading application update...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationUri(Uri.fromFile(destinationFile));

            DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                long downloadId = downloadManager.enqueue(request);

                BroadcastReceiver onComplete = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        if (id == downloadId) {
                            try {
                                context.unregisterReceiver(this);
                            } catch (Exception ignored) {}
                            openPackageInstaller(activity, destinationFile);
                        }
                    }
                };

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_EXPORTED);
                } else {
                    activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                }
            }
        } catch (Exception e) {
            Toast.makeText(activity, "Download failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Opens native Android package installer using FileProvider content:// URI.
     */
    private void openPackageInstaller(Activity activity, File apkFile) {
        if (apkFile == null || !apkFile.exists()) {
            Toast.makeText(activity, "Downloaded file not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Uri contentUri = FileProvider.getUriForFile(
                    activity,
                    activity.getPackageName() + ".fileprovider",
                    apkFile
            );

            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            activity.startActivity(installIntent);
        } catch (Exception e) {
            Toast.makeText(activity, "Error opening installer: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
