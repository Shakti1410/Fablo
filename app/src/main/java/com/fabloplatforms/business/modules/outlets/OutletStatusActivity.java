package com.fabloplatforms.business.modules.outlets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.common.BusinessAccountStatus;
import com.fabloplatforms.business.databinding.ActivityGatewayBinding;
import com.fabloplatforms.business.databinding.ActivityOutletStatusBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BusinessInterface;
import com.fabloplatforms.business.interfaces.OutletInterface;
import com.fabloplatforms.business.modules.outlets.model.OutletModel;
import com.fabloplatforms.business.modules.outlets.model.OutletStatus;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.BusinessPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.GlobalError;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutletStatusActivity extends AppCompatActivity {
    ActivityOutletStatusBinding binding;
    private Context context;


    private static final String TAG = "OutletStatusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOutletStatusBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = OutletStatusActivity.this;
        initView();
    }
    private void initView() {


      //  getOutletid();
        gotoOutletScreen(Constant.SWITCH_OUTLET_TIMING);
    }
    private void getOutletid() {
        BusinessPref businessPref = new BusinessPref(context);
        String businessId = businessPref.getId();

        OutletInterface outletInterface = RestClient.getRetrofitServiceInstance(context).create(OutletInterface.class);
        Call<OutletModel> call = outletInterface.getOutletByBusinessId(businessId);
        call.enqueue(new Callback<OutletModel>() {
            @Override
            public void onResponse(Call<OutletModel> call, Response<OutletModel> response) {
                Log.e(TAG, "onResponse: " + response);
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        Log.e(TAG, "http: " + response);
                        performDecisionUponOutletStatus(response.body().getItems());
                    }
                    else {
                        gotoOutletScreen(Constant.SWITCH_OUTLET_DETAILS);

                    }
                }
            }

            @Override
            public void onFailure(Call<OutletModel> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    private void performDecisionUponOutletStatus(OutletModel.Item items) {
        String outetId = items.getId();
        OutletInterface outletInterface = RestClient.getRetrofitServiceInstance(context).create(OutletInterface.class);
        Call<OutletStatus> call = outletInterface.getOutletStatus(outetId);
        call.enqueue(new Callback<OutletStatus>() {
            @Override
            public void onResponse(Call<OutletStatus> call, Response<OutletStatus> response) {
                Log.e(TAG, "onResponse: " + response);
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        Log.e(TAG, "http: " + response);
                        getOutletdetails(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<OutletStatus> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    private void getOutletdetails(OutletStatus status){
        if(status.getDetails()==Constant.OUTLET_STATUS_STEP_INCOMPLETE)
        {
            gotoOutletScreen(Constant.SWITCH_OUTLET_DETAILS);
        }
        else if(status.getTimings()==Constant.OUTLET_STATUS_STEP_INCOMPLETE)
        {
            gotoOutletScreen(Constant.SWITCH_OUTLET_TIMING);
        }
        else if(status.getPhotos()==Constant.OUTLET_STATUS_STEP_INCOMPLETE)
        {
            gotoOutletScreen(Constant.SWITCH_OUTLET_PHOTOS);
        }
        else
        {
            gotoOutletScreen(Constant.SWITCH_OUTLET_DETAILS);
        }


    }

    private void gotoOutletScreen(String status) {

        Intent intent = new Intent(context, AddOutletActivity.class);
        intent.putExtra("outletstep", status);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}