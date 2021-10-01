package com.fabloplatforms.business.interfaces;

import com.fabloplatforms.business.onboard.model.Category;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryInterface {

    @GET("business/categories")
    Call<Category> getCategories();

}
