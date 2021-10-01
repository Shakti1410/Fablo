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
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.interfaces.MenuCategoryInterface;
import com.fabloplatforms.business.store.models.MenuCategoryResponse;
import com.fabloplatforms.business.store.models.MenuSubCategoryResponse;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreFabloLoading;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {

    private Context context;
    // private List<CategoryRoomModel> categoryList; for room
    private List<MenuCategoryResponse.Item> categoryList;
    private StoreFabloLoading loading;
    private StoreFabloAlert storeFabloAlert;




//


    public CategoryAdapter(Context context, List<MenuCategoryResponse.Item> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        storeFabloAlert = new StoreFabloAlert();
    }

    @Override
    public CatViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_category,parent,false);

        return new CatViewHolder(view);

    }

    @Override
    public void onBindViewHolder( CatViewHolder holder, int position) {
        loading = new StoreFabloLoading();
        MenuCategoryResponse.Item cat = categoryList.get(position);
        holder.catName.setText(cat.getCategoryName());
        holder.img_btn.setImageResource(R.drawable.ic_animated_arrow_down);

        StoreOutletPref pref = new StoreOutletPref(context);
        String store_id = pref.getId();
     /*   if(holder.cat_rec.getVisibility() == View.VISIBLE)
        {
            MenuCategoryInterface categoryInterface = RestClient.getRetrofitServiceInstance(context).create(MenuCategoryInterface.class);
            Call<MenuSubCategoryResponse> call = categoryInterface.getSubCategories(store_id,cat.getId());
            call.enqueue(new Callback<MenuSubCategoryResponse>() {
                @Override
                public void onResponse(Call<MenuSubCategoryResponse> call, Response<MenuSubCategoryResponse> response) {
                    if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                        if (response.body() != null) {

                            Toast.makeText(context, "Subcategory Retrieved ", Toast.LENGTH_SHORT).show();
                            SubcategoryAdapter adapter = new SubcategoryAdapter(context,response.body().getItems());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            holder.cat_rec.setLayoutManager(linearLayoutManager);
                            holder.cat_rec.setAdapter(adapter);
                            holder.cat_rec.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MenuSubCategoryResponse> call, Throwable t) {

                }
            });
        }
        else
        {
            holder.cat_rec.setVisibility(View.GONE);
        }*/
        final Animation myRotation = AnimationUtils.loadAnimation(context, R.anim.rotator);
        holder.cat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    holder.img_btn.startAnimation(myRotation);


                if(holder.cat_rec.getVisibility() == View.VISIBLE)
                {
                    holder.img_btn.setImageResource(R.drawable.ic_animated_arrow_down);
                    holder.cat_rec.setVisibility(View.GONE);


                }
                else
                {
                    holder.progressBar.setVisibility(View.VISIBLE);
                    holder.img_btn.setImageResource(R.drawable.ic_animated_arrow_up);
                    StoreOutletPref pref = new StoreOutletPref(context);
                    String store_id = pref.getId();
                    MenuCategoryInterface categoryInterface = RestClientStore.getRetrofitServiceInstance(context).create(MenuCategoryInterface.class);
                    Call<MenuSubCategoryResponse> call = categoryInterface.getSubCategories(store_id,cat.getId());
                    call.enqueue(new Callback<MenuSubCategoryResponse>() {
                        @Override
                        public void onResponse(Call<MenuSubCategoryResponse> call, Response<MenuSubCategoryResponse> response) {
                            if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                                if (response.body() != null) {

                                    SubcategoryAdapter adapter = new SubcategoryAdapter(context,response.body().getItems());
                                    adapter.notifyDataSetChanged();
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                    holder.cat_rec.setLayoutManager(linearLayoutManager);
                                    holder.cat_rec.setAdapter(adapter);
                                    holder.cat_rec.setVisibility(View.VISIBLE);
                                    holder.progressBar.setVisibility(View.GONE);
                                  //  loading.hideProgress();

                                }
                            } else {
                                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MenuSubCategoryResponse> call, Throwable t) {
                            holder.progressBar.setVisibility(View.GONE);
                            if (t instanceof NoConnectivityException){
                                showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                            }
                        }
                    });
                }



            }
        });


    }

    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }
    @Override
    public int getItemCount() {

        return categoryList.size();

    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        TextView catName;
        ImageView img_btn;
        RecyclerView cat_rec;
        LinearLayout cat_layout;
        ProgressBar progressBar;

       public CatViewHolder(View itemView) {
           super(itemView);
           catName = itemView.findViewById(R.id.tvMainHeading);
           img_btn = itemView.findViewById(R.id.item_btm);
           cat_rec= itemView.findViewById(R.id.rv_category);
           cat_layout = itemView.findViewById(R.id.cat_layout);
           progressBar= itemView.findViewById(R.id.progress_cat);



       }
   }
}
