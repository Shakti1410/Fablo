package com.fabloplatforms.business.store.modules.outlet.web;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;

import com.fabloplatforms.business.databinding.ActivityWebLaunchBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.BusinessWebInterface;
import com.fabloplatforms.business.store.modules.outlet.web.model.WebLoginRequest;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.utils.BusinessPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.TempPref;
import com.google.zxing.Result;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebLaunchActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityWebLaunchBinding binding;
    private Context context;
    private CodeScanner mCodeScanner;
    private StoreFabloLoading storeFabloLoading;

    private static final String TAG = "WebLaunchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebLaunchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = WebLaunchActivity.this;
        initView();
    }

    private void initView() {
        mCodeScanner = new CodeScanner(this, binding.scannerView);
        storeFabloLoading = StoreFabloLoading.getInstance();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    public void run() {
                     //   Log.e(TAG, "run: "+result.getText().trim());
                        sendLoginRequest(result.getText().trim());
                    }
                });

            }
        });


        binding.scannerView.setOnClickListener(this);
        binding.btnActivate.setOnClickListener(this);
        binding.ivGoBack.setOnClickListener(this);
    }

    private void sendLoginRequest(String token) {
        Log.e(TAG, "sendLoginRequest: "+token);
        WebLoginRequest webLoginRequest = new WebLoginRequest();
        webLoginRequest.setToken(token);
        webLoginRequest.setAppVersion("1.0");
        //Temp code
        TempPref pref = new TempPref(context);
        String id = pref.getId();
        Log.d(TAG, "sendLoginRequest: Wb" + id);
        BusinessWebInterface businessWebInterface = RestClientStore.getRetrofitServiceInstance(context).create(BusinessWebInterface.class);
        Call<ResponseBody> call = businessWebInterface.sendLoginRequest(id, webLoginRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    Log.e(TAG, "onResponse: login success");
                } else if (response.code() == StoreConstant.HTTP_CLIENT_ERROR) {
                    Log.e(TAG, "onResponse: login failed");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
                if (t instanceof NoConnectivityException) {
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.scannerView) {
            binding.btnActivate.setEnabled(false);
            mCodeScanner.startPreview();
        }
        if (v == binding.ivGoBack) {
            onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}