package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.common.Business;
import com.fabloplatforms.business.common.BusinessAccountStatus;
import com.fabloplatforms.business.modules.model.StoreListResponse;
import com.fabloplatforms.business.onboard.model.BusinessLeadModel;
import com.fabloplatforms.business.onboard.model.CreateBusinessRequest;
import com.fabloplatforms.business.onboard.model.CreateBusinessResponse;
import com.fabloplatforms.business.onboard.model.CreateLeadRequest;
import com.fabloplatforms.business.onboard.model.QrRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BusinessInterface {

    @GET("business/token/{token}")
    Call<Business> getBusinessByToken(@Path("token") String token);

    @GET("business/token/{token}/status")
    Call<BusinessAccountStatus> getBusinessStatusByToken(@Path("token") String token);

    @GET("business/{business_id}/status")
    Call<BusinessAccountStatus> getBusinessStatus(@Path("business_id") String business_id);

    @POST("business/register/basicDetails")
    Call<CreateBusinessResponse> createBusiness(@Body CreateBusinessRequest createBusinessRequest);

    @POST("business/{business_id}/qr")
    Call<ResponseBody> activateGlobalQr(@Path("business_id") String business_id, @Body QrRequest qrRequest);
    @POST("/business/lead")
    Call<CreateLeadRequest> createBusinessLead(@Body BusinessLeadModel model);
    @GET("business/lead/{token}")
    Call<Business> getLeadByToken(@Path("token") String token);

    @GET("store/get/allStore/{id}")
    Call<StoreListResponse> getAllStore(@Path("id") String id);
}
