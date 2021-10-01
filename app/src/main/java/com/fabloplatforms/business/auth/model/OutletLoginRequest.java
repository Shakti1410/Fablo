package com.fabloplatforms.business.auth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutletLoginRequest {
    @SerializedName("storeUsername")
    @Expose
    private String storeUsername;
    @SerializedName("password")
    @Expose
    private String password;

    public String getStoreUsername() {
        return storeUsername;
    }

    public OutletLoginRequest() {
    }

    public OutletLoginRequest(String storeUsername, String password) {
        this.storeUsername = storeUsername;
        this.password = password;
    }

    public void setStoreUsername(String storeUsername) {
        this.storeUsername = storeUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
