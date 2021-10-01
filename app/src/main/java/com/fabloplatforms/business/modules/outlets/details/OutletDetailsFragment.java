package com.fabloplatforms.business.modules.outlets.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fabloplatforms.business.common.MapBoxActivity;
import com.fabloplatforms.business.databinding.FragmentOutletDetailsBinding;

public class OutletDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentOutletDetailsBinding binding;
    private Context context;
    private boolean outletLocation = false;
    private boolean outletAddress = false;
    private boolean outletRestaurantId = false;
    private boolean outletPhone = false;
    private boolean outletPassword = false;

    private static final String TAG = "OutletDetailsFragment";
    public OutletDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOutletDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {
        binding.etLocation.setOnClickListener(this);
        binding.btnProceed.setOnClickListener(this);
        binding.etLine1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String address = binding.etLine1.getText().toString().trim();
                if (address.isEmpty()) {
                    outletAddress = false;
                } else {
                    outletAddress = true;
                }
                checkValidation();
            }
        });
        binding.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = binding.etPhone.getText().toString().trim();
                if (phone.length() == 10) {
                    outletPhone = true;
                } else {
                    outletPhone = false;
                }
                checkValidation();
            }
        });
        binding.etRestId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = binding.etRestId.getText().toString().trim();
                if (phone.length() == 10) {
                    outletRestaurantId = true;
                } else {
                    outletRestaurantId = false;
                }
                checkValidation();
            }
        });
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = binding.etPassword.getText().toString().trim();
                if (phone.length() == 10) {
                    outletPassword = true;
                } else {
                    outletPassword = false;
                }
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        if (outletLocation && outletAddress && outletPhone && outletRestaurantId && outletPassword) {
            binding.btnProceed.setEnabled(true);
        } else {
            binding.btnProceed.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.etLocation) {
            Intent intent = new Intent(context, MapBoxActivity.class);
            startActivity(intent);
        }
    }

}