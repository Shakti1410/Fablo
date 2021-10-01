package com.fabloplatforms.business.modules.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreListResponse {

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
    private List<Item> items = null;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public class Item {

        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("packaging")
        @Expose
        private Packaging packaging;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("availability")
        @Expose
        private Integer availability;
        @SerializedName("verification")
        @Expose
        private Integer verification;
        @SerializedName("listed")
        @Expose
        private Integer listed;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("table_view")
        @Expose
        private String tableView;
        @SerializedName("tags")
        @Expose
        private List<String> tags = null;
        @SerializedName("menu_approval")
        @Expose
        private Integer menuApproval;
        @SerializedName("menu_images")
        @Expose
        private List<String> menuImages = null;
        @SerializedName("avg_preparation_time")
        @Expose
        private Integer avgPreparationTime;
        @SerializedName("pure_veg")
        @Expose
        private Boolean pureVeg;
        @SerializedName("mrp_based_products")
        @Expose
        private Boolean mrpBasedProducts;
        @SerializedName("promoted")
        @Expose
        private Integer promoted;
        @SerializedName("promoted_type")
        @Expose
        private String promotedType;
        @SerializedName("promotion_details")
        @Expose
        private List<Object> promotionDetails = null;
        @SerializedName("offer_type")
        @Expose
        private String offerType;
        @SerializedName("offer_details")
        @Expose
        private List<Object> offerDetails = null;
        @SerializedName("timing")
        @Expose
        private Integer timing;
        @SerializedName("schema_version")
        @Expose
        private String schemaVersion;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("store_name")
        @Expose
        private String storeName;
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
        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("area_id")
        @Expose
        private String areaId;
        @SerializedName("area_name")
        @Expose
        private String areaName;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_english")
        @Expose
        private String categoryEnglish;
        @SerializedName("category_hindi")
        @Expose
        private String categoryHindi;
        @SerializedName("token_size")
        @Expose
        private Integer tokenSize;
        @SerializedName("app_version")
        @Expose
        private String appVersion;
        @SerializedName("business_id")
        @Expose
        private String businessId;
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

        public Packaging getPackaging() {
            return packaging;
        }

        public void setPackaging(Packaging packaging) {
            this.packaging = packaging;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Integer getAvailability() {
            return availability;
        }

        public void setAvailability(Integer availability) {
            this.availability = availability;
        }

        public Integer getVerification() {
            return verification;
        }

        public void setVerification(Integer verification) {
            this.verification = verification;
        }

        public Integer getListed() {
            return listed;
        }

        public void setListed(Integer listed) {
            this.listed = listed;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getTableView() {
            return tableView;
        }

        public void setTableView(String tableView) {
            this.tableView = tableView;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public Integer getMenuApproval() {
            return menuApproval;
        }

        public void setMenuApproval(Integer menuApproval) {
            this.menuApproval = menuApproval;
        }

        public List<String> getMenuImages() {
            return menuImages;
        }

        public void setMenuImages(List<String> menuImages) {
            this.menuImages = menuImages;
        }

        public Integer getAvgPreparationTime() {
            return avgPreparationTime;
        }

        public void setAvgPreparationTime(Integer avgPreparationTime) {
            this.avgPreparationTime = avgPreparationTime;
        }

        public Boolean getPureVeg() {
            return pureVeg;
        }

        public void setPureVeg(Boolean pureVeg) {
            this.pureVeg = pureVeg;
        }

        public Boolean getMrpBasedProducts() {
            return mrpBasedProducts;
        }

        public void setMrpBasedProducts(Boolean mrpBasedProducts) {
            this.mrpBasedProducts = mrpBasedProducts;
        }

        public Integer getPromoted() {
            return promoted;
        }

        public void setPromoted(Integer promoted) {
            this.promoted = promoted;
        }

        public String getPromotedType() {
            return promotedType;
        }

        public void setPromotedType(String promotedType) {
            this.promotedType = promotedType;
        }

        public List<Object> getPromotionDetails() {
            return promotionDetails;
        }

        public void setPromotionDetails(List<Object> promotionDetails) {
            this.promotionDetails = promotionDetails;
        }

        public String getOfferType() {
            return offerType;
        }

        public void setOfferType(String offerType) {
            this.offerType = offerType;
        }

        public List<Object> getOfferDetails() {
            return offerDetails;
        }

        public void setOfferDetails(List<Object> offerDetails) {
            this.offerDetails = offerDetails;
        }

        public Integer getTiming() {
            return timing;
        }

        public void setTiming(Integer timing) {
            this.timing = timing;
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

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
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

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
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

        public Integer getTokenSize() {
            return tokenSize;
        }

        public void setTokenSize(Integer tokenSize) {
            this.tokenSize = tokenSize;
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