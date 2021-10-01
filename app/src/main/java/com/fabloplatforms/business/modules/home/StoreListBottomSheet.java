package com.fabloplatforms.business.modules.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.BottomSheetCityBinding;
import com.fabloplatforms.business.databinding.BottomSheetStorelistBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BusinessInterface;
import com.fabloplatforms.business.modules.model.StoreListResponse;
import com.fabloplatforms.business.onboard.adapter.CityRecyclerAdapter;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreGlobalError;
import com.fabloplatforms.business.utils.BusinessPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloLoading;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreListBottomSheet extends BottomSheetDialogFragment {
    BottomSheetStorelistBinding binding;
    private FabloLoading fabloLoading;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetStorelistBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {
        fabloLoading = new FabloLoading();
        binding.recyclerStoreList.setLayoutManager(new LinearLayoutManager(getContext()));

            fetchStore();

    }

    private void fetchStore() {

        BusinessPref businessPref = new BusinessPref(context);
        String id = "6155a94e9cc4c4fb6fe4fe06";//businessPref.getId();
        BusinessInterface businessInterface = RestClient.getRetrofitServiceInstance(context).create(BusinessInterface.class);
        Call<StoreListResponse> call = businessInterface.getAllStore(id);
        call.enqueue(new Callback<StoreListResponse>() {
            @Override
            public void onResponse(Call<StoreListResponse> call, Response<StoreListResponse> response) {
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {

                        StoreListRecyclerAdapter recyclerAdapter = new StoreListRecyclerAdapter(getContext(),response.body().getItems());
                        recyclerAdapter.notifyDataSetChanged();
                        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                        Drawable drawable = context.getResources().getDrawable(R.drawable.divider);
                        decoration.setDrawable(drawable);
                        binding.recyclerStoreList.addItemDecoration(decoration);
                        binding.recyclerStoreList.setAdapter(recyclerAdapter);
                        binding.progressStore.setVisibility(View.GONE);
                        binding.recyclerStoreList.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<StoreListResponse> call, Throwable t) {

                if (t instanceof NoConnectivityException) {
                    StoreGlobalError storeGlobalError = new StoreGlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", StoreConstant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(storeGlobalError);
                }
            }
        });
    }
}
