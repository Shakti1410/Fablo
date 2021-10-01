package com.fabloplatforms.business.modules.outlets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutletStatus {
    @SerializedName("details")
    @Expose
    private Integer details;
    @SerializedName("timings")
    @Expose
    private Integer timings;
    @SerializedName("photos")
    @Expose
    private Integer photos;

    public OutletStatus() {
    }

    public OutletStatus(Integer details, Integer timings, Integer photos) {
        this.details = details;
        this.timings = timings;
        this.photos = photos;
    }

    public Integer getDetails() {
        return details;
    }

    public void setDetails(Integer details) {
        this.details = details;
    }

    public Integer getTimings() {
        return timings;
    }

    public void setTimings(Integer timings) {
        this.timings = timings;
    }

    public Integer getPhotos() {
        return photos;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }
}
