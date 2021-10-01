package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.BankAccountRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BankInterface {

    @POST("business/register/bankDetails")
    Call<ResponseBody> createBank(@Body BankAccountRequest bankAccountRequest);

}
