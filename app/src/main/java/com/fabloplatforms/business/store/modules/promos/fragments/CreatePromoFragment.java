package com.fabloplatforms.business.store.modules.promos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fabloplatforms.business.databinding.FragmentCreatePromoBinding;


public class CreatePromoFragment extends Fragment {
    FragmentCreatePromoBinding binding;
   

    public CreatePromoFragment() {
        // Required empty public constructor
    }

  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePromoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
    }
}