package com.fabloplatforms.business.store.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuProductResponse {

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

        @SerializedName("service_type")
        @Expose
        private ServiceType serviceType;
        @SerializedName("tax_name")
        @Expose
        private String taxName;
        @SerializedName("tax_percent")
        @Expose
        private Integer taxPercent;
        @SerializedName("charge_name")
        @Expose
        private String chargeName;
        @SerializedName("charge_price")
        @Expose
        private Integer chargePrice;
        @SerializedName("photos")
        @Expose
        private List<String> photos = null;
        @SerializedName("beverage")
        @Expose
        private String beverage;
        @SerializedName("stock")
        @Expose
        private Integer stock;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("schema_version")
        @Expose
        private String schemaVersion;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_category_id")
        @Expose
        private String productCategoryId;
        @SerializedName("product_category_name")
        @Expose
        private String productCategoryName;
        @SerializedName("product_subcategory_id")
        @Expose
        private String productSubcategoryId;
        @SerializedName("product_subcategory_name")
        @Expose
        private String productSubcategoryName;
        @SerializedName("food_type")
        @Expose
        private String foodType;
        @SerializedName("base_price")
        @Expose
        private Integer basePrice;
        @SerializedName("tax_id")
        @Expose
        private String taxId;
        @SerializedName("charge_id")
        @Expose
        private String chargeId;
        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("calories_count")
        @Expose
        private Integer caloriesCount;
        @SerializedName("portion_size")
        @Expose
        private Integer portionSize;
        @SerializedName("preparation_time")
        @Expose
        private String preparationTime;
        @SerializedName("app_version")
        @Expose
        private String appVersion;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public ServiceType getServiceType() {
            return serviceType;
        }

        public void setServiceType(ServiceType serviceType) {
            this.serviceType = serviceType;
        }

        public String getTaxName() {
            return taxName;
        }

        public void setTaxName(String taxName) {
            this.taxName = taxName;
        }

        public Integer getTaxPercent() {
            return taxPercent;
        }

        public void setTaxPercent(Integer taxPercent) {
            this.taxPercent = taxPercent;
        }

        public String getChargeName() {
            return chargeName;
        }

        public void setChargeName(String chargeName) {
            this.chargeName = chargeName;
        }

        public Integer getChargePrice() {
            return chargePrice;
        }

        public void setChargePrice(Integer chargePrice) {
            this.chargePrice = chargePrice;
        }

        public List<String> getPhotos() {
            return photos;
        }

        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }

        public String getBeverage() {
            return beverage;
        }

        public void setBeverage(String beverage) {
            this.beverage = beverage;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(String productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public String getProductCategoryName() {
            return productCategoryName;
        }

        public void setProductCategoryName(String productCategoryName) {
            this.productCategoryName = productCategoryName;
        }

        public String getProductSubcategoryId() {
            return productSubcategoryId;
        }

        public void setProductSubcategoryId(String productSubcategoryId) {
            this.productSubcategoryId = productSubcategoryId;
        }

        public String getProductSubcategoryName() {
            return productSubcategoryName;
        }

        public void setProductSubcategoryName(String productSubcategoryName) {
            this.productSubcategoryName = productSubcategoryName;
        }

        public String getFoodType() {
            return foodType;
        }

        public void setFoodType(String foodType) {
            this.foodType = foodType;
        }

        public Integer getBasePrice() {
            return basePrice;
        }

        public void setBasePrice(Integer basePrice) {
            this.basePrice = basePrice;
        }

        public String getTaxId() {
            return taxId;
        }

        public void setTaxId(String taxId) {
            this.taxId = taxId;
        }

        public String getChargeId() {
            return chargeId;
        }

        public void setChargeId(String chargeId) {
            this.chargeId = chargeId;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public Integer getCaloriesCount() {
            return caloriesCount;
        }

        public void setCaloriesCount(Integer caloriesCount) {
            this.caloriesCount = caloriesCount;
        }

        public Integer getPortionSize() {
            return portionSize;
        }

        public void setPortionSize(Integer portionSize) {
            this.portionSize = portionSize;
        }

        public String getPreparationTime() {
            return preparationTime;
        }

        public void setPreparationTime(String preparationTime) {
            this.preparationTime = preparationTime;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
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

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }
    public class ServiceType {

        @SerializedName("deliver")
        @Expose
        private Integer deliver;
        @SerializedName("takeaway")
        @Expose
        private Integer takeaway;

        public Integer getDeliver() {
            return deliver;
        }

        public void setDeliver(Integer deliver) {
            this.deliver = deliver;
        }

        public Integer getTakeaway() {
            return takeaway;
        }

        public void setTakeaway(Integer takeaway) {
            this.takeaway = takeaway;
        }

    }
}
