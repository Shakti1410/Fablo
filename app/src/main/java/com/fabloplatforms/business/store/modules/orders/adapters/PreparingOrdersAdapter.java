package com.fabloplatforms.business.store.modules.orders.adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.OrderInterface;
import com.fabloplatforms.business.store.models.OrdersCount;
import com.fabloplatforms.business.store.models.acceptorder.AcceptOrderResponse;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;

import com.google.android.material.button.MaterialButton;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreparingOrdersAdapter extends RecyclerView.Adapter<PreparingOrdersAdapter.PreparingOrderViewHolder> {
    private Context context;
    String deliveryName;
    private StoreFabloAlert storeFabloAlert = new StoreFabloAlert();
    int status;
    // private List<CategoryRoomModel> categoryList; for room
    private List<PendingOrderResponse.Items> orderlist;


    public PreparingOrdersAdapter(Context context, List<PendingOrderResponse.Items> orderlist) {
        this.context = context;
        this.orderlist = orderlist;

    }

    @Override
    public PreparingOrderViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_preparing_order,parent,false);

        return new PreparingOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreparingOrderViewHolder holder,  int position) {
        holder.setIsRecyclable(false);
        PendingOrderResponse.Items model = orderlist.get(position);
        String preTime= model.getTime().getPreparation();
     //   orderNoUpdate(orderlist.size());
        String oId = model.getOrderId();
         status = model.getStatus();

        if(status == 1)
        {
            holder.btnReady.setText("Start Preparing");
            holder.btnReady.setBackgroundColor(Color.parseColor("#51C273"));

        }
        else if(status == 2)
        {
            holder.btnReady.setText("Mark Ready");
            holder.btnReady.setBackgroundColor(Color.parseColor("#00AA13"));


        }
        String name = model.getUser().getName()+"'s Order";
        Integer discount = model.getPayment().getDiscount();
        holder.orderId.setText("ID: "+oId);
        holder.orderTime.setText(model.getTime().getOrder());
        holder.amountPerson.setText("₹ xyz");
        holder.offerAmount.setText(String.valueOf(discount));
        holder.taxAmount.setText("₹ xyz");

        holder.cName.setText(name);
        holder.totAmount.setText("₹ "+model.getPayment().getFinalPayment());
        holder.amonutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.amountDetails.getVisibility() == View.VISIBLE)
                {
                    holder.amountDetails.setVisibility(View.GONE);
                    holder.amonutInfo.setImageResource(R.drawable.ic_fi_rr_angle_small_up);
                }
                else {
                    holder.amountDetails.setVisibility(View.VISIBLE);
                    holder.amonutInfo.setImageResource(R.drawable.ic_fi_rr_angle_small_down);
                }

            }
        });
        holder.btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status == 1)
                {

                   callStart(oId,holder);
                }
                else if(status == 2)
                {
                    callReady(oId,holder.getAdapterPosition());
                }
            }
        });
     //   holder.progress_btn.setText("Preparing");
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


    }

    private void orderNoUpdate(int size) {
        OrdersCount count = new OrdersCount();
        count.setPreOrder(size);
        EventBus.getDefault().post(count);
//        SharedPreferences preferences = context.getSharedPreferences("OrderCount",0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("preOrder",size);
//        editor.apply();
    }

    /* Animation Fun
        private void callAnim(PreparingOrderViewHolder holder, String preTime) {
            final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(holder.progressBar, "progress", holder.progressBar.getProgress(), 100).setDuration(20000);
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int progress = (int) valueAnimator.getAnimatedValue();
                    holder.progressBar.setProgress(progress);
                    holder.progress_btn.setText(String.valueOf(100-progress)+ " Min");

                    if(progress==100)
                    {
                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(500); //You can manage the blinking time with this parameter
                        anim.setStartOffset(20);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        holder.btnReady.startAnimation(anim);
                        holder.progress_btn.setText("Time Over");
                    }



                }
            });
            objectAnimator.start();

        }
    */
    private void callReady(String oId, int position) {
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<AcceptOrderResponse> call = orderInterface.makeReady(oId);
        call.enqueue(new Callback<AcceptOrderResponse>() {
            @Override
            public void onResponse(Call<AcceptOrderResponse> call, Response<AcceptOrderResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {

                    orderlist.remove(position);
                    notifyDataSetChanged();
                    orderNoUpdate(orderlist.size());

                } else {
                    Log.d("PreparingOrderAdapter", "onResponse: "+response.message());

                }
            }

            @Override
            public void onFailure(Call<AcceptOrderResponse> call, Throwable t) {
                Log.d("PreparingOrderAdapter", "onFailure: "+t.getMessage());
            }
        });

    }

    private void callStart(String oId, PreparingOrderViewHolder holder) {
        OrderInterface orderInterface = RestClientStore.getRetrofitServiceOrder(context).create(OrderInterface.class);
        Call<AcceptOrderResponse> call = orderInterface.startPreparing(oId);
        call.enqueue(new Callback<AcceptOrderResponse>() {
            @Override
            public void onResponse(Call<AcceptOrderResponse> call, Response<AcceptOrderResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    holder.btnReady.setText("Mark Ready");
                    holder.btnReady.setBackgroundColor(Color.parseColor("#00AA13"));
                     status = status+1;
                    Log.d("PreparingOrderAdapter", "onResponse: "+response.body());

                } else {
                    Log.d("PreparingOrderAdapter", "onResponse: "+response.message());

                }
            }

            @Override
            public void onFailure(Call<AcceptOrderResponse> call, Throwable t) {
                Log.d("PreparingOrderAdapter", "onFailure: "+t.getMessage());
                if (t instanceof NoConnectivityException){
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });
    }
    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class PreparingOrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId,orderTime,deliveryName,deliveryContact,reachTime,timeZone,cName,totAmount,amountPerson,offerAmount,taxAmount,amountStatus,progress_btn;
        ImageView amonutInfo,delivery_img;
        MaterialButton btnReady;
       // FrameLayout btnReady;
        ProgressBar progressBar;
        RecyclerView rvItem;
        LinearLayout amountDetails,deliveyDetails;
        public PreparingOrderViewHolder( View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_code);
            orderTime = itemView.findViewById(R.id.pre_order_time);
            amountPerson = itemView.findViewById(R.id.amount_boy);
            offerAmount = itemView.findViewById(R.id.amount_promo);
            taxAmount = itemView.findViewById(R.id.amount_tax);
            amonutInfo = itemView.findViewById(R.id.amount_detail_btn);
            cName = itemView.findViewById(R.id.pre_customer_name);
            totAmount = itemView.findViewById(R.id.order_total);
            amountDetails = itemView.findViewById(R.id.amount_info_layout);
            btnReady = itemView.findViewById(R.id.btn_ready);
            rvItem = itemView.findViewById(R.id.rv_preparing);
//            progressBar = itemView.findViewById(R.id.progress_bar);
//            progress_btn = itemView.findViewById(R.id.progress_btn);
            deliveyDetails = itemView.findViewById(R.id.layout_delivery_partner);
            deliveryName = itemView.findViewById(R.id.tv_delivery_name);
            deliveryContact = itemView.findViewById(R.id.tv_delivery_contact);
            reachTime = itemView.findViewById(R.id.tv_delivery_time);
            delivery_img = itemView.findViewById(R.id.delivery_img);
        }
    }
}
