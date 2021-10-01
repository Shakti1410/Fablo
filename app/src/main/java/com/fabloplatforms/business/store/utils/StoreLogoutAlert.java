package com.fabloplatforms.business.store.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.auth.PhoneActivity;
import com.fabloplatforms.business.databinding.LogoutAlertBinding;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

public class StoreLogoutAlert {
    LogoutAlertBinding binding;
    public static StoreLogoutAlert storeLogoutAlert = null;
    private Dialog mDialog;


    public static StoreLogoutAlert getInstance(){
        if (storeLogoutAlert == null) {
            storeLogoutAlert = new StoreLogoutAlert();
        }

        return storeLogoutAlert;
    }

    public void showAlert(Context context, int status, boolean goBack, String title, String description, String tag) {
        mDialog = new Dialog(context);
        context.setTheme(R.style.Theme_FabloTrans);
        binding = LogoutAlertBinding.inflate(mDialog.getLayoutInflater());
        View view = binding.getRoot();
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        if (status == StoreConstant.ALERT_SUCCESS) {
            binding.lottieStatus.setAnimation(R.raw.logout);
            binding.lottieStatus.playAnimation();
        } else if (status == StoreConstant.ALERT_ERROR) {
            binding.lottieStatus.setAnimation(R.raw.logout);
            binding.lottieStatus.playAnimation();
        }

        binding.tvTitle.setText(title);
        binding.tvDescription.setText(description);

        binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAlert();
//                if (goBack) {
//                    hideAlert();
//                    EventBus.getDefault().post(tag);
//                } else {
//                    hideAlert();
//                }
            }
        });
        binding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    hideAlert();
                    signOut(context);
                    EventBus.getDefault().post(tag);

            }
        });

        mDialog.show();
    }

    private void signOut(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        Intent intent = new Intent(context, PhoneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null) {
//            firebaseAuth.signOut();
//            SharedPreferences preferences = context.getSharedPreferences(StoreConstant.PREF_OUTLET, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
//            editor.apply();
//
//        }
    }


    public void hideAlert() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
