package com.fabloplatforms.business.onboard.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentBusinessLeadBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BusinessInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.bottomsheet.CityBottomSheet;
import com.fabloplatforms.business.onboard.model.BusinessLeadModel;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.onboard.model.CreateLeadRequest;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusinessLeadFragment extends Fragment implements View.OnClickListener {

    FragmentBusinessLeadBinding binding;
    private CityBottomSheet cityBottomSheet;
    private City.Item cityData;
    private String uid;
    private boolean statusBusinessName = false;
    private boolean statusFullName = false;
    private boolean statusAddress = false;
    private boolean statusPhone = false;
    private boolean statusCity = false;
    private boolean statusPincode = false;
    private static final String TAG = "BusinessLeadFragment";
    private FabloLoading fabloLoading;
    private FabloAlert fabloAlert;



    public BusinessLeadFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBusinessLeadBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        initView();
        return view;
    }
    private void initView() {
        fabloLoading = FabloLoading.getInstance();
        fabloAlert = FabloAlert.getInstance();
        cityBottomSheet = new CityBottomSheet();
        binding.etCityName.setOnClickListener(this);
        binding.btnBusiProceed.setOnClickListener(this);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            uid = firebaseAuth.getCurrentUser().getUid();
            String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
            String trimPhone = phone.replace("+91", "");
            binding.etPhNumber.setText(trimPhone);
        }
        binding.etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String fullName = binding.etFullName.getText().toString();
                if (fullName.isEmpty()) {
                    statusFullName = false;
                } else {
                    statusFullName = true;
                }
                checkValidation();
            }
        });
        binding.etPhNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = binding.etPhNumber.getText().toString();
                if (phone.isEmpty()) {
                    statusPhone = false;
                } else {
                    statusPhone = true;
                }
                checkValidation();
            }
        });
        binding.etBusiName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String businessName = binding.etBusiName.getText().toString();
                if (businessName.isEmpty()) {
                    statusBusinessName = false;
                } else {
                    statusBusinessName = true;
                }
                checkValidation();
            }
        });
        binding.etCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cityName = binding.etCityName.getText().toString();
                if (cityName.isEmpty()) {
                    statusCity = false;
                } else {
                    statusCity = true;
                }
                checkValidation();
            }
        });
        binding.etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pinCode = binding.etPincode.getText().toString();
                if (pinCode.isEmpty()) {
                    statusPincode = false;
                } else {
                    statusPincode = true;
                }
                checkValidation();
            }
        });
        binding.etaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String fullName = binding.etFullName.getText().toString();
                if (fullName.isEmpty()) {
                    statusAddress = false;
                } else {
                    statusAddress = true;
                }
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        if (statusFullName  && statusBusinessName && statusCity && statusPincode && statusAddress ) {
            binding.btnBusiProceed.setEnabled(true);
        } else {
            binding.btnBusiProceed.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.etCityName) {

            cityBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            cityBottomSheet.show(getChildFragmentManager(),"city");//Checking
        }
        if (v == binding.btnBusiProceed) {
            fabloLoading.showProgress(getContext());
            gatherData();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(City.Item city) {
        if (city != null) {
            cityData = city;
            binding.etCityName.setText(city.getName());
            cityBottomSheet.dismiss();
        }
    }
    private void gatherData() {
        BusinessLeadModel model = new BusinessLeadModel();
        try {
            if (getContext() != null) {
                PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
                String version = pInfo.versionName;
                Log.e(TAG, "gatherData: "+version);
                model.setApp_version(version);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
            if (phone != null) {
                String trimPhone = phone.replace("+91", "");
                model.setPhone(trimPhone);
            }
        }
        String full_name = binding.etFullName.getText().toString();
        String businessName = binding.etBusiName.getText().toString();
        String pin = binding.etPincode.getText().toString();
        String address = binding.etaddress.getText().toString();
        int pin_code = Integer.parseInt(pin);
        model.setBusiness_name(businessName);
        model.setFull_name(full_name);
        model.setPincode(pin_code);
        model.setAddress(address);
        model.setCity_name(cityData.getName());
        model.setCity_id(cityData.getId());
        model.setToken(uid);

        createBusiness(model);
    }

    private void createBusiness(BusinessLeadModel model) {
        BusinessInterface businessInterface = RestClient.getRetrofitServiceInstance(getContext()).create(BusinessInterface.class);
        Call<CreateLeadRequest> call = businessInterface.createBusinessLead(model);
        call.enqueue(new Callback<CreateLeadRequest>() {
            @Override
            public void onResponse(Call<CreateLeadRequest> call, Response<CreateLeadRequest> response) {
                fabloLoading.hideProgress();
                Log.e(TAG, "onResponse: " + response.body());
                if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                    if (response.body() != null) {
                        Log.e(TAG, "http: " + response.message());
                        ((BusinessSetupActivity)getContext()).selectStep("contact");
//                        Intent intent = new Intent(getContext(), BusinessSetupActivity.class);
//                        intent.putExtra("step","contact");
//
//                        startActivity(intent);
                    }
                    else {
                        Log.e(TAG, "http: " + response.message());


                    }
                }else {
                    Log.e(TAG, "http: " + response.message());
                    Toast.makeText(getContext(), "Response Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CreateLeadRequest> call, Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException){
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GlobalError globalError) {
        if (globalError != null) {
            showError(globalError.getTitle(), globalError.getDescription());
        }
    }

    public void showError(String title, String description) {
        fabloAlert.showAlert(getContext(), Constant.ALERT_ERROR, false, title, description, "");
    }

    public void showSuccess(String title, String description) {
        fabloAlert.showAlert(getContext(), Constant.ALERT_SUCCESS, false, title, description, "");
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