package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.common.KycRequest;
import com.fabloplatforms.business.common.KycStatus;
import com.fabloplatforms.business.onboard.model.KycDetailsRequest;
import com.fabloplatforms.business.onboard.model.KycResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KycInterface {

    @GET("business/kyc/{business_id}")
    Call<KycStatus> getKycStatus(@Path("business_id") String business_id);

    @POST("business/kyc")
    Call<ResponseBody> scheduleKyc(@Body KycRequest kycRequest);

    @POST("business/register/kycDetails")
    Call<ResponseBody> addKycDetails(@Body KycDetailsRequest kycRequest);

}
