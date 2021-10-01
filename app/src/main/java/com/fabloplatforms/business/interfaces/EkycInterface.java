package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.EkycResponse;
import com.fabloplatforms.business.onboard.model.Ifsc;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EkycInterface {

    @GET("business/register/getAgreementDoc/{token}")
    Call<EkycResponse> getPdf(@Path("token") String token);
}
