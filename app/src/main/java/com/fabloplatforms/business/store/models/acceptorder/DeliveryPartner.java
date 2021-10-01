
package com.fabloplatforms.business.store.models.acceptorder;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DeliveryPartner {

    @SerializedName("location")
    @Expose
    private com.fabloplatforms.business.store.models.acceptorder.Location__2 location;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otp")
    @Expose
    private Integer otp;

    public com.fabloplatforms.business.store.models.acceptorder.Location__2 getLocation() {
        return location;
    }

    public void setLocation(com.fabloplatforms.business.store.models.acceptorder.Location__2 location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

}
