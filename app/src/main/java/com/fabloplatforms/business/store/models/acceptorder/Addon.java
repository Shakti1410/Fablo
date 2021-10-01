
package com.fabloplatforms.business.store.models.acceptorder;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Addon {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("addon_name")
    @Expose
    private String addonName;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("addon_id")
    @Expose
    private String addonId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAddonName() {
        return addonName;
    }

    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAddonId() {
        return addonId;
    }

    public void setAddonId(String addonId) {
        this.addonId = addonId;
    }

}
