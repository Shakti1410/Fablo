package com.fabloplatforms.business.store.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCountResponse {

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

        @SerializedName("preparingOrders")
        @Expose
        private Integer preparingOrders;
        @SerializedName("readyOrders")
        @Expose
        private Integer readyOrders;
        @SerializedName("dispatchedOrders")
        @Expose
        private Integer dispatchedOrders;

        public Integer getPreparingOrders() {
            return preparingOrders;
        }

        public void setPreparingOrders(Integer preparingOrders) {
            this.preparingOrders = preparingOrders;
        }

        public Integer getReadyOrders() {
            return readyOrders;
        }

        public void setReadyOrders(Integer readyOrders) {
            this.readyOrders = readyOrders;
        }

        public Integer getDispatchedOrders() {
            return dispatchedOrders;
        }

        public void setDispatchedOrders(Integer dispatchedOrders) {
            this.dispatchedOrders = dispatchedOrders;
        }

    }

}