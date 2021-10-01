package com.fabloplatforms.business.store.models.pendingorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("promoCode")
    @Expose
    private String promoCode;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("orderPrice")
    @Expose
    private Double orderPrice;
    @SerializedName("final_payment")
    @Expose
    private Double finalPayment;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payment_sub_type")
    @Expose
    private String paymentSubType;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("pay_time")
    @Expose
    private String payTime;

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getFinalPayment() {
        return finalPayment;
    }

    public void setFinalPayment(Double finalPayment) {
        this.finalPayment = finalPayment;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentSubType() {
        return paymentSubType;
    }

    public void setPaymentSubType(String paymentSubType) {
        this.paymentSubType = paymentSubType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

}