package com.fabloplatforms.business.store.models;

public class ProductStatus {
    private boolean online;

    public ProductStatus() {
    }

    public ProductStatus(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
