package com.fabloplatforms.business.store.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fabloplatforms.business.auth.model.OutletLoginResponse;


public class StoreOutletPref {
private Context context;

    public StoreOutletPref(Context context) {
        this.context = context;
    }
public void setOutletDetails(OutletLoginResponse outletLoginResponse){
    SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    if(outletLoginResponse!=null)
    {
        editor.putString("id", outletLoginResponse.getItems().getId());
        editor.putString("business_id", outletLoginResponse.getItems().getBusinessId());
        editor.putString("store_name",outletLoginResponse.getItems().getStoreName());
        editor.putString("locality",outletLoginResponse.getItems().getLocality());

        editor.apply();
    }
}
    public String getId() {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET, Context.MODE_PRIVATE);
        return preferences.getString("id", "none");
    }

    public String getBusinessId() {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET, Context.MODE_PRIVATE);
        return preferences.getString("business_id", "none");
    }


    public String getStoreName() {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET, Context.MODE_PRIVATE);
        return preferences.getString("store_name", "none");
    }
    public String getLocality() {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET, Context.MODE_PRIVATE);
        return preferences.getString("locality", "none");
    }


}
