package com.fabloplatforms.business.modules.outlets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.ActivityAddOutletBinding;
import com.fabloplatforms.business.modules.outlets.details.OutletDetailsFragment;
import com.fabloplatforms.business.modules.outlets.photos.OutletPhotosFragment;
import com.fabloplatforms.business.modules.outlets.timings.OutletTimingsFragment;
import com.fabloplatforms.business.onboard.fragment.CreateBusinessFragment;
import com.fabloplatforms.business.utils.Constant;

public class AddOutletActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddOutletBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOutletBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = AddOutletActivity.this;
        initView();

    }



    private void initView() {
        String status = getIntent().getStringExtra("outletstep");
        showLayout(status);
        binding.ivGoBack.setOnClickListener(this);
    }

    private void showLayout(String status) {
        if (status.contains(Constant.SWITCH_OUTLET_DETAILS)) {
            binding.ivDetails.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, getTheme()));
            binding.ivTimings.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_inactive, getTheme()));
            binding.ivPhotos.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_inactive, getTheme()));

            binding.ivDetails.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
            binding.ivTimings.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
            binding.ivPhotos.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);

            showOutletDetails();
        }
        if (status.contains(Constant.SWITCH_OUTLET_TIMING)) {
            binding.ivDetails.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, getTheme()));
            binding.ivTimings.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, getTheme()));
            binding.ivPhotos.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_inactive, getTheme()));

            binding.ivDetails.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
            binding.ivTimings.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
            binding.ivPhotos.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);

            showOutletTimings();
        }
        if (status.contains(Constant.SWITCH_OUTLET_PHOTOS)) {
            binding.ivDetails.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, getTheme()));
            binding.ivTimings.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, getTheme()));
            binding.ivPhotos.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, getTheme()));

            binding.ivDetails.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
            binding.ivTimings.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
            binding.ivPhotos.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

            showOutletPhotos();
        }
    }

    private void showOutletPhotos() {
        setFragment(new OutletPhotosFragment());
    }

    private void showOutletTimings() {
        setFragment(new OutletTimingsFragment());
    }

    private void showOutletDetails() {
        setFragment(new OutletDetailsFragment());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.ivGoBack) {
            onBackPressed();
        }
    }
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameSetup1, fragment);
        fragmentTransaction.commit();
    }



}