package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.common.KycRequest;
import com.fabloplatforms.business.onboard.model.CommissionRequest;
import com.fabloplatforms.business.onboard.model.CommissionResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommissionInterface {

    @POST("business/register/commissionDetails")
    Call<ResponseBody> addCommissionDetails(@Body CommissionRequest commissionRequest);

    @GET("business/register/commissionRate/{token}")
    Call<CommissionResponse> getCommission(@Path("token") String token);
}
