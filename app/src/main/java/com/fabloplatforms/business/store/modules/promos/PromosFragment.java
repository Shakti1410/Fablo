package com.fabloplatforms.business.store.modules.promos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentPromosBinding;
import com.fabloplatforms.business.store.modules.promos.fragments.CreatePromoFragment;
import com.fabloplatforms.business.store.modules.promos.fragments.TrackPerformanceFragment;


public class PromosFragment extends Fragment implements View.OnClickListener {
    FragmentPromosBinding binding;
    private Context context;

    public PromosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPromosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();

        initView();
        return view;
    }

    private void initView() {

        binding.tvCreatePromo.setOnClickListener(this);
        binding.tvTrackPerformance.setOnClickListener(this);


        showPromo();
    }

    private void showPreparing() {
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvCreatePromo) {
            showPromo();
        }
        if (v == binding.tvTrackPerformance) {
            showTrack();
        }
    }

    private void showPromo() {
        binding.tvCreatePromo.setBackground(ContextCompat.getDrawable(context, R.color.colorWhite));
        binding.tvCreatePromo.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        binding.promoView.setBackground(ContextCompat.getDrawable(context, R.color.colorPrimary));

        binding.tvTrackPerformance.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
        binding.tvTrackPerformance.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        binding.trackView.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_icon_shade));


        setFragment(new CreatePromoFragment());
    }

    private void showTrack() {
        binding.tvCreatePromo.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));
        binding.tvCreatePromo.setTextColor(ContextCompat.getColor(context, R.color.colorTextTitle));
        binding.promoView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_icon_shade));

        binding.tvTrackPerformance.setBackground(ContextCompat.getDrawable(context, R.color.colorWhite));
        binding.tvTrackPerformance.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        binding.trackView.setBackground(ContextCompat.getDrawable(context, R.color.colorPrimary));
        setFragment(new TrackPerformanceFragment());
    }
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameSetuppromo, fragment);
        fragmentTransaction.commit();
    }
}