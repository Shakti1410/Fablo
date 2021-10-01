package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.FssaiDetailsRequest;
import com.fabloplatforms.business.onboard.model.TaxDetailsRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FssaiInterface {
    @POST("business/register/fssaiDetails")
    Call<ResponseBody> createFssaiDetails(@Body FssaiDetailsRequest request);
}
