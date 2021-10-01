package com.fabloplatforms.business.store.models.pendingorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("location")
    @Expose
    private Location__1 location;
    @SerializedName("area_name")
    @Expose
    private String areaName;
    @SerializedName("area_id")
    @Expose
    private String areaId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public Location__1 getLocation() {
        return location;
    }

    public void setLocation(Location__1 location) {
        this.location = location;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}