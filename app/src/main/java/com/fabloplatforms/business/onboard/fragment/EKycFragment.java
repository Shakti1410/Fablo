package com.fabloplatforms.business.onboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fabloplatforms.business.GatewayActivity;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentEKycBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BusinessInterface;
import com.fabloplatforms.business.interfaces.EkycInterface;
import com.fabloplatforms.business.onboard.model.AadhaarBackDocType;
import com.fabloplatforms.business.onboard.model.AadhaarFrontDocType;
import com.fabloplatforms.business.onboard.model.CreateBusinessResponse;
import com.fabloplatforms.business.onboard.model.EkycResponse;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.LoginPref;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EKycFragment extends Fragment implements View.OnClickListener {
    private FragmentEKycBinding binding;
    private FabloLoading fabloLoading;
    private FabloAlert fabloAlert;
    private boolean statusMOU = false;
    private boolean statusGST = false;
    private boolean statusFUND = false;
    private Context context;
    private String mouURL="",gstURL="",fundURL="";




    private static final String TAG = "EKycFragment";

    public EKycFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEKycBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;

    }

    private void initView() {
        fabloLoading = FabloLoading.getInstance();
        fabloLoading.showProgress(context);
        fabloAlert = FabloAlert.getInstance();
        binding.btnviewMou.setOnClickListener(this);
        binding.btnviewFund.setOnClickListener(this);
        binding.btnviewGst.setOnClickListener(this);
        binding.btnProceed.setOnClickListener(this);
        binding.btnSubmitOtp.setOnClickListener(this);
        binding.ivBackEkyc.setOnClickListener(this);
        getPDF();

    }

    private void getPDF() {
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        EkycInterface ekycInterface = RestClient.getRetrofitServiceInstance(getContext()).create(EkycInterface.class);
        Call<EkycResponse> call = ekycInterface.getPdf(token);
        call.enqueue(new Callback<EkycResponse>() {
            @Override
            public void onResponse(Call<EkycResponse> call, Response<EkycResponse> response) {
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {

                    if (response.body() != null) {
                        fabloLoading.hideProgress();
                        mouURL = response.body().getItems().getLoc();

                    }

                } else {
                    fabloLoading.hideProgress();
                    Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EkycResponse> call, Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException){
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnviewMou) {
            downloadAndOpenPDF(context, mouURL);
        }
        if (v == binding.btnviewFund) {
              downloadFundPdf(context,mouURL);
        }
        if (v == binding.btnviewGst) {
            downloadGstPdf(context,mouURL);
        }
        if (v == binding.btnProceed) {
            Intent browserIntent = new Intent(getActivity(), GatewayActivity.class);
            startActivity(browserIntent);

        }
        if (v == binding.btnSubmitOtp) {

        }
        if (v == binding.ivBackEkyc) {
           getActivity().onBackPressed();

        }
    }

    private void downloadGstPdf(Context context, String mouURL) {
        fabloLoading.showProgress(context);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mouURL));
        startActivity(browserIntent);
        binding.checkBoxGst.setChecked(true);
        statusGST = true;
        checkValidation();
        fabloLoading.hideProgress();
    }

    private void downloadFundPdf(Context context, String mouURL) {
        fabloLoading.showProgress(context);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mouURL));
        startActivity(browserIntent);
        binding.checkBoxFund.setChecked(true);
        statusFUND = true;
        checkValidation();
        fabloLoading.hideProgress();
    }

    private void downloadAndOpenPDF(Context context, String mouURL) {
        fabloLoading.showProgress(context);
        if (mouURL != null && !mouURL.isEmpty())
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mouURL));

            startActivity(browserIntent);
            binding.checkBoxMOU.setChecked(true);
            statusMOU = true;
            checkValidation();
            fabloLoading.hideProgress();
        }
        else {
            fabloLoading.hideProgress();
        }

    }
    private void checkValidation() {
        if (statusMOU && statusFUND && statusGST ) {
            binding.btnProceed.setEnabled(true);
        } else {
            binding.btnProceed.setEnabled(false);
        }
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