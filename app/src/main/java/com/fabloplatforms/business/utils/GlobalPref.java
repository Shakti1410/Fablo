package com.fabloplatforms.business.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalPref {

    private Context context;

    public GlobalPref(Context context) {
        this.context = context;
    }

    public void setGlobal(Global global) {
        SharedPreferences preferences = context.getSharedPreferences("global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("appVersion", global.getAppVersion());
        editor.apply();
    }

    public String getAppVersion() {
        SharedPreferences preferences = context.getSharedPreferences("global", Context.MODE_PRIVATE);
        return preferences.getString("appVersion", "none");
    }

}
