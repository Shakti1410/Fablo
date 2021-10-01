package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.auth.model.LoginResponse;
import com.fabloplatforms.business.auth.model.OtpVerifyRequest;
import com.fabloplatforms.business.auth.model.OtpVerifyResponse;
import com.fabloplatforms.business.auth.model.StatusResponse;
import com.fabloplatforms.business.common.Business;
import com.fabloplatforms.business.onboard.model.BankAccountRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginInterface {

    @POST("business/login/verifyOtp")
    Call<OtpVerifyResponse> verifyOtp(@Body OtpVerifyRequest request);

    @GET("business/login/sendOtp/{phone}")
    Call<LoginResponse> getOtp(@Path("phone") String phone);

    @GET("business/register/getStatus/{token}")
    Call<StatusResponse> getStatus(@Path("token") String token);
}
