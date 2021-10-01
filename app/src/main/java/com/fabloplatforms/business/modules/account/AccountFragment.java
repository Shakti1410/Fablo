package com.fabloplatforms.business.modules.account;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fabloplatforms.business.MainActivity;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.auth.PhoneActivity;
import com.fabloplatforms.business.databinding.FragmentAccountBinding;
import com.fabloplatforms.business.store.modules.outlet.web.WebLaunchActivity;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreLogoutAlert;
import com.fabloplatforms.business.utils.BusinessLogoutAlert;
import com.fabloplatforms.business.utils.BusinessPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends Fragment implements View.OnClickListener{
    private FragmentAccountBinding binding;
    private Context context;
    private FabloAlert fabloAlert;
    private BusinessLogoutAlert logoutAlert;



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {

        fabloAlert = FabloAlert.getInstance();
        logoutAlert = BusinessLogoutAlert.getInstance();
        BusinessPref businessPref = new BusinessPref(context);
        binding.tvBusinessName.setText(businessPref.getBusinessName());
        binding.tvBusinessPhone.setText(businessPref.getPhone());
        binding.lhOutletWeb.setOnClickListener(this);
        binding.linearLogout.setOnClickListener(this);
        binding.linearNotification.setOnClickListener(this);
        binding.linearAbout.setOnClickListener(this);
        binding.linearPolicy.setOnClickListener(this);
        binding.linearTerms.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        if (v == binding.linearNotification) {
            Toast.makeText(getContext(), "Coming Soon..... ", Toast.LENGTH_SHORT).show();
        }
        if (v == binding.linearAbout) {
            Toast.makeText(getContext(), "Coming Soon..... ", Toast.LENGTH_SHORT).show();
        }
        if (v == binding.linearPolicy) {
            Toast.makeText(getContext(), "Coming Soon..... ", Toast.LENGTH_SHORT).show();
        }
        if (v == binding.linearTerms) {
            Toast.makeText(getContext(), "Coming Soon..... ", Toast.LENGTH_SHORT).show();
        }
        if (v == binding.linearLogout) {
            logoutAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, "Logout", "Are You Sure you want to Logout?", "");
        }
        if (v == binding.lhOutletWeb) {
            Intent intent = new Intent(context, WebLaunchActivity.class);
            startActivity(intent);
        }

    }
    private void signout(){


        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BUSINESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(context, PhoneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}