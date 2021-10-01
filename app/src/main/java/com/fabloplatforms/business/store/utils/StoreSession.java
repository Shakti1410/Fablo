package com.fabloplatforms.business.store.utils;

public class StoreSession {

    private String restaurantId;
    private int status;

    public StoreSession() {
    }

    public StoreSession(String restaurantId, int status) {
        this.restaurantId = restaurantId;
        this.status = status;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
