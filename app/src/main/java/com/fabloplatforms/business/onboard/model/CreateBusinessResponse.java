package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBusinessResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("items")
    @Expose
    private Items items;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
    public class Items {

        @SerializedName("stages_verification")
        @Expose
        private StagesVerification stagesVerification;
        @SerializedName("business_logo")
        @Expose
        private String businessLogo;
        @SerializedName("settlement_type")
        @Expose
        private Integer settlementType;
        @SerializedName("wallet")
        @Expose
        private Integer wallet;
        @SerializedName("credit")
        @Expose
        private Integer credit;
        @SerializedName("paylater_payment_limit")
        @Expose
        private Integer paylaterPaymentLimit;
        @SerializedName("other_payment_limit")
        @Expose
        private Integer otherPaymentLimit;
        @SerializedName("qr")
        @Expose
        private String qr;
        @SerializedName("listed")
        @Expose
        private Boolean listed;
        @SerializedName("agent_code")
        @Expose
        private String agentCode;
        @SerializedName("blocked")
        @Expose
        private Boolean blocked;
        @SerializedName("overall_verified")
        @Expose
        private Boolean overallVerified;
        @SerializedName("verification")
        @Expose
        private Integer verification;
        @SerializedName("schema_version")
        @Expose
        private String schemaVersion;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("business_name")
        @Expose
        private String businessName;
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
        @SerializedName("settlement_rate")
        @Expose
        private Integer settlementRate;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public StagesVerification getStagesVerification() {
            return stagesVerification;
        }

        public void setStagesVerification(StagesVerification stagesVerification) {
            this.stagesVerification = stagesVerification;
        }

        public String getBusinessLogo() {
            return businessLogo;
        }

        public void setBusinessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
        }

        public Integer getSettlementType() {
            return settlementType;
        }

        public void setSettlementType(Integer settlementType) {
            this.settlementType = settlementType;
        }

        public Integer getWallet() {
            return wallet;
        }

        public void setWallet(Integer wallet) {
            this.wallet = wallet;
        }

        public Integer getCredit() {
            return credit;
        }

        public void setCredit(Integer credit) {
            this.credit = credit;
        }

        public Integer getPaylaterPaymentLimit() {
            return paylaterPaymentLimit;
        }

        public void setPaylaterPaymentLimit(Integer paylaterPaymentLimit) {
            this.paylaterPaymentLimit = paylaterPaymentLimit;
        }

        public Integer getOtherPaymentLimit() {
            return otherPaymentLimit;
        }

        public void setOtherPaymentLimit(Integer otherPaymentLimit) {
            this.otherPaymentLimit = otherPaymentLimit;
        }

        public String getQr() {
            return qr;
        }

        public void setQr(String qr) {
            this.qr = qr;
        }

        public Boolean getListed() {
            return listed;
        }

        public void setListed(Boolean listed) {
            this.listed = listed;
        }

        public String getAgentCode() {
            return agentCode;
        }

        public void setAgentCode(String agentCode) {
            this.agentCode = agentCode;
        }

        public Boolean getBlocked() {
            return blocked;
        }

        public void setBlocked(Boolean blocked) {
            this.blocked = blocked;
        }

        public Boolean getOverallVerified() {
            return overallVerified;
        }

        public void setOverallVerified(Boolean overallVerified) {
            this.overallVerified = overallVerified;
        }

        public Integer getVerification() {
            return verification;
        }

        public void setVerification(Integer verification) {
            this.verification = verification;
        }

        public String getSchemaVersion() {
            return schemaVersion;
        }

        public void setSchemaVersion(String schemaVersion) {
            this.schemaVersion = schemaVersion;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public Integer getSettlementRate() {
            return settlementRate;
        }

        public void setSettlementRate(Integer settlementRate) {
            this.settlementRate = settlementRate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }
    public class StagesVerification {

        @SerializedName("contactDetails")
        @Expose
        private Integer contactDetails;
        @SerializedName("bankDetails")
        @Expose
        private Integer bankDetails;
        @SerializedName("taxDetails")
        @Expose
        private Integer taxDetails;
        @SerializedName("fssaiDetails")
        @Expose
        private Integer fssaiDetails;
        @SerializedName("kycDetails")
        @Expose
        private Integer kycDetails;
        @SerializedName("commissionDetails")
        @Expose
        private Integer commissionDetails;
        @SerializedName("eSignatureDetails")
        @Expose
        private Integer eSignatureDetails;

        public Integer getContactDetails() {
            return contactDetails;
        }

        public void setContactDetails(Integer contactDetails) {
            this.contactDetails = contactDetails;
        }

        public Integer getBankDetails() {
            return bankDetails;
        }

        public void setBankDetails(Integer bankDetails) {
            this.bankDetails = bankDetails;
        }

        public Integer getTaxDetails() {
            return taxDetails;
        }

        public void setTaxDetails(Integer taxDetails) {
            this.taxDetails = taxDetails;
        }

        public Integer getFssaiDetails() {
            return fssaiDetails;
        }

        public void setFssaiDetails(Integer fssaiDetails) {
            this.fssaiDetails = fssaiDetails;
        }

        public Integer getKycDetails() {
            return kycDetails;
        }

        public void setKycDetails(Integer kycDetails) {
            this.kycDetails = kycDetails;
        }

        public Integer getCommissionDetails() {
            return commissionDetails;
        }

        public void setCommissionDetails(Integer commissionDetails) {
            this.commissionDetails = commissionDetails;
        }

        public Integer geteSignatureDetails() {
            return eSignatureDetails;
        }

        public void seteSignatureDetails(Integer eSignatureDetails) {
            this.eSignatureDetails = eSignatureDetails;
        }

    }
}