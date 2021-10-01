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
import com.fabloplatforms.business.databinding.BottomSheetStateBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.LocationInterface;
import com.fabloplatforms.business.onboard.adapter.CityRecyclerAdapter;
import com.fabloplatforms.business.onboard.adapter.StateRecyclerAdapter;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.onboard.model.StateResponse;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateBottomSheet extends BottomSheetDialogFragment {
    BottomSheetStateBinding binding;
    private static final String TAG = "StateBottomSheet";
    private String countryId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetStateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        Bundle mArgs = getArguments();
        countryId = mArgs.getString("countryId");
        binding.recyclerState.setLayoutManager(new LinearLayoutManager(getContext()));
        if(countryId!=null)
        {
            fetchState();
        }

    }

    private void fetchState() {
        LocationInterface locationInterface = RestClient.getRetrofitServiceLoacation(getContext()).create(LocationInterface.class);
        Call<StateResponse> call = locationInterface.getState(countryId);
        call.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                Log.e(TAG, "onResponse: "+response);
                if (response.code() == 200) {

                    if (response.body() != null) {
                        StateRecyclerAdapter recyclerAdapter = new StateRecyclerAdapter(getContext(), response.body().getItems());
                        recyclerAdapter.notifyDataSetChanged();
                        binding.recyclerState.setAdapter(recyclerAdapter);
                        binding.progressState.setVisibility(View.GONE);
                        binding.recyclerState.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
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
