package com.fabloplatforms.business.store.models.pendingorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingOrderResponse {



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
        private List<Items> items = null;

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

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }


    public class Items {

        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("store")
        @Expose
        private Store store;
        @SerializedName("delivery_partner")
        @Expose
        private DeliveryPartner deliveryPartner;
        @SerializedName("payment")
        @Expose
        private Payment payment;
        @SerializedName("time")
        @Expose
        private Time time;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("cancellationReason")
        @Expose
        private String cancellationReason;
        @SerializedName("schema_version")
        @Expose
        private String schemaVersion;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("app_version")
        @Expose
        private String appVersion;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("deliveryContactType")
        @Expose
        private String deliveryContactType;
        @SerializedName("orderFromType")
        @Expose
        private String orderFromType;
        @SerializedName("orderByType")
        @Expose
        private String orderByType;
        @SerializedName("orderType")
        @Expose
        private String orderType;
        @SerializedName("products")
        @Expose
        private List<Product> products = null;
        @SerializedName("orderId")
        @Expose
        private String orderId;
        @SerializedName("distanceBetweenUserAndStore")
        @Expose
        private Double distanceBetweenUserAndStore;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Store getStore() {
            return store;
        }

        public void setStore(Store store) {
            this.store = store;
        }

        public DeliveryPartner getDeliveryPartner() {
            return deliveryPartner;
        }

        public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
            this.deliveryPartner = deliveryPartner;
        }

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }

        public Time getTime() {
            return time;
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getCancellationReason() {
            return cancellationReason;
        }

        public void setCancellationReason(String cancellationReason) {
            this.cancellationReason = cancellationReason;
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

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getDeliveryContactType() {
            return deliveryContactType;
        }

        public void setDeliveryContactType(String deliveryContactType) {
            this.deliveryContactType = deliveryContactType;
        }

        public String getOrderFromType() {
            return orderFromType;
        }

        public void setOrderFromType(String orderFromType) {
            this.orderFromType = orderFromType;
        }

        public String getOrderByType() {
            return orderByType;
        }

        public void setOrderByType(String orderByType) {
            this.orderByType = orderByType;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Double getDistanceBetweenUserAndStore() {
            return distanceBetweenUserAndStore;
        }

        public void setDistanceBetweenUserAndStore(Double distanceBetweenUserAndStore) {
            this.distanceBetweenUserAndStore = distanceBetweenUserAndStore;
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
}
