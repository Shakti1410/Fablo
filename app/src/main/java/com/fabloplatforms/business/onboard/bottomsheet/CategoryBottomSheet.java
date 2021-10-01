package com.fabloplatforms.business.onboard.bottomsheet;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fabloplatforms.business.databinding.BottomSheetCategoryBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.CategoryInterface;
import com.fabloplatforms.business.onboard.adapter.CategoryRecyclerAdapter;
import com.fabloplatforms.business.onboard.model.Category;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryBottomSheet extends BottomSheetDialogFragment {

    BottomSheetCategoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        binding.recyclerCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchCategory();
    }

    private void fetchCategory() {
        CategoryInterface categoryInterface = RestClient.getRetrofitServiceInstance(getContext()).create(CategoryInterface.class);
        Call<Category> call = categoryInterface.getCategories();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(getContext(), response.body().getItems());
                        categoryRecyclerAdapter.notifyDataSetChanged();
                        binding.recyclerCategory.setAdapter(categoryRecyclerAdapter);
                        binding.progress.setVisibility(View.GONE);
                        binding.recyclerCategory.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof NoConnectivityException){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 1000);
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }
}