package com.fabloplatforms.business.store.interfaces;



import com.fabloplatforms.business.store.models.OrderCountResponse;
import com.fabloplatforms.business.store.models.acceptorder.AcceptOrderRequest;
import com.fabloplatforms.business.store.models.acceptorder.AcceptOrderResponse;
import com.fabloplatforms.business.store.models.acceptorder.OrderOtpRequest;
import com.fabloplatforms.business.store.models.acceptorder.RejectOrderRequest;
import com.fabloplatforms.business.store.models.acceptorder.RejectReasonResponse;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderInterface {

    @POST("order/reject/store/{order_id}")
    Call<AcceptOrderResponse> orderReject(@Path("order_id") String order_id, @Body RejectOrderRequest request);

    @POST("order/accept/{order_id}")
    Call<AcceptOrderResponse> acceptOrder(@Path("order_id") String order_id, @Body AcceptOrderRequest request);

    @GET("order/preparing/start/{order_id}")
    Call<AcceptOrderResponse> startPreparing(@Path("order_id") String order_id);


    @GET("order/ready/{order_id}")
    Call<AcceptOrderResponse> makeReady(@Path("order_id") String order_id);

    @POST("order/dispatch/{order_id}")
    Call<AcceptOrderResponse> makeDispatch(@Path("order_id") String order_id, @Body OrderOtpRequest request);

    @GET("order/pending/{store_id}")
    Call<PendingOrderResponse>pendingOrder(@Path("store_id") String storeid);

    @GET("order/preparing/{store_id}")
    Call<PendingOrderResponse>preparingOrder(@Path("store_id") String storeid);

    @GET("order/ready/list/{store_id}")
    Call<PendingOrderResponse> readyOrder(@Path("store_id") String store_id);

    @GET("order/dispatch/list/{store_id}")
    Call<PendingOrderResponse>dispatchOrder(@Path("store_id") String storeid);

    @GET("order/reasonForReject/restorant")
    Call<RejectReasonResponse>rejectReason();

    @GET("order/count/{store_id}")
    Call<OrderCountResponse>getOrderCount(@Path("store_id") String storeid);

    @GET("order/allOrder/{store_id}")
    Call<PendingOrderResponse>getAllOrder(@Path("store_id") String storeid);
}
