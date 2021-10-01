package com.fabloplatforms.business.store.models.pendingorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Time {

    @SerializedName("preparation")
    @Expose
    private String preparation;
    @SerializedName("expected_preparation")
    @Expose
    private String expectedPreparation;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("expected_delivery")
    @Expose
    private String expectedDelivery;
    @SerializedName("pickup")
    @Expose
    private String pickup;
    @SerializedName("expected_pickup")
    @Expose
    private String expectedPickup;
    @SerializedName("order")
    @Expose
    private String order;

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getExpectedPreparation() {
        return expectedPreparation;
    }

    public void setExpectedPreparation(String expectedPreparation) {
        this.expectedPreparation = expectedPreparation;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(String expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getExpectedPickup() {
        return expectedPickup;
    }

    public void setExpectedPickup(String expectedPickup) {
        this.expectedPickup = expectedPickup;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}