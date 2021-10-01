package com.fabloplatforms.business.store.modules.outlet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.fabloplatforms.business.databinding.FragmentOutletBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.store.MyForeground;
import com.fabloplatforms.business.store.interfaces.OutletInterface;
import com.fabloplatforms.business.store.models.OutletStatus;
import com.fabloplatforms.business.store.models.storeavailability.StoreAvailabilityManualResponse;
import com.fabloplatforms.business.store.modules.outlet.web.WebLaunchActivity;
import com.fabloplatforms.business.store.storeretrofit.RestClientStore;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.fabloplatforms.business.store.utils.StoreLogoutAlert;
import com.fabloplatforms.business.store.utils.StoreOutletOnOFFBottomSheet;
import com.fabloplatforms.business.store.utils.StoreOutletPref;
import com.fabloplatforms.business.store.utils.StoreOutletStatusPref;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutletFragment extends Fragment implements SwitchButton.OnCheckedChangeListener, View.OnClickListener {

    FragmentOutletBinding binding;
    private Context context;
    private StoreLogoutAlert fabloAlert;
    private StoreFabloAlert storeFabloAlert;
    int event=0;
    private static final String TAG = "OutletFragment";


    public OutletFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOutletBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if (getContext() != null) {
            context = getContext();
        }
      //  switchUpdate();
        initView();
        return view;
    }



    private void initView() {
        StoreOutletPref pref = new StoreOutletPref(context);
        storeFabloAlert = new StoreFabloAlert();
        binding.outletName.setText(pref.getStoreName());
        binding.outletLocality.setText(pref.getLocality());
        fabloAlert = StoreLogoutAlert.getInstance();
        binding.switchOutlet.setOnCheckedChangeListener(this);
        binding.lhOutletWeb.setOnClickListener(this);
        binding.lhOrderHistory.setOnClickListener(this);
        binding.layoutSupport.setOnClickListener(this);
        binding.logoutBtn.setOnClickListener(this);
        checkOutletStatus();
    }

    private void checkOutletStatus() {
        StoreOutletStatusPref storeOutletStatusPref = new StoreOutletStatusPref(context);
        if (storeOutletStatusPref.getOutletStatus()) {
            binding.switchOutlet.setChecked(true);
        } else {
            binding.switchOutlet.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        if (view == binding.switchOutlet) {
            if(!binding.switchOutlet.isChecked())
            {
                StoreOutletOnOFFBottomSheet bottomSheet = new StoreOutletOnOFFBottomSheet();
                bottomSheet.setCancelable(false);
                bottomSheet.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheet.getTag());


            }
            else{
                if(event == 0)
                {
                    callManualApi();
                }

            }

        }
    }
    private void callManualApi() {
        StoreOutletPref pref = new StoreOutletPref(context);
        String store_id = pref.getId();
        OutletInterface outletInterface = RestClientStore.getRetrofitServiceInstance(context).create(OutletInterface.class);
        Call<StoreAvailabilityManualResponse> call = outletInterface.manualOFF(store_id,1);
        call.enqueue(new Callback<StoreAvailabilityManualResponse>() {
            @Override
            public void onResponse(Call<StoreAvailabilityManualResponse> call, Response<StoreAvailabilityManualResponse> response) {
                if (response.code() == StoreConstant.HTTP_RESPONSE_SUCCESS) {
                    Log.d(TAG, "onResponse: "+response.body().getMessage());
                    startforeground();
                    OutletStatus outletStatus = new OutletStatus(true);
                    StoreOutletStatusPref storeOutletStatusPref = new StoreOutletStatusPref(context);
                    storeOutletStatusPref.setOutletStatus(outletStatus);
                    EventBus.getDefault().post(outletStatus);

                } else {
                    Log.d(TAG, "onResponse: "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<StoreAvailabilityManualResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                if (t instanceof NoConnectivityException) {
                    showError("Internet error", "Seems you don't have proper internet connection right now, please try again later.");

                }
            }
        });

    }
    public void showError(String title, String description) {
        storeFabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
    }


    private void startforeground() {
        Intent intent = new Intent(context, MyForeground.class);
        intent.setAction(MyForeground.ACTION_START_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            //lower then Oreo, just start the service.
            context.startService(intent);
        }

        // context.finish();  //make sure this activity has exited. f
    }

    @Override
    public void onClick(View v) {
        if (v == binding.lhOutletWeb) {
            Intent intent = new Intent(context, WebLaunchActivity.class);
            startActivity(intent);
        }
        if (v == binding.logoutBtn) {
            showMsg("Logout","Are You Sure you want to Logout?");
        }
        if (v == binding.lhOrderHistory) {
            Intent intent = new Intent(context, OrderHistoryActivity.class);
            startActivity(intent);
        }
        if (v == binding.layoutSupport) {
            Intent intent = new Intent(context, SupportActivity.class);
            startActivity(intent);

        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String back) {
        if (back != null) {
            event=1;
            binding.switchOutlet.setChecked(true);

        }
    }

    public void showMsg(String title, String description) {
        fabloAlert.showAlert(context, StoreConstant.ALERT_ERROR, false, title, description, "");
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}