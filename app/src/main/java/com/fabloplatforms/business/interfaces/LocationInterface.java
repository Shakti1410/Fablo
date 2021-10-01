package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.AreaResponse;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.onboard.model.Country;
import com.fabloplatforms.business.onboard.model.StateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LocationInterface {

    @GET("locations/cities/{id}")
    Call<City> getCities(@Path("id") String id);

    @GET("locations/countries/")
    Call<Country> getCountries();

    @GET("locations/states/{id}")
    Call<StateResponse> getState(@Path("id") String id);

    @GET("locations/areas/{id}")
    Call<AreaResponse> getArea(@Path("id") String id);

}
