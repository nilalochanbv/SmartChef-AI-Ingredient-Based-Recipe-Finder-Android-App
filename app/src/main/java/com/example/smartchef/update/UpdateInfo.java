package com.example.smartchef.update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UpdateInfo implements Serializable {
    private int versionCode;
    private String versionName;
    private String apkUrl;
    private boolean forceUpdate;
    private List<String> releaseNotes;

    public UpdateInfo() {
        this.releaseNotes = new ArrayList<>();
    }

    public UpdateInfo(int versionCode, String versionName, String apkUrl, boolean forceUpdate, List<String> releaseNotes) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.apkUrl = apkUrl;
        this.forceUpdate = forceUpdate;
        this.releaseNotes = releaseNotes != null ? releaseNotes : new ArrayList<>();
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public List<String> getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(List<String> releaseNotes) {
        this.releaseNotes = releaseNotes;
    }
}
