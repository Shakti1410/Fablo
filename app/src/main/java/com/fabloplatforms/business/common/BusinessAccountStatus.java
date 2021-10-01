package com.fabloplatforms.business.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessAccountStatus {

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
    public BusinessAccountStatus() {
    }

    /**
     * @param message
     * @param error
     * @param items
     * @param status
     */
    public BusinessAccountStatus(Boolean status, String message, String error, Items items) {
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

        @SerializedName("business")
        @Expose
        private Integer business;
        @SerializedName("contactPerson")
        @Expose
        private Integer contactPerson;
        @SerializedName("kyc")
        @Expose
        private Integer kyc;
        @SerializedName("Bank")
        @Expose
        private Integer bank;

        /**
         * No args constructor for use in serialization
         *
         */
        public Items() {
        }

        /**
         *
         * @param bank
         * @param business
         * @param kyc
         * @param contactPerson
         */
        public Items(Integer business, Integer contactPerson, Integer kyc, Integer bank) {
            super();
            this.business = business;
            this.contactPerson = contactPerson;
            this.kyc = kyc;
            this.bank = bank;
        }

        public Integer getBusiness() {
            return business;
        }

        public void setBusiness(Integer business) {
            this.business = business;
        }

        public Integer getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(Integer contactPerson) {
            this.contactPerson = contactPerson;
        }

        public Integer getKyc() {
            return kyc;
        }

        public void setKyc(Integer kyc) {
            this.kyc = kyc;
        }

        public Integer getBank() {
            return bank;
        }

        public void setBank(Integer bank) {
            this.bank = bank;
        }

    }

}