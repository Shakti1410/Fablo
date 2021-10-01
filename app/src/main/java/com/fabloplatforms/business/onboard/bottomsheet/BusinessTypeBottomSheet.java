package com.fabloplatforms.business.onboard.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fabloplatforms.business.databinding.BottomSheetBusinessTypeBinding;
import com.fabloplatforms.business.onboard.model.BusinessType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

public class BusinessTypeBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    BottomSheetBusinessTypeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetBusinessTypeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        binding.tvSeller.setOnClickListener(this);
        binding.tvServiceProvider.setOnClickListener(this);
        binding.tvSellerService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvSeller) {
            BusinessType businessType = new BusinessType();
            businessType.setId(1);
            businessType.setType("Seller");
            EventBus.getDefault().post(businessType);
        }
        if (v == binding.tvServiceProvider) {
            BusinessType businessType = new BusinessType();
            businessType.setId(2);
            businessType.setType("Service provider");
            EventBus.getDefault().post(businessType);
        }
        if (v == binding.tvSellerService) {
            BusinessType businessType = new BusinessType();
            businessType.setId(3);
            businessType.setType("Seller & service provider");
            EventBus.getDefault().post(businessType);
        }
    }
}
