package com.fabloplatforms.business.store.modules.orders.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentAcceptOrderBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;
import com.fabloplatforms.business.store.modules.orders.adapters.AcceptOdersAdapter;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AcceptOrderFragment extends BottomSheetDialogFragment {

    FragmentAcceptOrderBinding binding;
    private Context context;
    private StoreFabloLoading loading;
    private StoreFabloAlert storeFabloAlert;
    private static final String TAG = "AcceptOrderFragment";
//Only for Checking





    public AcceptOrderFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAcceptOrderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {


        storeFabloAlert = new StoreFabloAlert();
        loading = new StoreFabloLoading();
        getItem();


//        binding.nav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    dismiss();
//            }
//        });
    }

    private void getItem() {
        loading.showProgress(context);
        StoreOutletPref pref = new StoreOutletPref(context);
        String store_id = pref.getId();
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<PendingOrderResponse> call = orderInterface.pendingOrder(store_id);
        call.enqueue(new Callback<PendingOrderResponse>() {
            @Override
            public void onResponse(Call<PendingOrderResponse> call, Response<PendingOrderResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        loading.hideProgress();
                        if(!response.body().getItems().isEmpty())
                        {

                            binding.recyclerOrders.setLayoutManager(new LinearLayoutManager(context));
                            AcceptOdersAdapter acceptAdapter = new AcceptOdersAdapter(getContext(), response.body().getItems());
                            DividerItemDecoration decoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                            Drawable drawable = context.getResources().getDrawable(R.drawable.divider);
                            decoration.setDrawable(drawable);
                            binding.recyclerOrders.addItemDecoration(decoration);
                            binding.recyclerOrders.setAdapter(acceptAdapter);

                        }



                    }
                } else {
                    loading.hideProgress();
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PendingOrderResponse> call, Throwable t) {
                loading.hideProgress();
                Log.d(TAG, "onFailure: "+t.getMessage());
                if (t instanceof NoConnectivityException){
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });




     /*  ModelOrderTemp orderTemp = new ModelOrderTemp("1001","Monday","10:05","AM","Shakti","9898989898","2000",10);
       orderArrayList.add(orderTemp);
        orderArrayList.add(orderTemp);
        orderArrayList.add(orderTemp);
       OrderItemTemp item = new OrderItemTemp("2","Biryani",440);
        OrderItemTemp item1 = new OrderItemTemp("2","Biryani",440);
        itemArrayList.add(item);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);*/
    }
    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}