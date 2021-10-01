package com.fabloplatforms.business.store.modules.outlet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.databinding.OrderHistoryItemBinding;
import com.fabloplatforms.business.store.models.pendingorder.PendingOrderResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewModel> {
    private Context context;
    private List<PendingOrderResponse.Items> orderlist;
    OrderHistoryItemBinding binding;

    public OrderHistoryAdapter(Context context, List<PendingOrderResponse.Items> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    @NonNull
    @NotNull
    @Override
    public OrderHistoryViewModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = OrderHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View view = binding.getRoot();
        return new OrderHistoryViewModel (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderHistoryViewModel holder, int position) {
        holder.setIsRecyclable(false);
        PendingOrderResponse.Items model = orderlist.get(position);
        String oId = model.getOrderId();
        int status = model.getStatus();
        orderStatus(status);
        String name = model.getUser().getName();
        binding.orderHistoryId.setText("ID: "+oId);
        binding.orderDay.setText(model.getTime().getOrder());
        binding.orderHistoryCname.setText(name);
        binding.tvHistoryFoodPrice.setText("₹ "+model.getPayment().getFinalPayment());
    }

    private void orderStatus(int status) {
        switch(status) {
            case 0:
                setText("Pending","#FBAF18");
                break;
            case 1:
                setText("Accepted","#388BF2");
                break;
            case 2:
                setText("Preparing","#009688");
                break;
            case 3:
                setText("Ready","#31B057");
                break;
            case 4:
                setText("Dispatched","#FC8338");

                break;
            case 5:
                setText("Delivered","#00AA13");
                break;
            case 7:
                setText("Rejected","#DF1995");
                break;

        }
    }

    private void setText(String status, String color) {
        binding.orderHistoryStatus.setText(status);
        binding.orderHistoryStatus.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class OrderHistoryViewModel extends RecyclerView.ViewHolder {
        public OrderHistoryViewModel(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
