package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.ContactPersonRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContactPersonInterface {

    @POST("business/register/contactDetails")
    Call<ResponseBody> addContactPerson(@Body ContactPersonRequest contactPersonRequest);

}
