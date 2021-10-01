package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxDetailsRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("pancard")
    @Expose
    private Pancard pancard;
    @SerializedName("gst")
    @Expose
    private Gst gst;
    @SerializedName("app_version")
    @Expose
    private String appVersion;
    @SerializedName("turnOver")
    @Expose
    private String turnOver;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Pancard getPancard() {
        return pancard;
    }

    public void setPancard(Pancard pancard) {
        this.pancard = pancard;
    }

    public Gst getGst() {
        return gst;
    }

    public void setGst(Gst gst) {
        this.gst = gst;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
    public String getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(String turnOver) {
        this.turnOver = turnOver;
    }
}
