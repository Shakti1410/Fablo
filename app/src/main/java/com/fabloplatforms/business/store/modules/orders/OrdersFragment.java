package com.fabloplatforms.business.store.modules.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentOrdersBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.OrderCountResponse;
import com.fabloplatforms.business.store.models.OrdersCount;
import com.fabloplatforms.business.store.models.OutletStatus;
import com.fabloplatforms.business.store.modules.orders.fragments.PickedOrderFragment;
import com.fabloplatforms.business.store.modules.orders.fragments.PreparingOrderFragment;
import com.fabloplatforms.business.store.modules.orders.fragments.ReadyOrderFragment;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.store.utils.StoreOutletStatusPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment implements View.OnClickListener {

    FragmentOrdersBinding binding;
    private Context context;
    private StoreFabloLoading storeFabloLoading;
    private static final String TAG ="OrdersFragment";
    private StoreFabloAlert storeFabloAlert;
    private Integer preOrder=0,readyOrder=0,pickedOrder=0;
    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

    public OrdersFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        storeFabloLoading = StoreFabloLoading.getInstance();
        storeFabloAlert = new StoreFabloAlert();
     //   orderNumberUpdate();
        checkOutletStatus();

        return view;
    }
    private void checkOutletStatus() {
        StoreOutletStatusPref storeOutletStatusPref = new StoreOutletStatusPref(context);
        if (storeOutletStatusPref.getOutletStatus()) {
            binding.orderType.setVisibility(View.VISIBLE);
            binding.frameSetup.setVisibility(View.VISIBLE);
            binding.storeOffline.setVisibility(View.GONE);
            initView();
        } else {
            binding.frameSetup.setVisibility(View.GONE);
            binding.orderType.setVisibility(View.GONE);
            binding.storeOffline.setVisibility(View.VISIBLE);

        }
    }
    private void initView() {



        binding.layoutPreparing.setOnClickListener(this);
        binding.layoutReady.setOnClickListener(this);
        binding.layoutDispatched.setOnClickListener(this);
        showPreparing();
        getOrderNumber();
//        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.ting);
//        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.start();
//            }
//        });


    }
    private void orderNumberUpdate() {
        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if(key.equals("acceptOrder"))
                {
                //   getOrderNumber();
                    SharedPreferences mPrefs = context.getSharedPreferences("OrderCount",0);
                    String s = binding.tvPreparing.getText().toString();
                    preOrder = Integer.parseInt(s);
                    preOrder = preOrder+1;
                    setPreNumber(preOrder);
                }
                if(key.equals("preOrder"))
                {
                 //  getOrderNumber();
                    SharedPreferences mPrefs = context.getSharedPreferences("OrderCount",0);
                    preOrder = mPrefs.getInt("preOrder",0);
                    String s = binding.tvReady.getText().toString();
                    readyOrder = Integer.parseInt(s);
                    readyOrder = readyOrder+1;
                    setPreReadyNumber(preOrder,readyOrder);
                }
                if(key.equals("readyOrder"))
                {
                 //   getOrderNumber();
                    SharedPreferences mPrefs = context.getSharedPreferences("OrderCount",0);
                    readyOrder = mPrefs.getInt("readyOrder",0);
                    String s = binding.tvPickedUp.getText().toString();
                    pickedOrder = Integer.parseInt(s);
                    pickedOrder = pickedOrder+1;
                    setReadyDispNumber(readyOrder,pickedOrder);
                }
                if(key.equals("pickedOrder"))
                {
                //  getOrderNumber();
                    SharedPreferences mPrefs = context.getSharedPreferences("OrderCount",0);
                    pickedOrder = mPrefs.getInt("pickedOrder",0);
                    setDisNumber(pickedOrder);
                }
            }
        };
        SharedPreferences sharedPreferences = context.getSharedPreferences("OrderCount",0);
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

    }

    private void setDisNumber(Integer pickedOr) {
        binding.tvPickedUp.setText(String.valueOf(pickedOr));
    }

    private void setReadyDispNumber(Integer readyOr, Integer pickedOr) {

        binding.tvReady.setText(String.valueOf(readyOr));
        binding.tvPickedUp.setText(String.valueOf(pickedOr));
    }

    private void setPreReadyNumber(Integer preOr, Integer readyOr) {
        binding.tvPreparing.setText(String.valueOf(preOr));
        binding.tvReady.setText(String.valueOf(readyOr));
    }

    private void setPreNumber(Integer pre) {
        binding.tvPreparing.setText(String.valueOf(pre));
    }

    @Override
    public void onClick(View v) {
        if (v == binding.layoutPreparing) {
            showPreparing();
        }
        if (v == binding.layoutReady) {
            showReady();
        }
        if (v == binding.layoutDispatched) {
            showPickedUp();
        }
    }
    private void getOrderNumber() {
        storeFabloLoading.showProgress(context);
        StoreOutletPref pref = new StoreOutletPref(context);
        String storeId = pref.getId();
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<OrderCountResponse> call = orderInterface.getOrderCount(storeId);
        call.enqueue(new Callback<OrderCountResponse>() {
            @Override
            public void onResponse(Call<OrderCountResponse> call, Response<OrderCountResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        storeFabloLoading.hideProgress();
                        preOrder = response.body().getItems().getPreparingOrders();
                        readyOrder = response.body().getItems().getReadyOrders();
                        pickedOrder = response.body().getItems().getDispatchedOrders();
                        Log.d(TAG, "onResponse: "+ preOrder+ readyOrder+pickedOrder);
                        setOrderNumber();
                    }
                } else {
                    storeFabloLoading.hideProgress();
                    Log.d(TAG, "onResponse: "+response.message());

                }
            }

            @Override
            public void onFailure(Call<OrderCountResponse> call, Throwable t) {
               storeFabloLoading.hideProgress();
                Log.d(TAG, "onFailure: "+t.getMessage());
                if (t instanceof NoConnectivityException) {
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });

    }

    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }


    private void setOrderNumber() {

        binding.tvPreparing.setText(String.valueOf(preOrder));
        binding.tvReady.setText(String.valueOf(readyOrder));
        binding.tvPickedUp.setText(String.valueOf(pickedOrder));

    }

    private void showPreparing() {
        binding.layoutPreparing.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_green));
       // binding.tvPreparing.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_green));
        binding.textPreparing.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        binding.tvPreparing.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));

        binding.layoutReady.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
      //  binding.tvReady.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
        binding.tvReady.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        binding.textReady.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));

        binding.layoutDispatched.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
      //  binding.tvPickedUp.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
        binding.tvPickedUp.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        binding.textdispatched.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));

        setFragment(new PreparingOrderFragment());
    }

    private void showReady() {
        binding.layoutPreparing.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
    //    binding.tvPreparing.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
        binding.tvPreparing.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        binding.textPreparing.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));

        binding.layoutReady.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_green));
     //   binding.tvReady.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_green));
        binding.tvReady.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        binding.textReady.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));

        binding.layoutDispatched.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
     //   binding.tvPickedUp.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
        binding.tvPickedUp.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        binding.textdispatched.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        setFragment(new ReadyOrderFragment());
       // getOrder();
    }

    private void showPickedUp() {
        binding.layoutPreparing.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
      //  binding.tvPreparing.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
        binding.tvPreparing.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        binding.textPreparing.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));

        binding.layoutReady.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
      //  binding.tvReady.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
        binding.tvReady.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        binding.textReady.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));

        binding.layoutDispatched.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_green));
       // binding.tvPickedUp.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_green));
        binding.tvPickedUp.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        binding.textdispatched.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        setFragment(new PickedOrderFragment());
        //getOrder();
    }

    private void getOrder() {
        storeFabloLoading.showProgress(context);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                storeFabloLoading.hideProgress();
            }
        }, 200);
    }
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameSetup, fragment);
        fragmentTransaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrdersCount count) {
        if (count != null) {
            initView();
          // getOrderNumber();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}