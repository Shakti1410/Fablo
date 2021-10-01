package com.fabloplatforms.business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;

import com.fabloplatforms.business.common.ScheduleKycBottomSheet;
import com.fabloplatforms.business.databinding.ActivityMainBinding;
import com.fabloplatforms.business.modules.account.AccountFragment;
import com.fabloplatforms.business.modules.activity.ActivityFragment;
import com.fabloplatforms.business.modules.home.HomeFragment;
import com.fabloplatforms.business.modules.manage.ManageFragment;
import com.fabloplatforms.business.modules.outlets.BusinessOutletFragment;
import com.fabloplatforms.business.utils.BusinessPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.GlobalError;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import net.gotev.speech.Speech;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    private Context context;
    private FabloAlert fabloAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = MainActivity.this;
        initView();
    }

    private void initView() {
        batryMethod();
        fabloAlert = FabloAlert.getInstance();
        binding.lvOverview.setOnClickListener(this);
        binding.lvStore.setOnClickListener(this);
        binding.lvManage.setOnClickListener(this);
        binding.lvActivity.setOnClickListener(this);
        binding.lvAccount.setOnClickListener(this);

        selectHome();
       // getKycStatus();



    }

    private void batryMethod() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent1 = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent1.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent1.setData(Uri.parse("package:" + packageName));
                startActivity(intent1);
            }
        }
        Speech.init(this, getPackageName());
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }

    private void selectHome() {
        binding.ivOverview.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_solid_overview));
        binding.ivStore.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_store));
        binding.ivManage.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_manage));
        binding.ivActivity.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_activity));
        binding.ivAccount.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_account));

        binding.tvOverview.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.tvStore.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvManage.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvActivity.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvAccount.setTextColor(getResources().getColor(R.color.colorIconGrey));

        loadFragment(new HomeFragment());
    }

    private void selectStore() {
        binding.ivOverview.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_overview));
        binding.ivStore.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_solid_store));
        binding.ivManage.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_manage));
        binding.ivActivity.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_activity));
        binding.ivAccount.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_account));

        binding.tvOverview.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvStore.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.tvManage.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvAccount.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvAccount.setTextColor(getResources().getColor(R.color.colorIconGrey));

        loadFragment(new BusinessOutletFragment());
    }

    private void selectManage() {
        binding.ivOverview.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_overview));
        binding.ivStore.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_store));
        binding.ivManage.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_solid_manage));
        binding.ivActivity.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_activity));
        binding.ivAccount.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_account));

        binding.tvOverview.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvStore.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvManage.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.tvActivity.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvAccount.setTextColor(getResources().getColor(R.color.colorIconGrey));

        loadFragment(new ManageFragment());
    }


    private void selectActivity() {
        binding.ivOverview.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_overview));
        binding.ivStore.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_store));
        binding.ivManage.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_manage));
        binding.ivActivity.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_solid_activity));
        binding.ivAccount.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_account));

        binding.tvOverview.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvStore.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvManage.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvActivity.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.tvAccount.setTextColor(getResources().getColor(R.color.colorIconGrey));

        loadFragment(new ActivityFragment());
    }


    private void selectAccount() {
        binding.ivOverview.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_overview));
        binding.ivStore.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_store));
        binding.ivManage.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_manage));
        binding.ivActivity.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_out_activity));
        binding.ivAccount.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fi_solid_account));

        binding.tvOverview.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvStore.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvManage.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvActivity.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvAccount.setTextColor(getResources().getColor(R.color.colorPrimary));

        loadFragment(new AccountFragment());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.lvOverview) {
            selectHome();
        }
        if (v == binding.lvStore) {
            selectStore();
        }
        if (v == binding.lvManage) {
            selectManage();
        }
        if (v == binding.lvActivity) {
            selectActivity();
        }
        if (v == binding.lvAccount) {
            selectAccount();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getKycStatus() {
        BusinessPref businessPref = new BusinessPref(context);
        if (businessPref.getKycStatus() == Constant.KYC_STATUS_NOT_SUBMIT || businessPref.getKycStatus() == Constant.KYC_STATUS_REJECTED) {
            showKycSchedulerBottomSheet(businessPref.getKycStatus());
        }
    }

    private void showKycSchedulerBottomSheet(Integer status) {
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        ScheduleKycBottomSheet scheduleKycBottomSheet = new ScheduleKycBottomSheet();
        scheduleKycBottomSheet.setArguments(bundle);
        scheduleKycBottomSheet.show(getSupportFragmentManager(), "kyc");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GlobalError globalError) {
        if (globalError != null) {
            showError(globalError.getTitle(), globalError.getDescription());
        }
    }

    public void showError(String title, String description) {
        fabloAlert.showAlert(context, Constant.ALERT_ERROR, false, title, description, "");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}