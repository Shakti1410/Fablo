package com.fabloplatforms.business.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;

import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.GlobalError;

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

public class RestClient {

    private static Retrofit retrofit;
    private static Retrofit retrofitLocation;

    private static final String DEV_REMOTE = "https://fablo-food-business-oodq3.ondigitalocean.app/";//"https://fablo-restaurant-partner-98ykz.ondigitalocean.app/";
    private static final String DEV_Location = "https://fablo-administrator-ahyzw.ondigitalocean.app/";
    private static final String DEV_LOCAL = "https://090c9aa8cf17.ngrok.io/";

    public static Retrofit getRetrofitServiceInstance(Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(context))
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        if (response.code() == Constant.HTTP_RESOURCE_NOT_FOUND) {
                            GlobalError globalError = new GlobalError("Something went wrong", "We cannot process your request right now, please try again after some time.", Constant.HTTP_RESOURCE_NOT_FOUND);
                            EventBus.getDefault().post(globalError);
                            return response;

                        } else if (response.code() == Constant.HTTP_SERVER_ERROR) {
                            GlobalError globalError = new GlobalError("Something went wrong", "We cannot process your request right now, please try again after some time.", Constant.HTTP_SERVER_ERROR);
                            EventBus.getDefault().post(globalError);
                            return response;

                        } else if (response.code() == Constant.HTTP_SERVER_MAINTENANCE) {
                            GlobalError globalError = new GlobalError("Scheduled maintenance", "Our engineers are working hard to make this feature up and running again.", Constant.HTTP_SERVER_MAINTENANCE);
                            EventBus.getDefault().post(globalError);
                            return response;
                        }

                        return response;
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(DEV_REMOTE)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;

    }
    public static Retrofit getRetrofitServiceLoacation(Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(context))
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        if (response.code() == Constant.HTTP_RESOURCE_NOT_FOUND) {
                            GlobalError globalError = new GlobalError("Something went wrong", "We cannot process your request right now, please try again after some time.", Constant.HTTP_RESOURCE_NOT_FOUND);
                            EventBus.getDefault().post(globalError);
                            return response;

                        } else if (response.code() == Constant.HTTP_SERVER_ERROR) {
                            GlobalError globalError = new GlobalError("Something went wrong", "We cannot process your request right now, please try again after some time.", Constant.HTTP_SERVER_ERROR);
                            EventBus.getDefault().post(globalError);
                            return response;

                        } else if (response.code() == Constant.HTTP_SERVER_MAINTENANCE) {
                            GlobalError globalError = new GlobalError("Scheduled maintenance", "Our engineers are working hard to make this feature up and running again.", Constant.HTTP_SERVER_MAINTENANCE);
                            EventBus.getDefault().post(globalError);
                            return response;
                        }

                        return response;
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();


        if (retrofitLocation == null) {
            retrofitLocation = new Retrofit.Builder()
                    .baseUrl(DEV_Location)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofitLocation;

    }

}
