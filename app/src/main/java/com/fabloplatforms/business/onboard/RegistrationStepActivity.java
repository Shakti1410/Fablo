package com.fabloplatforms.business.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.fabloplatforms.business.MainActivity;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.ActivityMainBinding;
import com.fabloplatforms.business.databinding.ActivityRegistrationStepBinding;
import com.fabloplatforms.business.onboard.fragment.BankAccountFragment;
import com.fabloplatforms.business.onboard.fragment.CommissionRateFragment;
import com.fabloplatforms.business.onboard.fragment.ContactPersonFragment;
import com.fabloplatforms.business.onboard.fragment.CreateBusinessFragment;
import com.fabloplatforms.business.onboard.fragment.EKycFragment;
import com.fabloplatforms.business.onboard.fragment.FssaiDetailsFragment;
import com.fabloplatforms.business.onboard.fragment.KycDetailsFragment;
import com.fabloplatforms.business.onboard.fragment.TaxDetailsFragment;
import com.fabloplatforms.business.utils.Constant;

public class RegistrationStepActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityRegistrationStepBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationStepBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = RegistrationStepActivity.this;
        initView();
    }

    private void initView() {
        String step = getIntent().getStringExtra("status");
        binding.basicDetailsCard.setOnClickListener(this);
        binding.bankDetailsCard.setOnClickListener(this);
        binding.contactPersoncard.setOnClickListener(this);
        binding.eKycCard.setOnClickListener(this);
        binding.fssaiDetailsCard.setOnClickListener(this);
        binding.commissionRateCard.setOnClickListener(this);
        binding.kycDetailsCard.setOnClickListener(this);
        binding.taxDetailsCard.setOnClickListener(this);
        switchSteps(step);
    }

    private void switchSteps(String step) {
        switch (step) {
            case "Home":
                Intent intent = new Intent(context, BusinessSetupActivity.class);
                intent.putExtra("step", "Home");
                startActivity(intent);
                break;

            case Constant.SWITCH_STEP_BUSINESS:
              //  binding.basicDetailsCard.setClickable(true);
                binding.basicDetailsCard.setEnabled(true);
                binding.contactPersoncard.setEnabled(false);
                binding.bankDetailsCard.setEnabled(false);
                binding.taxDetailsCard.setEnabled(false);
                binding.fssaiDetailsCard.setEnabled(false);
                binding.kycDetailsCard.setEnabled(false);
                binding.commissionRateCard.setEnabled(false);
                binding.eKycCard.setEnabled(false);

                binding.contactPersoncard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.bankDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.taxDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.fssaiDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.kycDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.commissionRateCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.eKycCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));

                break;
            case Constant.SWITCH_STEP_CONTACT:

                binding.basicDetailsCard.setEnabled(false);
                binding.contactPersoncard.setEnabled(true);
                binding.bankDetailsCard.setEnabled(false);
                binding.taxDetailsCard.setEnabled(false);
                binding.fssaiDetailsCard.setEnabled(false);
                binding.kycDetailsCard.setEnabled(false);
                binding.commissionRateCard.setEnabled(false);
                binding.eKycCard.setEnabled(false);
                binding.tvTitleBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.basicDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.bankDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.taxDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.fssaiDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.kycDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.commissionRateCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.eKycCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));

                binding.ivBasic.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active,context.getTheme()));
                binding.ivBasic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                break;

            case Constant.SWITCH_STEP_BANK:

                binding.basicDetailsCard.setEnabled(false);
                binding.contactPersoncard.setEnabled(false);
                binding.bankDetailsCard.setEnabled(true);
                binding.taxDetailsCard.setEnabled(false);
                binding.fssaiDetailsCard.setEnabled(false);
                binding.kycDetailsCard.setEnabled(false);
                binding.commissionRateCard.setEnabled(false);
                binding.eKycCard.setEnabled(false);
                binding.tvTitleBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.basicDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.contactPersoncard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.taxDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.fssaiDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.kycDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.commissionRateCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.eKycCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));


                binding.ivBasic.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active,context.getTheme()));
                binding.ivBasic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewOneContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivContact.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivContact.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                break;

            case Constant.SWITCH_STEP_TAX:

                binding.basicDetailsCard.setEnabled(false);
                binding.contactPersoncard.setEnabled(false);
                binding.bankDetailsCard.setEnabled(false);
                binding.taxDetailsCard.setEnabled(true);
                binding.fssaiDetailsCard.setEnabled(false);
                binding.kycDetailsCard.setEnabled(false);
                binding.commissionRateCard.setEnabled(false);
                binding.eKycCard.setEnabled(false);
                binding.tvTitleBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBank.setTextColor(getResources().getColor(android.R.color.white));

                binding.basicDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.contactPersoncard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.bankDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.fssaiDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.kycDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.commissionRateCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.eKycCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));


                binding.ivBasic.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active,context.getTheme()));
                binding.ivBasic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewOneContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivContact.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivContact.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivBank.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivBank.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

                break;

            case Constant.SWITCH_STEP_FSSAI:
                binding.basicDetailsCard.setEnabled(false);
                binding.contactPersoncard.setEnabled(false);
                binding.bankDetailsCard.setEnabled(false);
                binding.taxDetailsCard.setEnabled(false);
                binding.fssaiDetailsCard.setEnabled(true);
                binding.kycDetailsCard.setEnabled(false);
                binding.commissionRateCard.setEnabled(false);
                binding.eKycCard.setEnabled(false);
                binding.tvTitleBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleTax.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesTax.setTextColor(getResources().getColor(android.R.color.white));

                binding.basicDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.contactPersoncard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.bankDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.taxDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.kycDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.commissionRateCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.eKycCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));

                binding.ivBasic.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active,context.getTheme()));
                binding.ivBasic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewOneContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivContact.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivContact.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivBank.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivBank.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneTax.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoTax.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivTax.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivTax.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

                break;

            case Constant.SWITCH_STEP_KYC:

                binding.basicDetailsCard.setEnabled(false);
                binding.contactPersoncard.setEnabled(false);
                binding.bankDetailsCard.setEnabled(false);
                binding.taxDetailsCard.setEnabled(false);
                binding.fssaiDetailsCard.setEnabled(false);
                binding.kycDetailsCard.setEnabled(true);
                binding.commissionRateCard.setEnabled(false);
                binding.eKycCard.setEnabled(false);
                binding.tvTitleBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleTax.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesTax.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleFssai.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesFssai.setTextColor(getResources().getColor(android.R.color.white));

                binding.basicDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.contactPersoncard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.bankDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.taxDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.fssaiDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.commissionRateCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));
                binding.eKycCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));

                binding.ivBasic.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active,context.getTheme()));
                binding.ivBasic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewOneContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivContact.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivContact.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivBank.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivBank.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneTax.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoTax.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivTax.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivTax.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneFssai.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoFssai.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivFssai.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivFssai.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

                break;

            case Constant.SWITCH_STEP_COMMISSION:

                binding.basicDetailsCard.setEnabled(false);
                binding.contactPersoncard.setEnabled(false);
                binding.bankDetailsCard.setEnabled(false);
                binding.taxDetailsCard.setEnabled(false);
                binding.fssaiDetailsCard.setEnabled(false);
                binding.kycDetailsCard.setEnabled(false);
                binding.commissionRateCard.setEnabled(true);
                binding.eKycCard.setEnabled(false);
                binding.tvTitleBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleTax.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesTax.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleFssai.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesFssai.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleKyc.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesKyc.setTextColor(getResources().getColor(android.R.color.white));

                binding.basicDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.contactPersoncard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.bankDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.taxDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.fssaiDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.kycDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.eKycCard.setCardBackgroundColor(getResources().getColor(R.color.colorDivider));

                binding.ivBasic.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active,context.getTheme()));
                binding.ivBasic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewOneContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivContact.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivContact.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivBank.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivBank.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneTax.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoTax.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivTax.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivTax.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneFssai.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoFssai.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivFssai.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivFssai.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneKyc.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoKyc.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivKyc.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivKyc.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

                break;

            case Constant.SWITCH_STEP_EKYC:

                binding.basicDetailsCard.setEnabled(false);
                binding.contactPersoncard.setEnabled(false);
                binding.bankDetailsCard.setEnabled(false);
                binding.taxDetailsCard.setEnabled(false);
                binding.fssaiDetailsCard.setEnabled(false);
                binding.kycDetailsCard.setEnabled(false);
                binding.commissionRateCard.setEnabled(false);
                binding.eKycCard.setEnabled(true);
                binding.tvTitleBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBasic.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesContact.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesBank.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleTax.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesTax.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleFssai.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesFssai.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleKyc.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesKyc.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvTitleCommission.setTextColor(getResources().getColor(android.R.color.white));
                binding.tvDesCommission.setTextColor(getResources().getColor(android.R.color.white));

                binding.basicDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.contactPersoncard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.bankDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.taxDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.fssaiDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.kycDetailsCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.commissionRateCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                binding.ivBasic.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active,context.getTheme()));
                binding.ivBasic.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBasic.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewOneContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoContact.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivContact.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivContact.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoBank.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivBank.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivBank.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneTax.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoTax.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivTax.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivTax.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneFssai.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoFssai.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivFssai.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivFssai.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneKyc.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoKyc.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivKyc.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivKyc.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                binding.viewOneCommision.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.viewTwoCommision.setBackgroundColor(getResources().getColor(R.color.quantum_googgreen));
                binding.ivCommision.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_circle_step_active, context.getTheme()));
                binding.ivCommision.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.basicDetailsCard) {
            Intent intent = new Intent(context, BusinessSetupActivity.class);
            intent.putExtra("step", "business");
            startActivity(intent);
            //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //   finish();

        }
        if (v == binding.bankDetailsCard) {
            Intent intent = new Intent(context, BusinessSetupActivity.class);
            intent.putExtra("step", "bank");
            startActivity(intent);
        }
        if (v == binding.contactPersoncard) {
            Intent intent = new Intent(context, BusinessSetupActivity.class);
            intent.putExtra("step", "contact");
            startActivity(intent);
        }
        if (v == binding.eKycCard) {
            Intent intent = new Intent(context, BusinessSetupActivity.class);
            intent.putExtra("step", "Ekyc");
            startActivity(intent);
        }
        if (v == binding.fssaiDetailsCard) {
            Intent intent = new Intent(context, BusinessSetupActivity.class);
            intent.putExtra("step", "fssai");
            startActivity(intent);
        }
        if (v == binding.commissionRateCard) {
            Intent intent = new Intent(context, BusinessSetupActivity.class);
            intent.putExtra("step", "commission");
            startActivity(intent);
        }
        if (v == binding.kycDetailsCard) {
            Intent intent = new Intent(context, BusinessSetupActivity.class);
            intent.putExtra("step", "kyc");
            startActivity(intent);
        } if (v == binding.taxDetailsCard) {
            Intent intent = new Intent(context, BusinessSetupActivity.class);
            intent.putExtra("step", "tax");
            startActivity(intent);
        }

    }
}