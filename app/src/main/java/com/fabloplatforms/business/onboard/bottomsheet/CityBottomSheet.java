package com.fabloplatforms.business.onboard.bottomsheet;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fabloplatforms.business.databinding.BottomSheetCityBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.LocationInterface;
import com.fabloplatforms.business.onboard.adapter.CityRecyclerAdapter;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityBottomSheet extends BottomSheetDialogFragment {

    BottomSheetCityBinding binding;
    private static final String TAG = "CityBottomSheet";
    private String stateId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetCityBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        Bundle mArgs = getArguments();
        stateId = mArgs.getString("stateId");
        binding.recyclerCity.setLayoutManager(new LinearLayoutManager(getContext()));
        if(stateId!=null) {
            fetchCity();
        }
    }

    private void fetchCity() {
        LocationInterface locationInterface = RestClient.getRetrofitServiceLoacation(getContext()).create(LocationInterface.class);
        Call<City> call = locationInterface.getCities(stateId);
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(@NonNull Call<City> call, @NonNull Response<City> response) {
                Log.e(TAG, "onResponse: "+response);
                if (response.code() == 200) {

                    if (response.body() != null) {
                        CityRecyclerAdapter cityRecyclerAdapter = new CityRecyclerAdapter(getContext(), response.body().getItems());
                        cityRecyclerAdapter.notifyDataSetChanged();
                        binding.recyclerCity.setAdapter(cityRecyclerAdapter);
                        binding.progress.setVisibility(View.GONE);
                        binding.recyclerCity.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<City> call, @NonNull Throwable t) {
                if (t instanceof NoConnectivityException){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 1000);
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });

    }

}
