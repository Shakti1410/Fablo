package com.fabloplatforms.business.store.modules.menu.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FoodItemBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.MenuCategoryInterface;
import com.fabloplatforms.business.store.models.MenuProductResponse;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreProductStockBottomSheet;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>   {
    private Context context;
    private List<MenuProductResponse.Item> productList;
    FoodItemBinding binding;

    public ItemAdapter(Context context, List<MenuProductResponse.Item> productList) {
        this.context = context;
        this.productList = productList;

    }



    @Override
    public ItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        binding = FoodItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View view = binding.getRoot();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ItemViewHolder holder, int position) {
       // switchUpdate();
        MenuProductResponse.Item product = productList.get(position);

        binding.tvFoodName.setText(product.getProductName());
        binding.tvFoodPrice.setText("₹ "+String.valueOf(product.getChargePrice()));
        int stock = product.getStock();
        String img = product.getPhotos().get(0);

        String pid = product.getId();
        String food_type = product.getFoodType();
        if(food_type.equals("veg"))
        {
            binding.foodType.setImageResource(R.drawable.ic_item_food_type_veg);
        }
        else if(food_type.equals("nonveg"))
        {
            binding.foodType.setImageResource(R.drawable.ic_item_food_type_non_veg);
        }
        else if(food_type.equals("egg"))
        {
            binding.foodType.setImageResource(R.drawable.ic_item_food_type_egg);
        }
        if(stock==1)
        {
            binding.swithStock.setChecked(true);

        }
        else {
            binding.swithStock.setChecked(false);
        }
        if(img != null)
        {
            Glide.with(context)
                    .load(img)
                    .into(binding.itemImg);
        }


        binding.swithStock.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                 if(!view.isChecked())
                {

                    StoreProductStockBottomSheet bottomSheet = new StoreProductStockBottomSheet();
                    Bundle args = new Bundle();
                    args.putString("productId", pid);
                    bottomSheet.setArguments(args);
                    bottomSheet.setCancelable(false);
                    bottomSheet.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheet.getTag());

                }
                else{
                     updateStock(pid,1);
                }



            }
        });
//        binding.swithStock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (buttonView == binding.swithStock) {
//                    if(!binding.swithStock.isChecked())
//                    {
//
//                    }
//                    else{
//
//                    }
//
//                }
//            }
//        });
//        binding.swithStock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//             //
////                if(stock==1){
////
////                    updateStock(pid,0);
////
////                }else {
////
////                    updateStock(pid,1);
////                }
//            }
//        });


    }


    private void updateStock(String pid, int stock) {
        MenuCategoryInterface categoryInterface = RestClientStore.getRetrofitServiceInstance(context).create(MenuCategoryInterface.class);
        Call<ResponseBody> call = categoryInterface.stockUpdateManual(pid,stock);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    Toast.makeText(context, "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error","error"+t);
                if (t instanceof NoConnectivityException){
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

//    private void switchUpdate() {
//        sPListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
//            @Override
//            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//                if(key.equals("product"))
//                {
//                    binding.swithStock.setChecked(true);
//
//                }
//
//            }
//        };
//        SharedPreferences sharedPreferences = context.getSharedPreferences("ProductState",0);
//        sharedPreferences.registerOnSharedPreferenceChangeListener(sPListener);
//
//    }




    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public ItemViewHolder( View itemView) {
            super(itemView);

        }
    }
}
