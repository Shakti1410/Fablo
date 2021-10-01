package com.fabloplatforms.business.store.models;

public class OutletStatus {

    private boolean online;

    public OutletStatus() {
    }

    public OutletStatus(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
