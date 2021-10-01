package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBusinessRequest {

    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_logo")
    @Expose
    private String businessLogo;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("area_name")
    @Expose
    private String areaName;
    @SerializedName("area_id")
    @Expose
    private String areaId;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("business_type_name")
    @Expose
    private String businessTypeName;
    @SerializedName("business_type")
    @Expose
    private Integer businessType;
    @SerializedName("category_hindi")
    @Expose
    private String categoryHindi;
    @SerializedName("category_english")
    @Expose
    private String categoryEnglish;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("app_version")
    @Expose
    private String appVersion;

    public String getBusinessLogo() {
        return businessLogo;
    }

    public void setBusinessLogo(String businessLogo) {
        this.businessLogo = businessLogo;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getCategoryHindi() {
        return categoryHindi;
    }

    public void setCategoryHindi(String categoryHindi) {
        this.categoryHindi = categoryHindi;
    }

    public String getCategoryEnglish() {
        return categoryEnglish;
    }

    public void setCategoryEnglish(String categoryEnglish) {
        this.categoryEnglish = categoryEnglish;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }


}