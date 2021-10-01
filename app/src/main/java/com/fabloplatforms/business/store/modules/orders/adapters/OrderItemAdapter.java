package com.fabloplatforms.business.store.modules.orders.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.store.models.pendingorder.Addon;
import com.fabloplatforms.business.store.models.pendingorder.Product;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>{
    private Context context;
    List<Product> itemList;

    public OrderItemAdapter(Context context, List<Product> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);

        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder( OrderItemViewHolder holder, int position) {

        Product model = itemList.get(position);

        int price = model.getItemPrice();
        int quantity = model.getItemQty();
        String variant = "";
        String addOn ="";
        int varPrice = 0;
        int addPrice = 0;
        int variantSize = model.getVariants().size();
        int addonSize = model.getAddons().size();
        holder.quantity.setText(String.valueOf(quantity));
        holder.foodName.setText(model.getItemName());
        holder.price.setText("₹ "+ price);
        for (int i = 0; i<variantSize;i++)
        {

            Product.Variant var = model.getVariants().get(i);
            int s = i+1;

            String a = " " + s + ". " + var.getVariantName()+" ";
            varPrice = varPrice + var.getPrice();
            variant = variant.concat(a);
        }
        for (int j = 0; j<addonSize;j++)
        {
            Addon add = model.getAddons().get(j);
            int k = j+1;

            String b = " " + k + ". " + add.getAddonName()+" ";
            addPrice = addPrice + add.getPrice();
            addOn = addOn.concat(b);
        }
        holder.variant.setText(variant);
        holder.addons.setText(addOn);
        holder.varprice.setText("₹ "+ varPrice);
        holder.addonprice.setText("₹ "+ addPrice);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView quantity, foodName, price, variant, addons,varprice,addonprice;
        public OrderItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.tv_order_food_quantity);
            foodName = itemView.findViewById(R.id.tv_order_food_name);
            price = itemView.findViewById(R.id.tv_food_price);
            variant = itemView.findViewById(R.id.item_variant);
            addons = itemView.findViewById(R.id.item_addons);
            varprice = itemView.findViewById(R.id.variant_price);
            addonprice = itemView.findViewById(R.id.addon_price);
        }
    }
}
