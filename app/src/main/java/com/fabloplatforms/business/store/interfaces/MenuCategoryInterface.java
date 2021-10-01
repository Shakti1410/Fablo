package com.fabloplatforms.business.store.interfaces;

import com.fabloplatforms.business.store.models.MenuCategoryResponse;
import com.fabloplatforms.business.store.models.MenuProductResponse;
import com.fabloplatforms.business.store.models.MenuSubCategoryResponse;
import com.fabloplatforms.business.store.models.storeavailability.StoreScheduleRequest;
import com.fabloplatforms.business.store.models.storeavailability.StoreScheduleResponse;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MenuCategoryInterface {

    @POST("product/{product_id}/stock/{stock}")
    Call<ResponseBody> stockUpdateManual(@Path("product_id") String productid, @Path("stock") int stock);

    @POST("product/{product_id}/scheduleChangeStock/{stock}")
    Call<StoreScheduleResponse> stockUpdateSchedule(@Path("product_id") String productid, @Path("stock") int stock, @Body StoreScheduleRequest request);

    @GET("product/category/{storeid}")
    Call<MenuCategoryResponse> getCategories(@Path("storeid") String storeid);

    @GET("product/subcategory/{storeid}/{catid}")
    Call<MenuSubCategoryResponse> getSubCategories(@Path("storeid") String storeid, @Path("catid") String catid);

    @GET("product/subcategory/{subcatid}/")
    Call<MenuProductResponse> getProducts(@Path("subcatid") String subcatid);
}
