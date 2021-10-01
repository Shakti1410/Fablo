package com.fabloplatforms.business.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Business {

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

    /**
     * No args constructor for use in serialization
     */
    public Business() {
    }

    /**
     * @param message
     * @param error
     * @param items
     * @param status
     */
    public Business(Boolean status, String message, String error, Items items) {
        super();
        this.status = status;
        this.message = message;
        this.error = error;
        this.items = items;
    }

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

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("settlement_type")
        @Expose
        private Integer settlementType;
        @SerializedName("wallet")
        @Expose
        private Integer wallet;
        @SerializedName("credit")
        @Expose
        private Integer credit;
        @SerializedName("qr")
        @Expose
        private String qr;
        @SerializedName("kyc_status")
        @Expose
        private Integer kycStatus;
        @SerializedName("listed")
        @Expose
        private Integer listed;
        @SerializedName("blocked")
        @Expose
        private Integer blocked;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("business_type")
        @Expose
        private Integer businessType;
        @SerializedName("business_type_name")
        @Expose
        private String businessTypeName;
        @SerializedName("category_english")
        @Expose
        private String categoryEnglish;
        @SerializedName("category_hindi")
        @Expose
        private String categoryHindi;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("settlement_rate")
        @Expose
        private Float settlementRate;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("paylater_payment_limit")
        @Expose
        private Integer paylaterPaymentLimit;
        @SerializedName("other_payment_limit")
        @Expose
        private Integer otherPaymentLimit;

        /**
         * No args constructor for use in serialization
         *
         */
        public Items() {
        }

        /**
         *
         * @param settlementType
         * @param qr
         * @param wallet
         * @param kycStatus
         * @param businessName
         * @param cityId
         * @param settlementRate
         * @param categoryEnglish
         * @param token
         * @param listed
         * @param blocked
         * @param cityName
         * @param businessTypeName
         * @param phone
         * @param paylaterPaymentLimit
         * @param id
         * @param credit
         * @param businessType
         * @param categoryHindi
         * @param otherPaymentLimit
         * @param categoryId
         */
        public Items(String id, Integer settlementType, Integer wallet, Integer credit, String qr, Integer kycStatus, Integer listed, Integer blocked, String businessName, Integer businessType, String businessTypeName, String categoryEnglish, String categoryHindi, String categoryId, String cityId, String cityName, String phone, Float settlementRate, String token, Integer paylaterPaymentLimit, Integer otherPaymentLimit) {
            super();
            this.id = id;
            this.settlementType = settlementType;
            this.wallet = wallet;
            this.credit = credit;
            this.qr = qr;
            this.kycStatus = kycStatus;
            this.listed = listed;
            this.blocked = blocked;
            this.businessName = businessName;
            this.businessType = businessType;
            this.businessTypeName = businessTypeName;
            this.categoryEnglish = categoryEnglish;
            this.categoryHindi = categoryHindi;
            this.categoryId = categoryId;
            this.cityId = cityId;
            this.cityName = cityName;
            this.phone = phone;
            this.settlementRate = settlementRate;
            this.token = token;
            this.paylaterPaymentLimit = paylaterPaymentLimit;
            this.otherPaymentLimit = otherPaymentLimit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getQr() {
            return qr;
        }

        public void setQr(String qr) {
            this.qr = qr;
        }

        public Integer getKycStatus() {
            return kycStatus;
        }

        public void setKycStatus(Integer kycStatus) {
            this.kycStatus = kycStatus;
        }

        public Integer getListed() {
            return listed;
        }

        public void setListed(Integer listed) {
            this.listed = listed;
        }

        public Integer getBlocked() {
            return blocked;
        }

        public void setBlocked(Integer blocked) {
            this.blocked = blocked;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public Integer getBusinessType() {
            return businessType;
        }

        public void setBusinessType(Integer businessType) {
            this.businessType = businessType;
        }

        public String getBusinessTypeName() {
            return businessTypeName;
        }

        public void setBusinessTypeName(String businessTypeName) {
            this.businessTypeName = businessTypeName;
        }

        public String getCategoryEnglish() {
            return categoryEnglish;
        }

        public void setCategoryEnglish(String categoryEnglish) {
            this.categoryEnglish = categoryEnglish;
        }

        public String getCategoryHindi() {
            return categoryHindi;
        }

        public void setCategoryHindi(String categoryHindi) {
            this.categoryHindi = categoryHindi;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Float getSettlementRate() {
            return settlementRate;
        }

        public void setSettlementRate(Float settlementRate) {
            this.settlementRate = settlementRate;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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

    }

}