package com.fabloplatforms.business.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.auth.model.LoginResponse;
import com.fabloplatforms.business.auth.model.OutletLoginRequest;
import com.fabloplatforms.business.auth.model.OutletLoginResponse;
import com.fabloplatforms.business.databinding.ActivityPhoneBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.KycInterface;
import com.fabloplatforms.business.interfaces.LoginInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.store.StoreMainActivity;
import com.fabloplatforms.business.store.error.ClientErrorBody;
import com.fabloplatforms.business.store.interfaces.OutletInterface;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreGlobalError;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.Global;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.GlobalPref;
import com.fabloplatforms.business.utils.LoginPref;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityPhoneBinding binding;
    Context context;

    private static final String TAG = "PhoneActivity";
    private  Global global;
    StoreFabloAlert storeFabloAlert;
    private FabloLoading fabloLoading;
    private boolean statusPassword = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = PhoneActivity.this;
        fabloLoading = FabloLoading.getInstance();
        storeFabloAlert = new StoreFabloAlert();
        if(isOnline())
        {
            initView();
            setNotice();
        }
        else {
            networkMsg();
        }

    }

    private void initView() {
        fabloLoading = FabloLoading.getInstance();
        storeFabloAlert = new StoreFabloAlert();
        global = new Global();
        try {
            if (context != null) {
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                String version = pInfo.versionName;
                GlobalPref globalPref = new GlobalPref(context);
                global.setAppVersion(version);
                globalPref.setGlobal(global);
                Log.e(TAG, "gatherData: "+version);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        binding.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = binding.etPhone.getText().toString();
                if(phone.isEmpty())
                {
                    binding.btnSendOtp.setEnabled(false);
                }
                else {
                    binding.btnSendOtp.setEnabled(true);
                }
               if(phone.length()> 6)
                {
                    binding.tiPassword.setVisibility(View.GONE);
                    binding.btnSendOtp.setEnabled(true);
                    binding.btnSendOtp.setText("Send OTP");
                    binding.tvPhone.setVisibility(View.VISIBLE);

                }
               else {
                   binding.btnSendOtp.setText("Continue");
                   binding.etPassword.setText("");
                   binding.tvPhone.setVisibility(View.GONE);
               }

// else {
//                        binding.btnSendOtp.setEnabled(false);
//                    }
//                } else {
//                    binding.btnSendOtp.setEnabled(false);
//                }
            }
        });
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
  //              String address2 = binding.etPassword.getText().toString();
//                if (!address2.isEmpty()) {
//
//                    binding.btnSendOtp.setEnabled(true);
//                }
//                else {
//                    binding.btnSendOtp.setEnabled(false);
//                }

            }
        });
        binding.btnSendOtp.setOnClickListener(this);
    }

    private void setNotice() {
        String notice = "<font color=#52575C>By logging in or registering, you agree to our</font> <font color=#31B057>Terms of Services</font> <font color=#52575C>and</font> <font color=#31B057>Privacy Policy</font>";
        binding.tvNotice.setText(Html.fromHtml(notice));
    }



    private void businessLogin(String phone) {
        fabloLoading.showProgress(context);
        LoginInterface loginInterface = RestClient.getRetrofitServiceInstance(context).create(LoginInterface.class);
        Call<LoginResponse> call = loginInterface.getOtp(phone);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                fabloLoading.hideProgress();
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    fabloLoading.hideProgress();
                    String phone1 = binding.etPhone.getText().toString();
                    LoginPref loginPref = new LoginPref(context);
                    loginPref.setLoginPref(response.body(),global,phone1);
                    int otp = response.body().getItems().getCode();
                    Intent intent = new Intent(PhoneActivity.this, CodeActivity.class);
                    intent.putExtra("OTP",otp); // Remove when Finalize
                    intent.putExtra("phone", phone1);
                    startActivity(intent);

                } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                    fabloLoading.hideProgress();
                    Toast.makeText(context, "There is some issue while adding Kyc details.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException){
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

//                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
//                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnSendOtp) {
            checkUser();

        }
    }

    private void checkUser() {
        String phone = binding.etPhone.getText().toString();
        if(phone.length() == 6)
        {
            binding.tiPassword.setVisibility(View.VISIBLE);
            binding.btnSendOtp.setText("Login");
            String password = binding.etPassword.getText().toString();
            if(!password.isEmpty())
            {
                storeLogin(phone,password);

            }

        }
        else if(phone.length() == 10)
        {
            businessLogin(phone);
        }
        else {
            binding.etPhone.setError("Wrong Credential");
        }
    }

    private void storeLogin(String phone, String pass) {
        fabloLoading.showProgress(context);
        String restId = phone;
        String password = pass;

        if (!restId.isEmpty() && !password.isEmpty()) {
            OutletLoginRequest outletLoginRequest = new OutletLoginRequest();
            outletLoginRequest.setStoreUsername(restId);
            outletLoginRequest.setPassword(password);

            getRestaurantByIdAndPassword(outletLoginRequest);
        }
    }
    private void getRestaurantByIdAndPassword(OutletLoginRequest outletLoginRequest) {
        OutletInterface outletInterface = RestClientStore.getRetrofitServiceInstance(context).create(OutletInterface.class);
        Call<OutletLoginResponse> call = outletInterface.outLogin(outletLoginRequest);
        call.enqueue(new Callback<OutletLoginResponse>() {
            @Override
            public void onResponse(Call<OutletLoginResponse> call, Response<OutletLoginResponse> response) {


                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {

                    if (response.body() != null) {
                        fabloLoading.hideProgress();
                        binding.etPhone.setText("");
                        binding.etPassword.setText("");
                        StoreOutletPref storeOutletPref = new StoreOutletPref(context);
                        storeOutletPref.setOutletDetails(response.body());

                        launchMain();
                    }
                } else if (response.code() == StoreConstant.HTTP_CLIENT_ERROR) {
                    fabloLoading.hideProgress();
                    Gson gson = new GsonBuilder().create();
                    try {
                        ClientErrorBody clientErrorBody = gson.fromJson(response.errorBody().string(), ClientErrorBody.class);
                        if (clientErrorBody.getMessage().equals(StoreConstant.ERROR_INVALID_CREDENTIALS)) {
                            showError("Login error", "Seams you have entered wrong Id or password.");
                        }
                    } catch (IOException e) {
                        showError("Incorrect details", "Seems there is some issue with the details you have entered.");
                    }
                }
            }

            @Override
            public void onFailure(Call<OutletLoginResponse> call, Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException) {
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");
//                    StoreGlobalError storeGlobalError = new StoreGlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", StoreConstant.STATUS_NO_ERROR);
//                    EventBus.getDefault().post(storeGlobalError);
                }
            }
        });
    }
    private void networkMsg() {
        try {
            storeFabloAlert.showAlert(context,StoreConstant.ALERT_ERROR,false,"Network Alert","Internet not available, Cross check your internet connectivity and try again",TAG);

        } catch (Exception e) {
            Toast.makeText(this, "Error :" + e, Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){

            return false;
        }
        return true;
    }

    private void launchMain() {
       fabloLoading.hideProgress();
        Intent intent = new Intent(context, StoreMainActivity.class);
        intent.putExtra("LoginType","store");
        startActivity(intent);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
}