package com.fabloplatforms.business.auth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerifyRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("otp")
    @Expose
    private String otp;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}