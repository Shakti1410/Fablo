package com.fabloplatforms.business.store.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientErrorBody {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;

    /**
     * No args constructor for use in serialization
     *
     */
    public ClientErrorBody() {
    }

    /**
     *
     * @param message
     * @param error
     * @param status
     */
    public ClientErrorBody(Boolean status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}