package com.fabloplatforms.business.store.models.storeavailability;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreScheduleRequest {

    @SerializedName("store_id")
    @Expose
    private String store_id;
    @SerializedName("tommorow")
    @Expose
    private String tommorow;
    @SerializedName("hours")
    @Expose
    private String hours;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getTommorow() {
        return tommorow;
    }

    public void setTommorow(String tommorow) {
        this.tommorow = tommorow;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}