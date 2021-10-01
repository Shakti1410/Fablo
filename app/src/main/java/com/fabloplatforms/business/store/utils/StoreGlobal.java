package com.fabloplatforms.business.store.utils;

public class StoreGlobal {

    private String appVersion;

    public StoreGlobal() {
    }

    public StoreGlobal(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
