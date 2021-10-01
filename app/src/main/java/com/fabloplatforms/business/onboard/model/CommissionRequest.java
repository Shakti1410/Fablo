package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommissionRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("commission")
    @Expose
    private Commission commission;
    @SerializedName("app_version")
    @Expose
    private String appVersion;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}
