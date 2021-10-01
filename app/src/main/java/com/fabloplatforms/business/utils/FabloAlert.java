package com.fabloplatforms.business.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.DialogAlertBinding;

import org.greenrobot.eventbus.EventBus;

public class FabloAlert {

    DialogAlertBinding binding;
    public static  FabloAlert fabloAlert = null;
    private Dialog mDialog;

    public static FabloAlert getInstance() {
        if (fabloAlert == null) {
            fabloAlert = new FabloAlert();
        }
        return fabloAlert;
    }

    public void showAlert(Context context, int status, boolean goBack, String title, String description, String tag) {
        mDialog = new Dialog(context);
        context.setTheme(R.style.Theme_FabloTrans);
        binding = DialogAlertBinding.inflate(mDialog.getLayoutInflater());
        View view = binding.getRoot();
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        if (status == Constant.ALERT_SUCCESS) {
            binding.lottieStatus.setAnimation(R.raw.success);
            binding.lottieStatus.playAnimation();
        } else if (status == Constant.ALERT_ERROR) {
            binding.lottieStatus.setAnimation(R.raw.error);
            binding.lottieStatus.playAnimation();
        }

        binding.tvTitle.setText(title);
        binding.tvDescription.setText(description);

        binding.btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goBack) {
                    hideAlert();
                    EventBus.getDefault().post(tag);
                } else {
                    hideAlert();
                }
            }
        });

        mDialog.show();
    }

    public void hideAlert() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
