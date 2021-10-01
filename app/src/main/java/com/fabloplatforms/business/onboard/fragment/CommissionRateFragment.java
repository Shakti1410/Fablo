package com.fabloplatforms.business.onboard.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fabloplatforms.business.databinding.FragmentCommissionRateBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.CommissionInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.model.Commission;
import com.fabloplatforms.business.onboard.model.CommissionRequest;
import com.fabloplatforms.business.onboard.model.CommissionResponse;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.LoginPref;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommissionRateFragment extends Fragment {
    private FragmentCommissionRateBinding binding;

    private boolean statusCommissionRate = false;
    private FabloLoading fabloLoading;
    private FabloAlert fabloAlert;
    private int rate;
    private Context context;




    private static final String TAG = "CommissionRateFragment";


    public CommissionRateFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentCommissionRateBinding.inflate(inflater,container,false);
       View view = binding.getRoot();
       context = getContext();
        initView();
       return  view;
    }

    private void initView() {
        fabloLoading = FabloLoading.getInstance();
        fabloAlert = FabloAlert.getInstance();
        fetchCommission();
        binding.ivBackCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.etselectCommission.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String panNumber = binding.etselectCommission.getText().toString().trim();
                if (!panNumber.isEmpty()) {
                    statusCommissionRate = true;
                } else {
                    statusCommissionRate = false;
                }
                checkValidation();
            }
        });
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatherData();
            }
        });
    }

    private void gatherData() {
        fabloLoading.showProgress(getContext());
        LoginPref loginPref = new LoginPref(context);
        String version = loginPref.getAppVersion();
        String token = loginPref.getToken();
        CommissionRequest request = new CommissionRequest();
        Commission commission = new Commission();
        commission.setRate(rate);
        request.setAppVersion(version);
        request.setToken(token);
        request.setCommission(commission);
        addCommission(request);
    }

    private void addCommission(CommissionRequest request) {
        if(request!=null)
        {
            CommissionInterface commissionInterface = RestClient.getRetrofitServiceInstance(getContext()).create(CommissionInterface.class);
            Call<ResponseBody> call = commissionInterface.addCommissionDetails(request);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    fabloLoading.hideProgress();
                    if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                        fabloLoading.hideProgress();
                        ((BusinessSetupActivity)getContext()).selectStep("Ekyc");
                    } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                        fabloLoading.hideProgress();
                        Toast.makeText(getContext(), "There is some issue while adding details.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    fabloLoading.hideProgress();
                    if (t instanceof NoConnectivityException){
                        GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                        EventBus.getDefault().post(globalError);
                    }
                }
            });

        }
    }

    private void checkValidation() {
        if (statusCommissionRate ) {
            binding.btnProceed.setEnabled(true);
        } else {
            binding.btnProceed.setEnabled(false);
        }
    }
    private void fetchCommission() {
        fabloLoading.showProgress(getContext());
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        CommissionInterface commissionInterface = RestClient.getRetrofitServiceInstance(getContext()).create(CommissionInterface.class);
        Call<CommissionResponse> call = commissionInterface.getCommission(token);//Token
        call.enqueue(new Callback<CommissionResponse>() {
            @Override
            public void onResponse(Call<CommissionResponse> call, Response<CommissionResponse> response) {
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {

                    if (response.body() != null) {
                        fabloLoading.hideProgress();
                        rate = response.body().getItems().getCommissionRate();
                        binding.etselectCommission.setText(String.valueOf(rate) + "%");

                    }

                } else {
                    fabloLoading.hideProgress();
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommissionResponse> call, Throwable t) {
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
}