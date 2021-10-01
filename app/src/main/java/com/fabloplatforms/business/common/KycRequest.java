package com.fabloplatforms.business.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycRequest {

    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("schedule_date")
    @Expose
    private String scheduleDate;
    @SerializedName("schedule_time")
    @Expose
    private String scheduleTime;
    @SerializedName("app_version")
    @Expose
    private String appVersion;

    /**
     * No args constructor for use in serialization
     */
    public KycRequest() {
    }

    /**
     * @param scheduleTime
     * @param appVersion
     * @param businessId
     * @param scheduleDate
     */
    public KycRequest(String businessId, String scheduleDate, String scheduleTime, String appVersion) {
        super();
        this.businessId = businessId;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.appVersion = appVersion;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}