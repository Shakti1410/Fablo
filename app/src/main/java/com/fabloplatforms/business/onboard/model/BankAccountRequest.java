package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankAccountRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("beneficiary_name")
    @Expose
    private String beneficiaryName;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("document")
    @Expose
    private Document document;
    @SerializedName("app_version")
    @Expose
    private String appVersion;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}