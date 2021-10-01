package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.City;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CityInterface {

    @GET("cities")
    Call<City> getCities();

}
