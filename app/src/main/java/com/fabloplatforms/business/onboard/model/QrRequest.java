package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrRequest {

    @SerializedName("qr")
    @Expose
    private String qr;

    /**
     * No args constructor for use in serialization
     */
    public QrRequest() {
    }

    /**
     * @param qr
     */
    public QrRequest(String qr) {
        super();
        this.qr = qr;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

}