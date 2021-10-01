package com.fabloplatforms.business.store.storeretrofit;

import android.content.Context;


import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreGlobalError;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestClientStore {

    private static Retrofit retrofitservice;
    private static Retrofit retrofitorder;

    private static final String DEV_REMOTE ="https://fablo-food-business-oodq3.ondigitalocean.app/";// "https://fablo-restaurant-partner-98ykz.ondigitalocean.app/";
    private static final String DEV_LOCAL = "https://090c9aa8cf17.ngrok.io/";
    private static final String Order_Url = "https://order-eqoju.ondigitalocean.app/";
    public static Retrofit getRetrofitServiceInstance(Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptorStore(context))
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        if (response.code() == StoreConstant.HTTP_RESOURCE_NOT_FOUND) {
                            StoreGlobalError storeGlobalError = new StoreGlobalError("Something went wrong", "We cannot process your request right now, please try again after some time.", StoreConstant.HTTP_RESOURCE_NOT_FOUND);
                            EventBus.getDefault().post(storeGlobalError);
                            return response;

                        } else if (response.code() == StoreConstant.HTTP_SERVER_ERROR) {
                            StoreGlobalError storeGlobalError = new StoreGlobalError("Something went wrong", "We cannot process your request right now, please try again after some time.", StoreConstant.HTTP_SERVER_ERROR);
                            EventBus.getDefault().post(storeGlobalError);
                            return response;

                        } else if (response.code() == StoreConstant.HTTP_SERVER_MAINTENANCE) {
                            StoreGlobalError storeGlobalError = new StoreGlobalError("Scheduled maintenance", "Our engineers are working hard to make this feature up and running again.", StoreConstant.HTTP_SERVER_MAINTENANCE);
                            EventBus.getDefault().post(storeGlobalError);
                            return response;
                        }

                        return response;
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();


        if (retrofitservice == null) {
            retrofitservice = new Retrofit.Builder()
                    .baseUrl(DEV_REMOTE)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofitservice;

    }
    public static Retrofit getRetrofitServiceOrder(Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptorStore(context))
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        if (response.code() == StoreConstant.HTTP_RESOURCE_NOT_FOUND) {
                            StoreGlobalError storeGlobalError = new StoreGlobalError("Something went wrong", "We cannot process your request right now, please try again after some time.", StoreConstant.HTTP_RESOURCE_NOT_FOUND);
                            EventBus.getDefault().post(storeGlobalError);
                            return response;

                        } else if (response.code() == StoreConstant.HTTP_SERVER_ERROR) {
                            StoreGlobalError storeGlobalError = new StoreGlobalError("Something went wrong", "We cannot process your request right now, please try again after some time.", StoreConstant.HTTP_SERVER_ERROR);
                            EventBus.getDefault().post(storeGlobalError);
                            return response;

                        } else if (response.code() == StoreConstant.HTTP_SERVER_MAINTENANCE) {
                            StoreGlobalError storeGlobalError = new StoreGlobalError("Scheduled maintenance", "Our engineers are working hard to make this feature up and running again.", StoreConstant.HTTP_SERVER_MAINTENANCE);
                            EventBus.getDefault().post(storeGlobalError);
                            return response;
                        }

                        return response;
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();


        if (retrofitorder == null) {
            retrofitorder = new Retrofit.Builder()
                    .baseUrl(Order_Url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofitorder;

    }

}
