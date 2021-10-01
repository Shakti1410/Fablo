package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycDetailsRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("aadhar_number")
    @Expose
    private String aadharNumber;
    @SerializedName("aadhar_front")
    @Expose
    private AadharFront aadharFront;
    @SerializedName("aadhar_back")
    @Expose
    private AadharBack aadharBack;
    @SerializedName("app_version")
    @Expose
    private String appVersion;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public AadharFront getAadharFront() {
        return aadharFront;
    }

    public void setAadharFront(AadharFront aadharFront) {
        this.aadharFront = aadharFront;
    }

    public AadharBack getAadharBack() {
        return aadharBack;
    }

    public void setAadharBack(AadharBack aadharBack) {
        this.aadharBack = aadharBack;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}
