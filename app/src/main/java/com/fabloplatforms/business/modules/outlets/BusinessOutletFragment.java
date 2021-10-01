package com.fabloplatforms.business.modules.outlets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentAddoutletBinding;


import org.jetbrains.annotations.NotNull;

public class BusinessOutletFragment extends Fragment implements View.OnClickListener {

    private FragmentAddoutletBinding binding;
    private Context context;

    public BusinessOutletFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddoutletBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if (getContext() != null) {
            context = getContext();
        }
        initView();
        return view;
    }

    private void initView() {
        binding.btnAddOutlet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnAddOutlet) {
            Intent intent = new Intent(context, OutletStatusActivity.class);
            startActivity(intent);
        }
    }
}