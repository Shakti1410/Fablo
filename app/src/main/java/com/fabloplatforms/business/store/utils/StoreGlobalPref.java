package com.fabloplatforms.business.store.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreGlobalPref {

    private Context context;

    public StoreGlobalPref(Context context) {
        this.context = context;
    }

    public void setGlobal(StoreGlobal storeGlobal) {
        SharedPreferences preferences = context.getSharedPreferences("global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("appVersion", storeGlobal.getAppVersion());
        editor.apply();
    }

    public String getAppVersion() {
        SharedPreferences preferences = context.getSharedPreferences("global", Context.MODE_PRIVATE);
        return preferences.getString("appVersion", "none");
    }

}
