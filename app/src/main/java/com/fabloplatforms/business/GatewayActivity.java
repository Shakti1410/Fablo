package com.fabloplatforms.business;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fabloplatforms.business.auth.CodeActivity;
import com.fabloplatforms.business.auth.model.StatusResponse;
import com.fabloplatforms.business.common.Business;
import com.fabloplatforms.business.common.BusinessAccountStatus;
import com.fabloplatforms.business.databinding.ActivityGatewayBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BusinessInterface;
import com.fabloplatforms.business.interfaces.LoginInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.RegistrationStepActivity;
import com.fabloplatforms.business.retrofit.LeadRetroClient;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.BusinessPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.Global;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.GlobalPref;
import com.fabloplatforms.business.utils.LoginPref;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GatewayActivity extends AppCompatActivity {

    ActivityGatewayBinding binding;
    private Context context;
    private FabloAlert fabloAlert;
    private FabloLoading fabloLoading;
    private static final String TAG = "GatewayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGatewayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = GatewayActivity.this;
        initView();
    }

    private void initView() {
        fabloLoading = FabloLoading.getInstance();
        fabloAlert = FabloAlert.getInstance();
        setupGlobal();
    //    checkStatus(0,1,1,1,1,1,1,1);
        getStatus();

    }

    private void getStatus() {
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        LoginInterface loginInterface = RestClient.getRetrofitServiceInstance(context).create(LoginInterface.class);
        Call<StatusResponse> call = loginInterface.getStatus(token);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                fabloLoading.hideProgress();
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    if(response.body()!=null)
                    {
                      int basic = response.body().getItems().getBasicDetails();
                      int contact = response.body().getItems().getContactDetails();
                      int bank = response.body().getItems().getBankDetails();
                      int tax = response.body().getItems().getTaxDetails();
                      int fssai = response.body().getItems().getFssaiDetails();
                      int kyc = response.body().getItems().getKycDetails();
                      int commission = response.body().getItems().getCommissionDetails();
                      int ekyc = 1;//response.body().getItems().geteSignatureDetails();
                      checkStatus(basic,contact,bank,tax,fssai,kyc,commission,ekyc);
                       // checkStatus(1,1,1,1,1,1,0,1);
                    }



                } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                    fabloLoading.hideProgress();
                    Toast.makeText(context, "Verification Error, \n Invalid Otp ! Please try Again ", Toast.LENGTH_SHORT).show();
//                    GlobalError globalError = new GlobalError("Verification Error", "Invalid Otp ! Please try Again", Constant.STATUS_NO_ERROR);
//                    EventBus.getDefault().post(globalError);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException){
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    private void checkStatus(int basic, int contact, int bank, int tax, int fssai, int kyc, int commission, int ekyc) {

        if(basic == 0)
        {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "business");
            startActivity(intent);
            finish();
        }
        else if(contact == 0)
        {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "contact");
            startActivity(intent);
            finish();
        }
        else if(bank == 0)
        {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "bank");
            startActivity(intent);
            finish();
        }
        else if(tax == 0)
        {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "tax");
            startActivity(intent);
            finish();
        }
        else if(fssai == 0)
        {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "fssai");
            startActivity(intent);
            finish();
        }
        else if(kyc == 0)
        {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "kyc");
            startActivity(intent);
            finish();
        }
        else if(commission == 0)
        {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "commission");
            startActivity(intent);
            finish();
        }
        else if(ekyc == 0)
        {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "Ekyc");
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(context, RegistrationStepActivity.class);
            intent.putExtra("status", "Home");
            startActivity(intent);
            finish();
        }
    }

    private void getToken() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            String token = firebaseAuth.getCurrentUser().getUid();
            Log.e(TAG, "Token : " + token);
            checkLeadByToken(token);
        }
    }

    private void checkLeadByToken(String token) {
        BusinessInterface businessInterface = LeadRetroClient.getRetrofitServiceInstance(context).create(BusinessInterface.class);
        Call<Business> call = businessInterface.getLeadByToken(token);
        call.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                Log.e(TAG, "onResponseCode: " + response.code());
               // Boolean status = response.body().getStatus();
               // if(response.body().getStatus())
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS)
                {

                        checkStatusByToken(token);
                        //LeadStatus Model class was created for leads status 1,2,3,4,5. just replace Business Model to Lead Status

                }
                else
                {
                    Log.e(TAG, "onResponseBody: " + response.body());
                    gotoSetupBusinessStepScreen(Constant.SWITCH_STEP_BUSINESS);
                }

            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
                if (t instanceof NoConnectivityException){
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }


    private void checkStatusByToken(String token) {
        BusinessInterface businessInterface = RestClient.getRetrofitServiceInstance(context).create(BusinessInterface.class);
        Call<BusinessAccountStatus> call = businessInterface.getBusinessStatusByToken(token);
        call.enqueue(new Callback<BusinessAccountStatus>() {
            @Override
            public void onResponse(@NotNull Call<BusinessAccountStatus> call, @NotNull Response<BusinessAccountStatus> response) {
                Log.e(TAG, "onResponse: " + response);
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        Log.e(TAG, "http: " + response);
                        performDecisionUponBusinessStatus(token, response.body().getItems());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<BusinessAccountStatus> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    private void performDecisionUponBusinessStatus(String token, BusinessAccountStatus.Items items) {
        if (items.getBusiness() == Constant.BUSINESS_STATUS_STEP_COMPLETE) {
            Log.e(TAG, "performDecisionUponBusinessStatus: business remain");
           // gotoSetupBusinessStepScreen(Constant.SWITCH_STEP_BUSINESS);
            if (items.getContactPerson() == Constant.BUSINESS_STATUS_STEP_INCOMPLETE) {
                Log.e(TAG, "performDecisionUponBusinessStatus: contact remain");
                gotoSetupBusinessStepScreen(Constant.SWITCH_STEP_CONTACT);
            } else if (items.getBank() == Constant.BUSINESS_STATUS_STEP_INCOMPLETE) {
                Log.e(TAG, "performDecisionUponBusinessStatus: bank remain");
                gotoSetupBusinessStepScreen(Constant.SWITCH_STEP_BANK);
            }
            else {
                Log.e(TAG, "performDecisionUponBusinessStatus: all complete");
                gotoSetupBusinessStepScreen(Constant.SWITCH_STEP_BANK);
               // getBusinessDetails(token);
            }

        }
        else {
            gotoSetupBusinessStepScreen(Constant.SWITCH_STEP_CONTACT);
        }




    }

    private void gotoSetupBusinessStepScreen(String switchStep) {
        Intent intent = new Intent(context, BusinessSetupActivity.class);
        intent.putExtra("step", switchStep);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

/*    private void getBusinessDetails(String token) {
        BusinessInterface businessInterface = RestClient.getRetrofitServiceInstance(context).create(BusinessInterface.class);
        Call<Business> call = businessInterface.getBusinessByToken(token);
        call.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(@NotNull Call<Business> call, @NotNull Response<Business> response) {
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        BusinessPref businessPref = new BusinessPref(context);
                        businessPref.setBusiness(response.body());
                        gotoMainScreen();
                    }

                } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                    showError("Something went wrong", "We cannot process your request right now, please try again after some time.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<Business> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    private void gotoMainScreen() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }*/

    private void setupGlobal() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            GlobalPref globalPref = new GlobalPref(context);
            Global global = new Global();
            global.setAppVersion(version);
            globalPref.setGlobal(global);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GlobalError globalError) {
        if (globalError != null) {
            showError(globalError.getTitle(), globalError.getDescription());
        }

    }

    public void showError(String title, String description) {
        fabloAlert.showAlert(GatewayActivity.this, Constant.ALERT_ERROR, false, title, description, "");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }


}