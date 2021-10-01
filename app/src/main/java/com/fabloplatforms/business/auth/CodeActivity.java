package com.fabloplatforms.business.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.fabloplatforms.business.GatewayActivity;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.auth.model.LoginResponse;
import com.fabloplatforms.business.auth.model.OtpVerifyRequest;
import com.fabloplatforms.business.auth.model.OtpVerifyResponse;
import com.fabloplatforms.business.databinding.ActivityCodeBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.LoginInterface;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.LoginPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCodeBinding binding;

    private String phone;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private String mVerificationId;
    private StoreFabloAlert storeFabloAlert;
    private FabloLoading fabloLoading;
    private Context context;
    private int otp=0;

    private static final String TAG = "CodeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = CodeActivity.this;
        otp = getIntent().getIntExtra("OTP",0);
        initView();
        setNotice();
    }

    private void initView() {
        fabloLoading = FabloLoading.getInstance();
        storeFabloAlert = new StoreFabloAlert();
        firebaseAuth = FirebaseAuth.getInstance();
        phone = getIntent().getStringExtra("phone");
        String message = "We just sent you a 4 digit otp on your phone number "+phone;
        binding.tvPhone.setText(message);

        binding.etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String otp = binding.etOtp.getText().toString();
                if (otp.length() == 4) {
                    if (TextUtils.isDigitsOnly(otp)) {
                        hideKeyboard(CodeActivity.this);
                        binding.etOtp.clearFocus();
                        binding.btnVerify.setEnabled(true);
                    } else {
                        binding.btnVerify.setEnabled(false);
                    }
                } else {
                    binding.btnVerify.setEnabled(false);
                }
            }
        });
        binding.etOtp.setText(String.valueOf(otp));
        binding.btnVerify.setOnClickListener(this);
        binding.tvResendOTP.setOnClickListener(this);
        binding.ivGoBack.setOnClickListener(this);

      //  addDelay();
      //  sendOtp();
    }

    private void addDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.lhAutoVerify.setVisibility(View.GONE);
                binding.tvResendOTP.setVisibility(View.VISIBLE);
            }
        }, 60000);
    }

    private void sendOtp() {
        setupVerificationCallbacks();
        String withCode = "+91" + phone;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(withCode)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(verificationCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void setupVerificationCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(CodeActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(CodeActivity.this, "You have exceeded OTP receiving limit, Please try again later.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                resendToken = token;
                binding.tvOtpState.setText("Auto verifying...");
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        binding.lhAutoVerify.setVisibility(View.GONE);
        fabloLoading.showProgress(CodeActivity.this);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fabloLoading.hideProgress();
                            Intent intent = new Intent(CodeActivity.this, GatewayActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                fabloLoading.hideProgress();
                                Toast.makeText(CodeActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void setNotice() {
        String notice = "<font color=#52575C>By logging in or registering, you agree to our</font> <font color=#31B057>Terms of Services</font> <font color=#52575C>and</font> <font color=#31B057>Privacy Policy</font>";
        binding.tvNotice.setText(Html.fromHtml(notice));
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnVerify) {
            verifyOtp();
        }
        if (v == binding.tvResendOTP) {
            resendOTP();
        }
        if (v == binding.ivGoBack) {
            onBackPressed();
        }
    }

    private void resendOTP() {
        resendVerificationCode();
        binding.tvOtpState.setText("Sending OTP...");
        binding.tvResendOTP.setVisibility(View.GONE);
        binding.lhAutoVerify.setVisibility(View.VISIBLE);
        addDelay();
    }

    private void resendVerificationCode() {
        String withCode = "+91" + phone;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(withCode)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(verificationCallbacks)
                        .setForceResendingToken(resendToken)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyOtp() {
        fabloLoading.showProgress(context);
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        String otp = binding.etOtp.getText().toString();
        OtpVerifyRequest request = new OtpVerifyRequest();
        request.setToken(token);
        request.setOtp(otp);
        LoginInterface loginInterface = RestClient.getRetrofitServiceInstance(context).create(LoginInterface.class);
        Call<OtpVerifyResponse> call = loginInterface.verifyOtp(request);
        call.enqueue(new Callback<OtpVerifyResponse>() {
            @Override
            public void onResponse(Call<OtpVerifyResponse> call, Response<OtpVerifyResponse> response) {
                fabloLoading.hideProgress();
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    if(response.body()!=null)
                    {
                        Intent intent = new Intent(CodeActivity.this, GatewayActivity.class);
                        startActivity(intent);
                        finish();
//                        if(response.body().getStatus())
//                        {
//
//                           // Toast.makeText(context, "Verified", Toast.LENGTH_SHORT).show();
//
////                    Intent intent = new Intent(PhoneActivity.this, CodeActivity.class);
////                    intent.putExtra("phone", phone1);
////                    startActivity(intent);
//                        }

                    }



                } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {

                  //  Toast.makeText(context, "Verification Error, \n Invalid Otp ! Please try Again ", Toast.LENGTH_SHORT).show();
                    showError("Verification Error", "Invalid Otp ! Please try Again");

//                    GlobalError globalError = new GlobalError("Verification Error", "Invalid Otp ! Please try Again", Constant.STATUS_NO_ERROR);
//                    EventBus.getDefault().post(globalError);
                    }
            }

            @Override
            public void onFailure(Call<OtpVerifyResponse> call, Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException){
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
//        signInWithPhoneAuthCredential(credential);
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