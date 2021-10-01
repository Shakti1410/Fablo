package com.fabloplatforms.business.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fabloplatforms.business.common.Business;

public class BusinessPref {

    private Context context;

    public BusinessPref(Context context) {
        this.context = context;
    }

    public void setBusiness(Business business) {
        if (business != null) {
            Business.Items businessDetails = business.getItems();
            SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("id", businessDetails.getId());
            editor.putInt("settlement_type", businessDetails.getSettlementType());
            editor.putFloat("wallet", businessDetails.getWallet());
            editor.putFloat("credit", businessDetails.getCredit());
            editor.putString("qr", businessDetails.getQr());
            editor.putInt("kyc_status", businessDetails.getKycStatus());
            editor.putInt("listed", businessDetails.getListed());
            editor.putInt("blocked", businessDetails.getBlocked());
            editor.putString("business_name", businessDetails.getBusinessName());
            editor.putInt("business_type", businessDetails.getBusinessType());
            editor.putString("business_type_name", businessDetails.getBusinessTypeName());
            editor.putString("category_english", businessDetails.getCategoryEnglish());
            editor.putString("category_hindi", businessDetails.getCategoryHindi());
            editor.putString("category_id", businessDetails.getCategoryId());
            editor.putString("city_id", businessDetails.getCityId());
            editor.putString("city_name", businessDetails.getCityName());
            editor.putString("phone", businessDetails.getPhone());
            editor.putFloat("settlement_rate", businessDetails.getSettlementRate());
            editor.putString("token", businessDetails.getToken());
            editor.putInt("paylater_payment_limit", businessDetails.getPaylaterPaymentLimit());
            editor.putInt("other_payment_limit", businessDetails.getOtherPaymentLimit());

            editor.apply();
        }
    }

    public String getId() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("id", "none");
    }

    public Integer getSettlementType() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getInt("settlement_type", 0);
    }

    public Float getWallet() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getFloat("wallet", 0);
    }

    public Float getCredit() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getFloat("credit", 0);
    }

    public String getQr() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("qr", "none");
    }

    public Integer getKycStatus() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getInt("kyc_status", 0);
    }

    public Integer getListed() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getInt("listed", 0);
    }

    public Integer getBlocked() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getInt("blocked", 0);
    }

    public String getBusinessName() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("business_name", "none");
    }

    public Integer getBusinessType() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getInt("business_type", 0);
    }

    public String getBusinessTypeName() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("business_type_name", "none");
    }

    public String getCategoryEnglish() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("category_english", "none");
    }

    public String getCategoryHindi() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("category_hindi", "none");
    }

    public String getCategoryId() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("category_id", "none");
    }

    public String getCityId() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("city_id", "none");
    }

    public String getCityName() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("city_name", "none");
    }

    public String getPhone() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("phone", "none");
    }

    public Float getSettlementRate() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getFloat("phone", 0);
    }

    public String getToken() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("token", "none");
    }

    public Integer getPaylaterPaymentLimit() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getInt("paylater_payment_limit", 0);
    }

    public Integer getOtherPaymentLimit() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getInt("other_payment_limit", 0);
    }

}
