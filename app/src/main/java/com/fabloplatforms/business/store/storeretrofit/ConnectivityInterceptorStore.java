package com.fabloplatforms.business.store.storeretrofit;

import android.content.Context;


import com.fabloplatforms.business.store.exceptions.NoConnectivityException;
import com.fabloplatforms.business.store.utils.StoreNetworkUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptorStore implements Interceptor {
 
    private Context context;

    public ConnectivityInterceptorStore(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (!StoreNetworkUtil.isOnline(context)) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

}