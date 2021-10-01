package com.fabloplatforms.business.store.interfaces;

import com.fabloplatforms.business.auth.model.OutletLoginRequest;
import com.fabloplatforms.business.auth.model.OutletLoginResponse;
import com.fabloplatforms.business.store.models.storeavailability.StoreAvailabilityManualResponse;
import com.fabloplatforms.business.store.models.storeavailability.StoreScheduleRequest;
import com.fabloplatforms.business.store.models.storeavailability.StoreScheduleResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OutletInterface {
    @POST("store/login")
    Call<OutletLoginResponse> outLogin(@Body OutletLoginRequest outletLoginRequest);

    @GET("store/availability/{store_id}/set/{status}")
    Call<StoreAvailabilityManualResponse> manualOFF(@Path("store_id") String store_id, @Path("status") int status);

    @POST("store/availability/{store_id}/set/{status}/schedule")
    Call<StoreScheduleResponse> storeSchedule(@Path("store_id") String store_id, @Path("status") int status, @Body StoreScheduleRequest request);
}
