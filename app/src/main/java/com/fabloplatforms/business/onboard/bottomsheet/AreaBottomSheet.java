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

import com.fabloplatforms.business.databinding.BottomSheetAreaBinding;
import com.fabloplatforms.business.databinding.BottomSheetStateBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.LocationInterface;
import com.fabloplatforms.business.onboard.adapter.AreaRecyclerAdapter;
import com.fabloplatforms.business.onboard.adapter.StateRecyclerAdapter;
import com.fabloplatforms.business.onboard.model.AreaResponse;
import com.fabloplatforms.business.onboard.model.StateResponse;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaBottomSheet extends BottomSheetDialogFragment {

    BottomSheetAreaBinding binding;
    private static final String TAG = "AreaBottomSheet";
    private String cityId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetAreaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        Bundle mArgs = getArguments();
        cityId = mArgs.getString("cityId");
        binding.recyclerArea.setLayoutManager(new LinearLayoutManager(getContext()));
        if(cityId!=null)
        {
            fetchArea();
        }

    }

    private void fetchArea() {
        LocationInterface locationInterface = RestClient.getRetrofitServiceLoacation(getContext()).create(LocationInterface.class);
        Call<AreaResponse> call = locationInterface.getArea(cityId);
        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                Log.e(TAG, "onResponse: "+response);
                if (response.code() == 200) {

                    if (response.body() != null) {
                        AreaRecyclerAdapter recyclerAdapter = new AreaRecyclerAdapter(getContext(),response.body().getItems());
                        recyclerAdapter.notifyDataSetChanged();
                        binding.recyclerArea.setAdapter(recyclerAdapter);
                        binding.progressArea.setVisibility(View.GONE);
                        binding.recyclerArea.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
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
