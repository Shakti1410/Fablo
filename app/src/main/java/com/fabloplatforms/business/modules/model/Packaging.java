package com.fabloplatforms.business.modules.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Packaging {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("rate")
    @Expose
    private Integer rate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

}
