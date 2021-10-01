package com.fabloplatforms.business.modules.outlets.photos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentOutletPhotosBinding;

public class OutletPhotosFragment extends Fragment {

    private FragmentOutletPhotosBinding binding;
    private Context context;

    public OutletPhotosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOutletPhotosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {
    }
}