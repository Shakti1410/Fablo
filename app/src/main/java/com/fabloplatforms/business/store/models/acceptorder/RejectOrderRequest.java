package com.fabloplatforms.business.store.models.acceptorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RejectOrderRequest {

    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("userId")
    @Expose
    private String userId;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
