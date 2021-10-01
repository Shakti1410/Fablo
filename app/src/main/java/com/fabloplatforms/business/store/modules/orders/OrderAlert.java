package com.fabloplatforms.business.store.modules.orders;

import android.app.Dialog;
import android.content.Context;
import android.view.View;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.OrderAlertBinding;
import com.fabloplatforms.business.store.utils.StoreConstant;

import org.greenrobot.eventbus.EventBus;

public class OrderAlert {


    OrderAlertBinding binding;
    public static OrderAlert orderAlert = null;
    private Dialog mDialog;


    public static OrderAlert getInstance(){
        if (orderAlert == null) {
            orderAlert = new OrderAlert();
        }

        return orderAlert;
    }

    public void showAlert(Context context, int status, boolean goBack, String title, String description, String tag) {
        mDialog = new Dialog(context);
        context.setTheme(R.style.Theme_FabloTrans);
        binding = OrderAlertBinding.inflate(mDialog.getLayoutInflater());
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

        binding.tvTitleOrder.setText(title);
        binding.tvDescriptionOrder.setText(description);

        binding.btnOrderCancel.setOnClickListener(new View.OnClickListener() {
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
        binding.btnOrderAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideAlert();

                EventBus.getDefault().post(tag);

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
