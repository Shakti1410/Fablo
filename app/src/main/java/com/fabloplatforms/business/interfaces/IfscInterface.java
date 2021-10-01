package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.Ifsc;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IfscInterface {

    @GET("{ifsc}")
    Call<Ifsc> checkIfsc(@Path("ifsc") String ifsc);

}
