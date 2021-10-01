package com.fabloplatforms.business.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fabloplatforms.business.common.Business;

public class TempPref {
    private Context context;


    public TempPref(Context context) {
        this.context = context;
    }

    public void setId(TempModel business) {
        if (business != null) {


            SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("id", business.getId());


            editor.apply();
        }
    }

    public String getId() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        return preferences.getString("id", "none");
    }
}
