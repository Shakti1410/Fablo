package com.fabloplatforms.business.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.auth.PhoneActivity;
import com.fabloplatforms.business.databinding.LogoutAlertBinding;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreLogoutAlert;

import org.greenrobot.eventbus.EventBus;

public class BusinessLogoutAlert {
    LogoutAlertBinding binding;
    public static BusinessLogoutAlert logoutAlert = null;
    private Dialog mDialog;

    public static BusinessLogoutAlert getInstance(){
        if (logoutAlert == null) {
            logoutAlert = new BusinessLogoutAlert();
        }

        return logoutAlert;
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
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(context, PhoneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    public void hideAlert() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
