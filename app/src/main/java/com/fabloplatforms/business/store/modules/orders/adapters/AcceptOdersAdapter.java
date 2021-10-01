package com.fabloplatforms.business.store.modules.orders.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.OrdersCount;
import com.fabloplatforms.business.store.models.acceptorder.AcceptOrderRequest;
import com.fabloplatforms.business.store.models.acceptorder.AcceptOrderResponse;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;
import com.fabloplatforms.business.store.models.pendingorder.Time;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreOrderCancelAlert;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.button.MaterialButton;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptOdersAdapter extends RecyclerView.Adapter<AcceptOdersAdapter.OrderAcceptViewHolder>{
    private Context context;
    private StoreFabloAlert storeFabloAlert;

    private StoreOrderCancelAlert fabloAlert = StoreOrderCancelAlert.getInstance();
    // private List<CategoryRoomModel> categoryList; for room
    List<PendingOrderResponse.Items> orderlist;


    public AcceptOdersAdapter(Context context, List<PendingOrderResponse.Items> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
       storeFabloAlert = new StoreFabloAlert();
    }

    @Override
    public OrderAcceptViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_accept_order,parent,false);

        return new OrderAcceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderAcceptViewHolder holder, int position) {
     //   fabloAlert = OrderCancelAlert.getInstance();
        PendingOrderResponse.Items model = orderlist.get(position);
        String uid = model.getUser().getId();
        String orderid = model.getOrderId();
        String preTime = model.getTime().getPreparation();
        Time t1 = model.getTime();
        Double total;
        holder.orderId.setText("ID: "+model.getOrderId());
        holder.orderTime.setText(t1.getOrder());
        holder.cName.setText(model.getUser().getName());
        holder.cPhone.setText(model.getUser().getContact());


        OrderItemAdapter adapter = new OrderItemAdapter(context,model.getProducts());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rvItem.setLayoutManager(linearLayoutManager);
        holder.rvItem.setAdapter(adapter);

        if(preTime.equals(""))
        {
            holder.preTime.setText(String.valueOf(0));
        }
        else
        {
            holder.preTime.setText(preTime);
        }


        total = model.getPayment().getFinalPayment();
        holder.totAmount.setText(String.valueOf(total));
        holder.timePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = holder.preTime.getText().toString();
                int time = Integer.parseInt(s);
                time = time+5;
                holder.preTime.setText(String.valueOf(time));
            }
        });
        holder.timeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = holder.preTime.getText().toString();
                int time = Integer.parseInt(s) ;
                if(time>0)
                {
                    time = time-5;
                }

                holder.preTime.setText(String.valueOf(time));
            }
        });
        holder.cPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = model.getUser().getContact();
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)));
            }
        });
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ptime = holder.preTime.getText().toString();

             acceptOrder(orderid,uid,ptime,holder.getAdapterPosition());
            }
        });
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              fabloAlert.showAlert(context,orderid,uid, StoreConstant.ALERT_ERROR, false, "Cancel Order", "", "");
//                Toast.makeText(context, "ready "+ check, Toast.LENGTH_SHORT).show();
//
//                   if(check == 1)
//                   {
//                       orderlist.remove(position);
//                       notifyDataSetChanged();
//                   }

            }
        });

    }
    private void orderNoUpdate() {

        OrdersCount count = new OrdersCount();
        count.setPreOrder(0);
        EventBus.getDefault().post(count);
//        SharedPreferences preferences = context.getSharedPreferences("OrderCount",0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("acceptOrder",0);
//        editor.apply();
    }
    private void acceptOrder(String orderid, String uid, String ptime, int position) {
        AcceptOrderRequest request = new AcceptOrderRequest();
        request.setUserId(uid);
        request.setPreparationTime(ptime);


        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<AcceptOrderResponse> call = orderInterface.acceptOrder(orderid,request);
        call.enqueue(new Callback<AcceptOrderResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<AcceptOrderResponse> call, Response<AcceptOrderResponse> response) {


                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    orderlist.remove(position);
                    notifyDataSetChanged();
                    orderNoUpdate();
                   // Toast.makeText(context, "Order Accepted", Toast.LENGTH_SHORT).show();

                    Log.d("OrderAccepted", "onResponse: "+ response.message());

                }
            }

            @Override
            public void onFailure(Call<AcceptOrderResponse> call, Throwable t) {
                Log.d("OrderAccepted", "onFailure: "+ t.getMessage());
                if (t instanceof NoConnectivityException){
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });
    }


    @Override
    public int getItemCount() {
       if(orderlist != null)
       {
           return orderlist.size();
       }
       else {
           return 0;
       }

    }
    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
    public static class OrderAcceptViewHolder extends RecyclerView.ViewHolder {
        TextView orderId,orderDay,orderTime,cPhone,cName,totAmount,preTime;
        ImageView timeMinus,timePlus;
        MaterialButton btnAccept,btnReject;
        RecyclerView rvItem;
        public OrderAcceptViewHolder( View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderDay = itemView.findViewById(R.id.order_day);
            orderTime = itemView.findViewById(R.id.order_time);

            cName = itemView.findViewById(R.id.customer_name);
            cPhone = itemView.findViewById(R.id.customer_phone);
            totAmount = itemView.findViewById(R.id.order_total);
            timeMinus = itemView.findViewById(R.id.img_timeMinus);
            timePlus = itemView.findViewById(R.id.img_timePlus);
            preTime = itemView.findViewById(R.id.tv_order_time);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnReject = itemView.findViewById(R.id.btn_reject);
            rvItem = itemView.findViewById(R.id.rv_order_item);
        }
    }
}
