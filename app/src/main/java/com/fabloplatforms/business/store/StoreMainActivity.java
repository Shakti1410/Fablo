package com.fabloplatforms.business.store;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.fabloplatforms.business.MainActivity;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.ActivityStoreMainBinding;
import com.fabloplatforms.business.store.models.OutletStatus;
import com.fabloplatforms.business.store.modules.insights.InsightsFragment;
import com.fabloplatforms.business.store.modules.menu.MenuFragment;
import com.fabloplatforms.business.store.modules.orders.OrdersFragment;
import com.fabloplatforms.business.store.modules.orders.fragments.AcceptOrderFragment;
import com.fabloplatforms.business.store.modules.outlet.OutletFragment;
import com.fabloplatforms.business.store.modules.promos.PromosFragment;
import com.fabloplatforms.business.store.utils.StoreOutletStatusPref;
import com.fabloplatforms.business.utils.FabloLoading;
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

public class StoreMainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityStoreMainBinding binding;
    private Context context;
    private String check = "", loginType = "";
    private FabloLoading fabloLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        EventBus.getDefault().post(view);
        setContentView(view);
        context = StoreMainActivity.this;
        fabloLoading = new FabloLoading();
        checkOrder(getIntent());
    }

    private void checkOrder(Intent intent) {
        // Bundle extras = intent.getExtras();

        check = intent.getStringExtra("resourceId");
        loginType = intent.getStringExtra("LoginType");

        if (check != null) {
            AcceptOrderFragment bottomSheet = new AcceptOrderFragment();
            bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheet.getTag());

        }
        if(loginType != null && loginType.equals("business")) {
            binding.businessSwitch.setVisibility(View.VISIBLE);

        }
        initView();

    }

    private void initView() {


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Intent intent1 = new Intent();
//            String packageName = getPackageName();
//            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
//            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                intent1.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent1.setData(Uri.parse("package:" + packageName));
//                startActivity(intent1);
//            }
//        }

        binding.lvOrders.setOnClickListener(this);
        binding.lvMenu.setOnClickListener(this);
        binding.lvInsights.setOnClickListener(this);
        binding.lvPromos.setOnClickListener(this);
        binding.lvOutlet.setOnClickListener(this);
        binding.fab.setOnClickListener(this);
        binding.tvBusinessS.setOnClickListener(this);
        Speech.init(this, getPackageName());
        selectOrders();
        checkOutletStatus();

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

    }

    @Override
    public void onClick(View v) {
        if (v == binding.lvOrders) {
            selectOrders();
        }
        if (v == binding.lvMenu) {
            selectMenu();
        }
//        if (v == binding.lvInsights) {
//            selectInsights();
//        }
//        if (v == binding.lvPromos) {
//            selectPromos();
//        }
        if (v == binding.lvOutlet) {
            selectOutlet();
        }
        if (v == binding.fab) {

            showOrder();


        }
        if (v == binding.tvBusinessS) {

            showBusiness();

        }
    }

    private void showBusiness() {
        Intent intent = new Intent(StoreMainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showOrder() {
        AcceptOrderFragment bottomSheet = new AcceptOrderFragment();
        // bottomSheet.dismiss();
        bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheet.getTag());


    }

    private void selectOrders() {
        binding.ivOrders.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_orders_on));
        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_menu_off));
        binding.ivInsights.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_insights_off));
        binding.ivPromos.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_promos_off));
        binding.ivOutlet.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_outlet_off));

        binding.tvOrders.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.tvMenu.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvInsights.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvPromos.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvOutlet.setTextColor(getResources().getColor(R.color.colorIconGrey));

        binding.tvTitle.setText("Orders");

        loadFragment(new OrdersFragment());

    }

    private void selectMenu() {
        binding.ivOrders.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_orders_off));
        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_menu_on));
        binding.ivInsights.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_insights_off));
        binding.ivPromos.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_promos_off));
        binding.ivOutlet.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_outlet_off));

        binding.tvOrders.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvMenu.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.tvInsights.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvPromos.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvOutlet.setTextColor(getResources().getColor(R.color.colorIconGrey));

        binding.tvTitle.setText("Menu");

        loadFragment(new MenuFragment());

    }

    private void selectInsights() {
        binding.ivOrders.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_orders_off));
        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_menu_off));
        binding.ivInsights.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_insights_on));
        binding.ivPromos.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_promos_off));
        binding.ivOutlet.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_outlet_off));

        binding.tvOrders.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvMenu.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvInsights.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.tvPromos.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvOutlet.setTextColor(getResources().getColor(R.color.colorIconGrey));

        binding.tvTitle.setText("Business Insights");

        loadFragment(new InsightsFragment());
    }

    private void selectPromos() {
        binding.ivOrders.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_orders_off));
        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_menu_off));
        binding.ivInsights.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_insights_off));
        binding.ivPromos.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_promos_on));
        binding.ivOutlet.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_outlet_off));

        binding.tvOrders.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvMenu.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvInsights.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvPromos.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.tvOutlet.setTextColor(getResources().getColor(R.color.colorIconGrey));

        binding.tvTitle.setText("Outlet Promotions");

        loadFragment(new PromosFragment());
    }

    private void selectOutlet() {
        binding.ivOrders.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_orders_off));
        binding.ivMenu.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_menu_off));
        binding.ivInsights.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_insights_off));
        binding.ivPromos.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_promos_off));
        binding.ivOutlet.setImageDrawable(ContextCompat.getDrawable(StoreMainActivity.this, R.drawable.ic_nav_outlet_on));

        binding.tvOrders.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvMenu.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvInsights.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvPromos.setTextColor(getResources().getColor(R.color.colorIconGrey));
        binding.tvOutlet.setTextColor(getResources().getColor(R.color.colorPrimary));

        binding.tvTitle.setText("Outlet Settings");

        loadFragment(new OutletFragment());

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void checkOutletStatus() {
        StoreOutletStatusPref storeOutletStatusPref = new StoreOutletStatusPref(context);
        if (storeOutletStatusPref.getOutletStatus()) {
            binding.tvOutletStatus.setVisibility(View.VISIBLE);
            binding.fab.show();
            binding.tvOutletStatus.setText("Your outlet is online & visible to customers");
            binding.tvOutletStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSuccess));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.tvOutletStatus.setVisibility(View.GONE);
                }
            }, 10000);
        } else {
            binding.tvOutletStatus.setVisibility(View.VISIBLE);
            binding.tvOutletStatus.setText("Your outlet is offline & not visible to customers");
            binding.fab.hide();
            binding.tvOutletStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OutletStatus outletStatus) {
        if (outletStatus != null) {
            if (outletStatus.isOnline()) {
                binding.tvOutletStatus.setVisibility(View.VISIBLE);
                binding.fab.show();
                binding.tvOutletStatus.setText("Your outlet is online & visible to customers");
                binding.tvOutletStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSuccess));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.tvOutletStatus.setVisibility(View.GONE);
                    }
                }, 10000);
            } else {
                binding.tvOutletStatus.setVisibility(View.VISIBLE);
                binding.fab.hide();
                binding.tvOutletStatus.setText("Your outlet is offline & not visible to customers");
                binding.tvOutletStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError));
            }
        }
    }

    ;

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