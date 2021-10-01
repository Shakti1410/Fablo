package com.fabloplatforms.business.store.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.OrderCancelAlertBinding;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.acceptorder.AcceptOrderResponse;
import com.fabloplatforms.business.store.models.acceptorder.RejectOrderRequest;
import com.fabloplatforms.business.store.models.acceptorder.RejectReasonResponse;
import com.fabloplatforms.business.store.modules.orders.adapters.RejectReasonAdapter;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreOrderCancelAlert {
    OrderCancelAlertBinding binding;
    public static StoreOrderCancelAlert alert = null;
    private Dialog mDialog;
    private View myView;

    private StoreFabloAlert storeFabloAlert;


    public static StoreOrderCancelAlert getInstance(){
        if (alert == null) {
            alert = new StoreOrderCancelAlert();
        }

        return alert;
    }

    public void showAlert(Context context,String orderid,String uid, int status, boolean goBack, String title, String description, String tag) {
        mDialog = new Dialog(context);
        storeFabloAlert = StoreFabloAlert.getInstance();
        context.setTheme(R.style.Theme_FabloTrans);
        binding = OrderCancelAlertBinding.inflate(mDialog.getLayoutInflater());
        View view = binding.getRoot();
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        if (status == StoreConstant.ALERT_SUCCESS) {
            binding.lottieStatusOrdercancel.setAnimation(R.raw.logout);
            binding.lottieStatusOrdercancel.playAnimation();
        } else if (status == StoreConstant.ALERT_ERROR) {
            binding.lottieStatusOrdercancel.setAnimation(R.raw.logout);
            binding.lottieStatusOrdercancel.playAnimation();
        }
          getReason(context);
        binding.tvSelectReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.recyclerReason.getVisibility() == View.GONE)
                {
                    binding.tverror.setVisibility(View.GONE);
                    binding.recyclerReason.setVisibility(View.VISIBLE);
                }
                else
                {
                    binding.recyclerReason.setVisibility(View.GONE);
                }

            }
        });
        binding.tvTitleOrdercancel.setText(title);
        binding.recyclerReason.setLayoutManager(new LinearLayoutManager(context));

        binding.btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("Reason");
                editor.apply();
                hideAlert();


            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String reason = preferences.getString("Reason", "");
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("Reason");
                editor.apply();
                if(!reason.equalsIgnoreCase(""))
                {

                   cancelOrder(orderid,uid, reason, context,view);
                    hideAlert();

                }
                else
                {
                    binding.tverror.setVisibility(View.VISIBLE);
                }

               // signOut(context);
              //  EventBus.getDefault().post(tag);

            }
        });

        mDialog.show();

    }

    private void cancelOrder(String orderid,String uid, String reason, Context context, View v) {
        RejectOrderRequest request = new RejectOrderRequest();
        request.setUserId(uid);
        request.setReason(reason);

        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<AcceptOrderResponse> call = orderInterface.orderReject(orderid,request);
        call.enqueue(new Callback<AcceptOrderResponse>() {
            @Override
            public void onResponse(Call<AcceptOrderResponse> call, Response<AcceptOrderResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {


                    Toast.makeText(context, "Order Rejected Successfully", Toast.LENGTH_SHORT).show();

                    Log.d("Reject Order", "onResponse: "+ response.message());

                }
            }

            @Override
            public void onFailure(Call<AcceptOrderResponse> call, Throwable t) {
                Log.d("OrderCancel", "onFailure: "+ t.getMessage());
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(View v) {
        if (v != null) {
            myView = v;
        }
    }

    private void getReason(Context context) {
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<RejectReasonResponse> call = orderInterface.rejectReason();
        call.enqueue(new Callback<RejectReasonResponse>() {
            @Override
            public void onResponse(Call<RejectReasonResponse> call, Response<RejectReasonResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        // loading.hideProgress();
                        binding.progress.setVisibility(View.GONE);

                        RejectReasonAdapter rejectAdapter = new RejectReasonAdapter(context, response.body().getItems());
                        DividerItemDecoration decoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                        Drawable drawable = context.getResources().getDrawable(R.drawable.divider);
                        decoration.setDrawable(drawable);
                        binding.recyclerReason.addItemDecoration(decoration);
                        binding.recyclerReason.setAdapter(rejectAdapter);


                    }
                } else {
                    Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RejectReasonResponse> call, Throwable t) {

            }
        });
    }

    public void hideAlert() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
