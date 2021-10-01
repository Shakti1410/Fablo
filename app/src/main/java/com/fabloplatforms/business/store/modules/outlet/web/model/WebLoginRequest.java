package com.fabloplatforms.business.store.modules.outlet.web.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebLoginRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("app_version")
    @Expose
    private String appVersion;

    /**
     * No args constructor for use in serialization
     */
    public WebLoginRequest() {
    }

    /**
     * @param appVersion
     * @param token
     */
    public WebLoginRequest(String token, String appVersion) {
        super();
        this.token = token;
        this.appVersion = appVersion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}