package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FssaiDetailsRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("availability")
    @Expose
    private Boolean availability;
    @SerializedName("acknowledgement")
    @Expose
    private Acknowledgement acknowledgement;
    @SerializedName("license")
    @Expose
    private License license;
    @SerializedName("app_version")
    @Expose
    private String appVersion;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Acknowledgement getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(Acknowledgement acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}
