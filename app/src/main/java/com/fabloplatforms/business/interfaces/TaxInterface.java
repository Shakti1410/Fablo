package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.BankAccountRequest;
import com.fabloplatforms.business.onboard.model.TaxDetailsRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TaxInterface {

    @POST("business/register/taxDetails")
    Call<ResponseBody> createTaxDetails(@Body TaxDetailsRequest request);

}
