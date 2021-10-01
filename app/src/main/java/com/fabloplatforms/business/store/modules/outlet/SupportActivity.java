package com.fabloplatforms.business.store.modules.outlet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fabloplatforms.business.databinding.ActivitySupportBinding;


public class SupportActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySupportBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = SupportActivity.this;
        initView();
    }

    private void initView() {
        binding.ivBackSupport.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == binding.ivBackSupport) {
            onBackPressed();
        }
    }

}