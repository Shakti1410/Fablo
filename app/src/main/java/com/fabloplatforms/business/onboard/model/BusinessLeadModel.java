package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessLeadModel {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("full_name")
    @Expose
    private String full_name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("business_name")
    @Expose
    private String business_name;
    @SerializedName("city_id")
    @Expose
    private String city_id;
    @SerializedName("city_name")
    @Expose
    private String city_name;
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("app_version")
    @Expose
    private String app_version;

    public BusinessLeadModel() {
    }

    public BusinessLeadModel(String token, String full_name, String phone, String business_name, String city_id, String city_name, Integer pincode, String address, String app_version) {
        this.token = token;
        this.full_name = full_name;
        this.phone = phone;
        this.business_name = business_name;
        this.city_id = city_id;
        this.city_name = city_name;
        this.pincode = pincode;
        this.address = address;
        this.app_version = app_version;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
