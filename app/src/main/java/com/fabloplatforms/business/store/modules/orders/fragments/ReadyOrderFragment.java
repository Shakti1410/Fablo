package com.fabloplatforms.business.store.modules.orders.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentReadyOrderBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;
import com.fabloplatforms.business.store.modules.orders.adapters.ReadyOrdersAdapter;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReadyOrderFragment extends Fragment {
    FragmentReadyOrderBinding binding;
    private static final String TAG = "ReadyOrderFragment";

    private Context context;
    private  String storeId;
    private StoreFabloAlert storeFabloAlert;
    private StoreFabloLoading loading;
    //Only for Checking
    ReadyOrdersAdapter readyAdapter;





    public ReadyOrderFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReadyOrderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        context = getContext();
        storeFabloAlert = new StoreFabloAlert();
        loading = new StoreFabloLoading();
        getItem();

    }

    private void getItem() {
        loading.showProgress(context);
        StoreOutletPref pref = new StoreOutletPref(context);
        storeId = pref.getId();
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<PendingOrderResponse> call = orderInterface.readyOrder(storeId);
        call.enqueue(new Callback<PendingOrderResponse>() {
            @Override
            public void onResponse(Call<PendingOrderResponse> call, Response<PendingOrderResponse> response) {

                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        loading.hideProgress();
                        if(!response.body().getItems().isEmpty()) {
                            readyAdapter = new ReadyOrdersAdapter(getContext(), response.body().getItems());
                            DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                            Drawable drawable = context.getResources().getDrawable(R.drawable.divider);
                            decoration.setDrawable(drawable);
                            binding.recycleReady.setLayoutManager(new LinearLayoutManager(context));
                            binding.recycleReady.addItemDecoration(decoration);
                            binding.recycleReady.setAdapter(readyAdapter);

                        }



                    }
                }
            }

            @Override
            public void onFailure(Call<PendingOrderResponse> call, Throwable t) {
                loading.hideProgress();
                Log.d(TAG, "onFailure: "+ t.getMessage());
                if (t instanceof NoConnectivityException) {
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });
    }

    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        readyAdapter = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}