package com.fabloplatforms.business.onboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.fabloplatforms.business.databinding.ListItemCategoryBinding;
import com.fabloplatforms.business.onboard.model.Category;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    ListItemCategoryBinding binding;

    private Context context;
    private List<Category.Item> categoryList;

    public CategoryRecyclerAdapter(Context context, List<Category.Item> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ListItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category.Item category = categoryList.get(position);
        if (category != null) {
            //Todo: change this
            String en_hi_category = category.getNameEnglish()+" ("+category.getNameHindi()+")";
            binding.tvCategory.setText(en_hi_category);
            Glide.with(context).load(category.getImage()).into(binding.ivCategoryImage);
            binding.lhCategory.setOnClickListener(v -> {
                EventBus.getDefault().post(category);
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
