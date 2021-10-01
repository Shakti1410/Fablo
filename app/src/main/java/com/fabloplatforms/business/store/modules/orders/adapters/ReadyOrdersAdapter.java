package com.fabloplatforms.business.store.modules.orders.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.OrdersCount;
import com.fabloplatforms.business.store.models.acceptorder.AcceptOrderResponse;
import com.fabloplatforms.business.store.models.acceptorder.OrderOtpRequest;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReadyOrdersAdapter extends RecyclerView.Adapter<ReadyOrdersAdapter.ReadyOrderViewHolder>{
    Context context;
    String deliveryName;
    String otp;
    private boolean statusOtp = false;
    private StoreFabloAlert storeFabloAlert;
    private List<PendingOrderResponse.Items> orderlist;


    public ReadyOrdersAdapter(Context context, List<PendingOrderResponse.Items> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
        storeFabloAlert = new StoreFabloAlert();
    }

    @Override
    public ReadyOrderViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ready_order_list,parent,false);

        return new ReadyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ReadyOrderViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        PendingOrderResponse.Items model = orderlist.get(position);
        String name = model.getUser().getName()+"'s Order";
       // orderNoUpdate(orderlist.size());
        String oRderId = model.getOrderId();
        holder.orderId.setText("ID: "+oRderId);
        holder.orderTime.setText(model.getTime().getOrder());
        holder.cName.setText(name);
        holder.totAmount.setText("₹ "+model.getPayment().getFinalPayment());
        OrderItemAdapter adapter = new OrderItemAdapter(context,model.getProducts());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rvItem.setLayoutManager(linearLayoutManager);
        holder.rvItem.setAdapter(adapter);
        deliveryName = model.getDeliveryPartner().getName().trim();
        if(!deliveryName.equals(""))
        {
            holder.deliveyDetails.setVisibility(View.VISIBLE);
            holder.deliveryName.setText(model.getDeliveryPartner().getName());
            // holder.deliveryContact.setText(model.getDeliveryPartner().);
        }
        holder.etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 otp = holder.etOtp.getText().toString().trim();
                if (otp.isEmpty()) {
                    statusOtp = false;
                } else {
                    statusOtp = true;
                }
                uiValidation(holder);
            }
        });
        holder.btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderDelevered(oRderId,otp,holder.getAdapterPosition());
            }
        });

    }
    private void orderNoUpdate(int size) {

        OrdersCount count = new OrdersCount();
        count.setPreOrder(size);
        EventBus.getDefault().post(count);
//        SharedPreferences preferences = context.getSharedPreferences("OrderCount",0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("readyOrder",size);
//        editor.apply();
    }
    private void orderDelevered(String oId,String otp,int position) {
        OrderOtpRequest request = new OrderOtpRequest();
        request.setOtp(otp);
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<AcceptOrderResponse> call = orderInterface.makeDispatch(oId,request);
        call.enqueue(new Callback<AcceptOrderResponse>() {
            @Override
            public void onResponse(Call<AcceptOrderResponse> call, Response<AcceptOrderResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    if(response.body()!=null) {
                        orderlist.remove(position);
                        notifyDataSetChanged();
                        orderNoUpdate(orderlist.size());
                        Toast.makeText(context, " " + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        Log.d("OrderAccepted", "onResponse: " + response.message());
                    }
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
    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
    private void uiValidation(ReadyOrderViewHolder holder) {
        if (statusOtp) {
            holder.btnDelivered.setEnabled(true);
        } else {
            holder.btnDelivered.setEnabled(false);
        }
    }


    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class ReadyOrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId,orderTime,cName,totAmount,deliveryName,deliveryContact;
        ImageView amonutInfo;
        TextInputEditText etOtp;
        MaterialButton btnDelivered;
        RecyclerView rvItem;
        LinearLayout deliveyDetails;

        public ReadyOrderViewHolder( View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.ready_order_code);
            orderTime = itemView.findViewById(R.id.ready_order_time);

            amonutInfo = itemView.findViewById(R.id.ready_amount_detail_btn);
            cName = itemView.findViewById(R.id.ready_customer_name);
            totAmount = itemView.findViewById(R.id.ready_total);
            deliveryName = itemView.findViewById(R.id.tv_deliveryR_name);
            deliveryContact = itemView.findViewById(R.id.tv_deliveryR_contact);
            btnDelivered = itemView.findViewById(R.id.btn_delivered);
            deliveyDetails = itemView.findViewById(R.id.layout_Rdelivery_partner);
            rvItem = itemView.findViewById(R.id.rv_ready);
            etOtp = itemView.findViewById(R.id.etOtp);

        }
    }
}
