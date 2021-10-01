package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ifsc {

    @SerializedName("STATE")
    @Expose
    private String state;
    @SerializedName("BRANCH")
    @Expose
    private String branch;
    @SerializedName("CITY")
    @Expose
    private String city;
    @SerializedName("RTGS")
    @Expose
    private Boolean rtgs;
    @SerializedName("IMPS")
    @Expose
    private Boolean imps;
    @SerializedName("DISTRICT")
    @Expose
    private String district;
    @SerializedName("MICR")
    @Expose
    private String micr;
    @SerializedName("NEFT")
    @Expose
    private Boolean neft;
    @SerializedName("SWIFT")
    @Expose
    private String swift;
    @SerializedName("CONTACT")
    @Expose
    private String contact;
    @SerializedName("ADDRESS")
    @Expose
    private String address;
    @SerializedName("CENTRE")
    @Expose
    private String centre;
    @SerializedName("UPI")
    @Expose
    private Boolean upi;
    @SerializedName("BANK")
    @Expose
    private String bank;
    @SerializedName("BANKCODE")
    @Expose
    private String bankcode;
    @SerializedName("IFSC")
    @Expose
    private String ifsc;

    /**
     * No args constructor for use in serialization
     */
    public Ifsc() {
    }

    /**
     * @param address
     * @param city
     * @param centre
     * @param branch
     * @param upi
     * @param bank
     * @param imps
     * @param neft
     * @param district
     * @param contact
     * @param micr
     * @param state
     * @param rtgs
     * @param ifsc
     * @param bankcode
     * @param swift
     */
    public Ifsc(String state, String branch, String city, Boolean rtgs, Boolean imps, String district, String micr, Boolean neft, String swift, String contact, String address, String centre, Boolean upi, String bank, String bankcode, String ifsc) {
        super();
        this.state = state;
        this.branch = branch;
        this.city = city;
        this.rtgs = rtgs;
        this.imps = imps;
        this.district = district;
        this.micr = micr;
        this.neft = neft;
        this.swift = swift;
        this.contact = contact;
        this.address = address;
        this.centre = centre;
        this.upi = upi;
        this.bank = bank;
        this.bankcode = bankcode;
        this.ifsc = ifsc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getRtgs() {
        return rtgs;
    }

    public void setRtgs(Boolean rtgs) {
        this.rtgs = rtgs;
    }

    public Boolean getImps() {
        return imps;
    }

    public void setImps(Boolean imps) {
        this.imps = imps;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public Boolean getNeft() {
        return neft;
    }

    public void setNeft(Boolean neft) {
        this.neft = neft;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre;
    }

    public Boolean getUpi() {
        return upi;
    }

    public void setUpi(Boolean upi) {
        this.upi = upi;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

}