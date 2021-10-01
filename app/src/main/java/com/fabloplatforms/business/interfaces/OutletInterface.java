package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.common.Business;
import com.fabloplatforms.business.common.BusinessAccountStatus;
import com.fabloplatforms.business.modules.outlets.model.OutletModel;
import com.fabloplatforms.business.modules.outlets.model.OutletStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OutletInterface {

    @GET("/store/stores/{business_id}")
    Call<OutletModel> getOutletByBusinessId(@Path("business_id") String business_id);

    @GET("business/token/{token}/status")
    Call<OutletStatus> getOutletStatus(@Path("token") String token);
}
