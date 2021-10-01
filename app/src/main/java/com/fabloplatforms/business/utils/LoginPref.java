package com.fabloplatforms.business.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fabloplatforms.business.auth.model.LoginResponse;

public class LoginPref {
    private Context context;

    public LoginPref(Context context) {
        this.context = context;
    }

    public void setLoginPref(LoginResponse response,Global global,String phone)
    {
        SharedPreferences preferences = context.getSharedPreferences(Constant.TOKEN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(response!=null)
        {
            editor.putString("token", response.getItems().getToken());
            editor.putString("phone",phone);
            editor.putString("appVersion", global.getAppVersion());
            editor.apply();
        }

//        SharedPreferences preferences1 = context.getSharedPreferences("global", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor1 = preferences1.edit();
//        editor1
//        editor1.apply();
    }

    public String getToken() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.TOKEN, Context.MODE_PRIVATE);
        return preferences.getString("token", "none");
    }

    public String getAppVersion() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.TOKEN, Context.MODE_PRIVATE);
        return preferences.getString("appVersion", "none");
    }
    public String getPhone() {
        SharedPreferences preferences = context.getSharedPreferences(Constant.TOKEN, Context.MODE_PRIVATE);
        return preferences.getString("phone", "none");
    }
}
