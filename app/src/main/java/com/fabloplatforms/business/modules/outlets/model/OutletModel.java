package com.fabloplatforms.business.modules.outlets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OutletModel {
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
    private Item items = null;

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

    public Item getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items = items;
    }
    public class Item {

        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("availability")
        @Expose
        private Integer availability;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("schema_version")
        @Expose
        private String schemaVersion;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("business_id")
        @Expose
        private String businessId;
        @SerializedName("locality")
        @Expose
        private String locality;
        @SerializedName("storeUsername")
        @Expose
        private String storeUsername;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("app_version")
        @Expose
        private String appVersion;
        @SerializedName("store_name")
        @Expose
        private String storeName;
        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_english")
        @Expose
        private String categoryEnglish;
        @SerializedName("category_hindi")
        @Expose
        private String categoryHindi;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Integer getAvailability() {
            return availability;
        }

        public void setAvailability(Integer availability) {
            this.availability = availability;
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

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public String getStoreUsername() {
            return storeUsername;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryEnglish() {
            return categoryEnglish;
        }

        public void setCategoryEnglish(String categoryEnglish) {
            this.categoryEnglish = categoryEnglish;
        }

        public String getCategoryHindi() {
            return categoryHindi;
        }

        public void setCategoryHindi(String categoryHindi) {
            this.categoryHindi = categoryHindi;
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
    public class Location {

        @SerializedName("coordinates")
        @Expose
        private List<Integer> coordinates = null;
        @SerializedName("type")
        @Expose
        private String type;

        public List<Integer> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Integer> coordinates) {
            this.coordinates = coordinates;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }
}

