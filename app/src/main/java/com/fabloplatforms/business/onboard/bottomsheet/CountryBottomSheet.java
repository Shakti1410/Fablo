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
import com.fabloplatforms.business.databinding.BottomSheetCountryBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.LocationInterface;
import com.fabloplatforms.business.onboard.adapter.CityRecyclerAdapter;
import com.fabloplatforms.business.onboard.adapter.CountryRecyclerAdapter;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.onboard.model.Country;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryBottomSheet extends BottomSheetDialogFragment {
    BottomSheetCountryBinding binding;
    private static final String TAG = "CountryBottomSheet";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetCountryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        binding.recyclerCountry.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchCountry();
    }

    private void fetchCountry() {
        LocationInterface locationInterface = RestClient.getRetrofitServiceLoacation(getContext()).create(LocationInterface.class);
        Call<Country> call = locationInterface.getCountries();
        call.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                Log.e(TAG, "onResponse: "+response);
                if (response.code() == 200) {

                    if (response.body() != null) {
                        CountryRecyclerAdapter recyclerAdapter = new CountryRecyclerAdapter(getContext(),response.body().getItems());
                        recyclerAdapter.notifyDataSetChanged();
                        binding.recyclerCountry.setAdapter(recyclerAdapter);
                        binding.progressCountry.setVisibility(View.GONE);
                        binding.recyclerCountry.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {
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
