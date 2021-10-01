package com.fabloplatforms.business.modules.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.common.BusinessAccountStatus;
import com.fabloplatforms.business.databinding.FragmentHomeBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BusinessInterface;
import com.fabloplatforms.business.modules.model.StoreListResponse;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreGlobalError;
import com.fabloplatforms.business.utils.BusinessPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloLoading;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    FragmentHomeBinding binding;
    private Context context;
    private FabloLoading fabloLoading;
    private StoreListBottomSheet storeBottomSheet;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {
        BusinessPref businessPref = new BusinessPref(getContext());
        fabloLoading = new FabloLoading();
        storeBottomSheet = new StoreListBottomSheet();
        binding.tvBusinessNameQR.setText(businessPref.getBusinessName());
        Glide.with(context).load(businessPref.getQr()).into(binding.ivBusinessQR);
        binding.tvBusiness.setOnClickListener(this);
        binding.tvStore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == binding.tvStore)
        {
            storeList();
        }

    }

    private void storeList() {

        storeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
        storeBottomSheet.show(getChildFragmentManager(), "store");
    }
}