package com.fabloplatforms.business.onboard;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fabloplatforms.business.MainActivity;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.ActivityBusinessSetupBinding;
import com.fabloplatforms.business.modules.home.HomeFragment;
import com.fabloplatforms.business.onboard.fragment.BankAccountFragment;
import com.fabloplatforms.business.onboard.fragment.BusinessLeadFragment;
import com.fabloplatforms.business.onboard.fragment.CommissionRateFragment;
import com.fabloplatforms.business.onboard.fragment.ContactPersonFragment;
import com.fabloplatforms.business.onboard.fragment.CreateBusinessFragment;
import com.fabloplatforms.business.onboard.fragment.EKycFragment;
import com.fabloplatforms.business.onboard.fragment.FssaiDetailsFragment;
import com.fabloplatforms.business.onboard.fragment.KycDetailsFragment;
import com.fabloplatforms.business.onboard.fragment.SetupCompleteFragment;
import com.fabloplatforms.business.onboard.fragment.TaxDetailsFragment;
import com.fabloplatforms.business.utils.Constant;

public class BusinessSetupActivity extends AppCompatActivity {

    ActivityBusinessSetupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusinessSetupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
    }

    private void initView() {
        String step = getIntent().getStringExtra("step");
        if (!step.isEmpty()) {
            selectStep(step);
        }

    }

    public void selectStep(String step) {
        if (step.contains(Constant.SWITCH_STEP_BUSINESS)) {
            showCreateBusiness();
        }
        if (step.contains(Constant.SWITCH_STEP_CONTACT)) {
            showContactPerson();
        }
        if (step.contains(Constant.SWITCH_STEP_BANK)) {
            showBankAccount();
        }
        if (step.contains(Constant.SWITCH_STEP_COMPLETE)) {
            showSetupComplete();
        }

        if (step.contains(Constant.SWITCH_STEP_TAX)) {
            showTax();
        }

        if (step.contains(Constant.SWITCH_STEP_KYC)) {
            showKyc();
        }

        if (step.contains(Constant.SWITCH_STEP_COMMISSION)) {
            showCommission();
        }
        if (step.contains(Constant.SWITCH_STEP_FSSAI)) {
            showFssai();
        }
        if (step.contains(Constant.SWITCH_STEP_EKYC)) {
            showEkyc();
        }
        if (step.contains("Home")) {
            showHome();
        }
    }

    private void showHome() {
        Intent intent = new Intent(BusinessSetupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
       // setFragment(new HomeFragment());
    }

    private void showEkyc() {
        setFragment(new EKycFragment());
    }

    private void showKyc() {
        setFragment(new KycDetailsFragment());
    }

    private void showFssai() {
        setFragment(new FssaiDetailsFragment());
    }

    private void showCommission() {
        setFragment(new CommissionRateFragment());
    }

    private void showTax() {
        setFragment(new TaxDetailsFragment());
    }

    private void showCreateBusiness() {
        //setFragment(new BusinessLeadFragment());
       setFragment(new CreateBusinessFragment());
    }

    private void showContactPerson() {
        setFragment(new ContactPersonFragment());
    }

    private void showBankAccount() {
        setFragment(new BankAccountFragment());
    }

    private void showSetupComplete() {
      //  binding.frameTitle.setVisibility(View.GONE);
       // binding.lhStep.setVisibility(View.GONE);
        setFragment(new SetupCompleteFragment());
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameSetup, fragment);
        fragmentTransaction.commit();
    }

}