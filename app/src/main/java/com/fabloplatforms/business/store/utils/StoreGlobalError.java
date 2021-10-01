package com.fabloplatforms.business.store.utils;

public class StoreGlobalError {

    private String title;
    private String description;
    private int code;

    public StoreGlobalError(String title, String description, int code) {
        this.title = title;
        this.description = description;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
