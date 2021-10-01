package com.fabloplatforms.business.store.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fabloplatforms.business.store.models.OutletStatus;


public class StoreOutletStatusPref {

    private Context context;

    public StoreOutletStatusPref(Context context) {
        this.context = context;
    }

    public void setOutletStatus(OutletStatus outletStatus) {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET_STATUS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("online", outletStatus.isOnline());
        editor.apply();
    }

    public boolean getOutletStatus() {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET_STATUS, Context.MODE_PRIVATE);
        return preferences.getBoolean("online", false);
    }

}
