package com.fabloplatforms.business.store.modules.outlet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.fabloplatforms.business.databinding.ActivityOrderHistoryBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    ActivityOrderHistoryBinding binding;
    private static final String TAG = "OrderHistoryActivity";
    OrderHistoryAdapter adapter;
    private StoreFabloLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = OrderHistoryActivity.this;
        initView();

    }

    private void initView() {

        binding.ivBack.setOnClickListener(this);

        loading = new StoreFabloLoading();
        getItem();


    }
    private void getItem() {
        loading.showProgress(context);
        StoreOutletPref pref = new StoreOutletPref(context);
        String storeId = pref.getId();
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<PendingOrderResponse> call = orderInterface.getAllOrder(storeId);
        call.enqueue(new Callback<PendingOrderResponse>() {
            @Override
            public void onResponse(Call<PendingOrderResponse> call, Response<PendingOrderResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        loading.hideProgress();
                        adapter = new OrderHistoryAdapter(context,response.body().getItems());
//                        DividerItemDecoration decoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
//                        Drawable drawable = context.getResources().getDrawable(R.drawable.divider);
//                        decoration.setDrawable(drawable);
//                        binding.recycleOrderHistory.addItemDecoration(decoration);
                        binding.recycleOrderHistory.setLayoutManager(new LinearLayoutManager(context));
                        binding.recycleOrderHistory.setAdapter(adapter);



                    }
                } else {
                    loading.hideProgress();
                    Log.d(TAG, "onResponse: "+response.message());

                }
            }

            @Override
            public void onFailure(Call<PendingOrderResponse> call, Throwable t) {
                loading.hideProgress();
                Log.d(TAG, "onFailure: "+t.getMessage());
                if (t instanceof NoConnectivityException) {
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.ivBack) {
            onBackPressed();
        }
    }
}