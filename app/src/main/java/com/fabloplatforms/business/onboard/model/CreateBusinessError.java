package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBusinessError {

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
    public CreateBusinessError() {
    }

    /**
     * @param message
     * @param error
     * @param items
     * @param status
     */
    public CreateBusinessError(Boolean status, String message, String error, Items items) {
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

        @SerializedName("driver")
        @Expose
        private Boolean driver;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("index")
        @Expose
        private Integer index;
        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("keyPattern")
        @Expose
        private KeyPattern keyPattern;
        @SerializedName("keyValue")
        @Expose
        private KeyValue keyValue;

        /**
         * No args constructor for use in serialization
         *
         */
        public Items() {
        }

        /**
         *
         * @param keyPattern
         * @param code
         * @param driver
         * @param keyValue
         * @param name
         * @param index
         */
        public Items(Boolean driver, String name, Integer index, Integer code, KeyPattern keyPattern, KeyValue keyValue) {
            super();
            this.driver = driver;
            this.name = name;
            this.index = index;
            this.code = code;
            this.keyPattern = keyPattern;
            this.keyValue = keyValue;
        }

        public Boolean getDriver() {
            return driver;
        }

        public void setDriver(Boolean driver) {
            this.driver = driver;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public KeyPattern getKeyPattern() {
            return keyPattern;
        }

        public void setKeyPattern(KeyPattern keyPattern) {
            this.keyPattern = keyPattern;
        }

        public KeyValue getKeyValue() {
            return keyValue;
        }

        public void setKeyValue(KeyValue keyValue) {
            this.keyValue = keyValue;
        }

    }

    public class KeyPattern {

        @SerializedName("phone")
        @Expose
        private Integer phone;

        /**
         * No args constructor for use in serialization
         *
         */
        public KeyPattern() {
        }

        /**
         *
         * @param phone
         */
        public KeyPattern(Integer phone) {
            super();
            this.phone = phone;
        }

        public Integer getPhone() {
            return phone;
        }

        public void setPhone(Integer phone) {
            this.phone = phone;
        }

    }

    public class KeyValue {

        @SerializedName("phone")
        @Expose
        private String phone;

        /**
         * No args constructor for use in serialization
         *
         */
        public KeyValue() {
        }

        /**
         *
         * @param phone
         */
        public KeyValue(String phone) {
            super();
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

    }

    @Override
    public String toString() {
        return "CreateBusinessError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", items=" + items +
                '}';
    }
}