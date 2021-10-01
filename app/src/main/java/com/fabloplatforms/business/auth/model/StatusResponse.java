package com.fabloplatforms.business.auth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusResponse {

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

        @SerializedName("BasicDetails")
        @Expose
        private Integer basicDetails;
        @SerializedName("ContactDetails")
        @Expose
        private Integer contactDetails;
        @SerializedName("BankDetails")
        @Expose
        private Integer bankDetails;
        @SerializedName("TaxDetails")
        @Expose
        private Integer taxDetails;
        @SerializedName("FssaiDetails")
        @Expose
        private Integer fssaiDetails;
        @SerializedName("KycDetails")
        @Expose
        private Integer kycDetails;
        @SerializedName("CommissionDetails")
        @Expose
        private Integer commissionDetails;
        @SerializedName("eSignatureDetails")
        @Expose
        private Integer eSignatureDetails;

        public Integer getBasicDetails() {
            return basicDetails;
        }

        public void setBasicDetails(Integer basicDetails) {
            this.basicDetails = basicDetails;
        }

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
