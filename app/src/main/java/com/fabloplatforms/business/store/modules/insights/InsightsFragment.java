package com.fabloplatforms.business.store.modules.insights;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentInsightsBinding;
import com.fabloplatforms.business.store.modules.insights.fragments.ComplaintsFragment;
import com.fabloplatforms.business.store.modules.insights.fragments.DeleveredOrderFragment;
import com.fabloplatforms.business.store.modules.insights.fragments.PreparationTimeFragment;
import com.fabloplatforms.business.store.modules.insights.fragments.RejectedOrderFragment;
import com.fabloplatforms.business.store.modules.insights.fragments.TopDishesFragment;


public class InsightsFragment extends Fragment {
    FragmentInsightsBinding binding;

    public InsightsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInsightsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {

        DeleveredOrderFragment deleveredFragment = new DeleveredOrderFragment();
        RejectedOrderFragment rejectedFragment = new RejectedOrderFragment();
        PreparationTimeFragment preparationFragment =  new PreparationTimeFragment();
        ComplaintsFragment complaintsFragment = new ComplaintsFragment();
        TopDishesFragment dishesFragment = new TopDishesFragment();

        FragmentManager manager= getChildFragmentManager();//create an instance of fragment manager

        FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.delivered_orderContainer, deleveredFragment, "Frag_1");
        transaction.add(R.id.rejected_orderContainer, rejectedFragment, "Frag_2");
        transaction.add(R.id.preparation_timeContainer, preparationFragment, "Frag_3");
        transaction.add(R.id.complaintsContainer, complaintsFragment, "Frag_4");
        transaction.add(R.id.top_dishesContainer, dishesFragment, "Frag_5");

        transaction.commit();
    }
}