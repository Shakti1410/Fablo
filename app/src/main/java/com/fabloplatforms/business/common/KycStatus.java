package com.fabloplatforms.business.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycStatus {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("items")
    @Expose
    private Items items;

    /**
     * No args constructor for use in serialization
     */
    public KycStatus() {
    }

    /**
     * @param message
     * @param error
     * @param items
     * @param status
     */
    public KycStatus(Boolean status, String message, String error, Items items) {
        super();
        this.status = status;
        this.message = message;
        this.error = error;
        this.items = items;
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

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public class Items {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("schema_version")
        @Expose
        private String schemaVersion;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("app_version")
        @Expose
        private String appVersion;
        @SerializedName("business_id")
        @Expose
        private String businessId;
        @SerializedName("schedule_date")
        @Expose
        private String scheduleDate;
        @SerializedName("schedule_time")
        @Expose
        private String scheduleTime;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        /**
         * No args constructor for use in serialization
         *
         */
        public Items() {
        }

        /**
         *
         * @param scheduleTime
         * @param createdAt
         * @param appVersion
         * @param schemaVersion
         * @param businessId
         * @param scheduleDate
         * @param id
         * @param status
         * @param updatedAt
         */
        public Items(Integer status, String schemaVersion, String id, String appVersion, String businessId, String scheduleDate, String scheduleTime, String createdAt, String updatedAt) {
            super();
            this.status = status;
            this.schemaVersion = schemaVersion;
            this.id = id;
            this.appVersion = appVersion;
            this.businessId = businessId;
            this.scheduleDate = scheduleDate;
            this.scheduleTime = scheduleTime;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getSchemaVersion() {
            return schemaVersion;
        }

        public void setSchemaVersion(String schemaVersion) {
            this.schemaVersion = schemaVersion;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

}