package com.fabloplatforms.business.store.interfaces;

import com.fabloplatforms.business.store.modules.outlet.web.model.WebLoginRequest;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BusinessWebInterface {
//
    @POST("business/web/Login/loginRequest/{business_id}")
    Call<ResponseBody> sendLoginRequest(@Path("business_id") String business_id, @Body WebLoginRequest webLoginRequest);

    @GET("business/web/Login/getWebSessions/{business_id}")
    Call<ResponseBody> getWebSessions(@Path("business_id") String business_id);

}
