
package com.fabloplatforms.business.store.models.acceptorder;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Variant {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("variant_name")
    @Expose
    private String variantName;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("variant_id")
    @Expose
    private String variantId;

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

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

}
