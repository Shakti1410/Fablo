package com.fabloplatforms.business.store.models.acceptorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderOtpRequest {

    @SerializedName("otp")
    @Expose
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
