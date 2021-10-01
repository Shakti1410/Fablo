package com.fabloplatforms.business.onboard.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fabloplatforms.business.databinding.BottomSheetBusinessTypeBinding;
import com.fabloplatforms.business.databinding.BottomSheetContactTypeBinding;
import com.fabloplatforms.business.onboard.model.BusinessType;
import com.fabloplatforms.business.onboard.model.ContactPersonType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

public class ContactPersonTypeBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    BottomSheetContactTypeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetContactTypeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        binding.tvManager.setOnClickListener(this);
        binding.tvOwner.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvOwner) {
            ContactPersonType type = new ContactPersonType();

            type.setType("Owner");
            EventBus.getDefault().post(type);
        }
        if (v == binding.tvManager) {
            ContactPersonType type = new ContactPersonType();

            type.setType("Manager");
            EventBus.getDefault().post(type);
        }
    }
}
