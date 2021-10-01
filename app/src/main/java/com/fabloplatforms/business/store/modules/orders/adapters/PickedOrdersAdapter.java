package com.fabloplatforms.business.store.modules.orders.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;

import java.util.List;


public class PickedOrdersAdapter extends RecyclerView.Adapter<PickedOrdersAdapter.PickedOrderViewHolder>{
    Context context;
    String deliveryName;
    String otp;
    private boolean statusOtp = false;
    private List<PendingOrderResponse.Items> orderlist;


    public PickedOrdersAdapter(Context context, List<PendingOrderResponse.Items> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    @Override
    public PickedOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_picked_order,parent,false);

        return new PickedOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder( PickedOrderViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        PendingOrderResponse.Items model = orderlist.get(position);
      //  orderNoUpdate(orderlist.size());
        String name = model.getUser().getName()+"'s Order";
        holder.orderId.setText("ID: "+model.getOrderId());
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

         //   holder.deliveyDetails.setVisibility(View.VISIBLE);
            holder.deliveryName.setText(model.getDeliveryPartner().getName());
            // holder.deliveryContact.setText(model.getDeliveryPartner().);
        }

//        holder.btnDelivered.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                orderDelevered(otp);
//            }
//        });

    }



    private void orderNoUpdate(int size) {

        SharedPreferences preferences = context.getSharedPreferences("OrderCount",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("pickedOrder",size);
        editor.apply();
    }


    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class PickedOrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId,orderTime,cName,totAmount,deliveryName,deliveryContact;
        ImageView amonutInfo;

        RecyclerView rvItem;
        LinearLayout deliveyDetails;

        public PickedOrderViewHolder( View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.picked_order_code);
            orderTime = itemView.findViewById(R.id.picked_order_time);

            amonutInfo = itemView.findViewById(R.id.picked_amount_detail_btn);
            cName = itemView.findViewById(R.id.picked_customer_name);
            totAmount = itemView.findViewById(R.id.picked_total);
            deliveryName = itemView.findViewById(R.id.tv_deliveryP_name);
            deliveryContact = itemView.findViewById(R.id.tv_deliveryP_contact);

            deliveyDetails = itemView.findViewById(R.id.layout_Pdelivery_partner);
            rvItem = itemView.findViewById(R.id.rv_picked);


        }
    }
}
