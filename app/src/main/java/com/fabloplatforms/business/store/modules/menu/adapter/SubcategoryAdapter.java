package com.fabloplatforms.business.store.modules.menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.MenuSubcategoryBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.MenuCategoryInterface;
import com.fabloplatforms.business.store.models.MenuProductResponse;
import com.fabloplatforms.business.store.models.MenuSubCategoryResponse;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.SubViewholder> {
    private Context context;
    private List<MenuSubCategoryResponse.Item> subcatList;
    private StoreFabloAlert storeFabloAlert;
 //   private List<MenuSubCategoryResponse.Product> productList;
    MenuSubcategoryBinding binding;
    private StoreFabloLoading loading;

    public SubcategoryAdapter(Context context, List<MenuSubCategoryResponse.Item> subcatList) {
        this.context = context;
        this.subcatList = subcatList;
        storeFabloAlert = new StoreFabloAlert();
    }

    @Override
    public SubViewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_subcategory,parent,false);


//        binding = MenuSubcategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        View view = binding.getRoot();
        return new SubViewholder(view);
    }

    @Override
    public void onBindViewHolder( SubViewholder holder, int position) {
        loading = new StoreFabloLoading();

        //loading.showProgress(context);
        MenuSubCategoryResponse.Item subcat = subcatList.get(position);
        holder.tvSubHeading.setText(subcat.getSubCategoryName());
        holder.itemSubBtn.setImageResource(R.drawable.ic_animated_arrow_down);
        final Animation myRotation = AnimationUtils.loadAnimation(context, R.anim.rotator);
//        OutletPref pref = new OutletPref(context);
//        String store_id = pref.getId();

        holder.subLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemSubBtn.startAnimation(myRotation);
                if(holder.rvSubcategory.getVisibility() == View.VISIBLE)
                {
                    holder.itemSubBtn.setImageResource(R.drawable.ic_animated_arrow_down);
                    holder.rvSubcategory.setVisibility(View.GONE);

                }
                else
                {
                    holder.progressSubcat.setVisibility(View.VISIBLE);
                    holder.itemSubBtn.setImageResource(R.drawable.ic_animated_arrow_up);
                    MenuCategoryInterface categoryInterface = RestClientStore.getRetrofitServiceInstance(context).create(MenuCategoryInterface.class);
                    Call<MenuProductResponse> call = categoryInterface.getProducts(subcat.getId());
                    call.enqueue(new Callback<MenuProductResponse>() {
                        @Override
                        public void onResponse(Call<MenuProductResponse> call, Response<MenuProductResponse> response) {
                            if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                                if (response.body() != null) {

                                    ItemAdapter adapter = new ItemAdapter(context,response.body().getItems());
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                    holder.rvSubcategory.setLayoutManager(linearLayoutManager);
                                    holder.rvSubcategory.setAdapter(adapter);
                                    holder.rvSubcategory.setVisibility(View.VISIBLE);
                                    holder.progressSubcat.setVisibility(View.GONE);

                                    // loading.hideProgress();

                                }
                            } else {
                                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MenuProductResponse> call, Throwable t) {
                            holder.progressSubcat.setVisibility(View.GONE);
                            if (t instanceof NoConnectivityException){
                                showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                            }
                        }
                    });

                }
            }
        });




      //  ItemAdapter adapterI = new ItemAdapter(productList);
     //   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
     //   binding.rvSubcategory.setLayoutManager(linearLayoutManager);
    //    binding.rvSubcategory.setAdapter(adapterI);
    }
    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }

    @Override
    public int getItemCount() {
        return subcatList.size();
    }

    public class SubViewholder extends RecyclerView.ViewHolder{
        TextView tvSubHeading;
        ImageView itemSubBtn;
        LinearLayout subLayout;
        RecyclerView rvSubcategory;
        ProgressBar progressSubcat;


        public SubViewholder( View itemView) {
            super(itemView);

            tvSubHeading = itemView.findViewById(R.id.tv_SubHeading);
            itemSubBtn = itemView.findViewById(R.id.item_sub_btn);
            subLayout = itemView.findViewById(R.id.sub_layout);
            rvSubcategory = itemView.findViewById(R.id.rv_subcategory);
            progressSubcat = itemView.findViewById(R.id.progress_subcat);

        }
    }
}
