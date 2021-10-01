package com.fabloplatforms.business.store.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreSessionPref {

    Context context;

    public StoreSessionPref(Context context) {
        this.context = context;
    }

    public void createSession(StoreSession storeSession) {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("restaurantId", storeSession.getRestaurantId());
        editor.putInt("status", storeSession.getStatus());
        editor.apply();

    }

    public String getRestaurantId() {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_SESSION, Context.MODE_PRIVATE);
        return preferences.getString("restaurantId", StoreConstant.PREF_DEFAULT);
    }

    public Integer getStatus() {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_SESSION, Context.MODE_PRIVATE);
        return preferences.getInt("status", StoreConstant.SESSION_INACTIVE);
    }

}
