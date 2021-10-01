package com.fabloplatforms.business.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.BottomSheetScheduleKycBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.KycInterface;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleKycBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    BottomSheetScheduleKycBinding binding;
    private Context context;
    private MaterialDatePicker.Builder builderDate;
    private MaterialDatePicker materialDatePicker;

    private MaterialTimePicker.Builder builderTime;
    private MaterialTimePicker materialTimePicker;

    private boolean statusDate = false;
    private boolean statusTime = false;

    private FabloAlert fabloAlert;
    private FabloLoading fabloLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetScheduleKycBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if (getContext() != null) {
            context = getContext();
        }
        if (getArguments() != null) {
            int status = getArguments().getInt("status");
            switchSchedulerType(status);
        }
        initView();
        return view;
    }

    private void initView() {
        fabloAlert = FabloAlert.getInstance();
        fabloLoading = FabloLoading.getInstance();

        initDatePicker();
        binding.etDate.setOnClickListener(this);
        binding.etTime.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        binding.etDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String date = binding.etDate.getText().toString().trim();
                if (date.isEmpty()) {
                    statusDate = false;
                } else {
                    statusDate = true;
                }
                formValidation();
            }
        });

        binding.etTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String time = binding.etTime.getText().toString().trim();
                if (time.isEmpty()) {
                    statusTime = false;
                } else {
                    statusTime = true;
                }
                formValidation();
            }
        });


    }

    private void switchSchedulerType(int status) {
        if (status == Constant.KYC_STATUS_NOT_SUBMIT) {
            binding.tvScheduleKycTitle.setText(getResources().getString(R.string.str_kyc_schedule_title));
            binding.tvScheduleKycDesc.setText(getResources().getString(R.string.str_kyc_schedule_desc));
        } else if (status == Constant.KYC_STATUS_REJECTED) {
            binding.tvScheduleKycTitle.setText(getResources().getString(R.string.str_re_kyc_schedule_title));
            binding.tvScheduleKycDesc.setText(getResources().getString(R.string.str_re_kyc_schedule_desc));
        }
    }

    private void initDatePicker() {
        builderDate = MaterialDatePicker.Builder.datePicker();
        builderDate.setTitleText("SELECT A DATE");
        builderDate.setSelection(MaterialDatePicker.todayInUtcMilliseconds());
        materialDatePicker = builderDate.build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long value = Long.parseLong(String.valueOf(selection));
                String dateString = DateFormat.format("dd-MM-yyyy", new Date(value)).toString();
                binding.etDate.setText(dateString);
                materialDatePicker.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.etDate) {
            materialDatePicker.show(getChildFragmentManager(), "date");
        }
        if (v == binding.etTime) {
            builderTime = new MaterialTimePicker.Builder();
            builderTime.setTitleText("SELECT A TIME");
            builderTime.setTimeFormat(TimeFormat.CLOCK_12H);
            materialTimePicker = builderTime.build();
            materialTimePicker.show(getChildFragmentManager(), "time");
            materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    java.text.DateFormat df = new SimpleDateFormat("hh:mm a");
                    Date date = new Date(0, 0, 0, materialTimePicker.getHour(), materialTimePicker.getMinute());
                    binding.etTime.setText(df.format(date));
                    materialTimePicker.dismiss();
                }
            });
        }
        if (v == binding.btnSubmit) {
            fabloLoading.showProgress(context);
            gatherData();
        }
    }

    private void formValidation() {
        if (statusDate && statusTime) {
            binding.btnSubmit.setEnabled(true);
        } else {
            binding.btnSubmit.setEnabled(false);
        }
    }

    private void gatherData() {
        String date = binding.etDate.getText().toString().trim();
        String time = binding.etTime.getText().toString().trim();

        SharedPreferences preferences = context.getSharedPreferences("temp", Context.MODE_PRIVATE);
        String business_id = preferences.getString("id", "none");

        if (!date.isEmpty() && !time.isEmpty()) {
            KycRequest kycRequest = new KycRequest();
            kycRequest.setAppVersion("1.0");
            kycRequest.setBusinessId(business_id);
            kycRequest.setScheduleDate(date);
            kycRequest.setScheduleTime(time);

            scheduleKycVisit(kycRequest);
        } else {
            fabloLoading.hideProgress();
        }

    }

    private void scheduleKycVisit(KycRequest kycRequest) {
        KycInterface kycInterface = RestClient.getRetrofitServiceInstance(context).create(KycInterface.class);
        Call<ResponseBody> call = kycInterface.scheduleKyc(kycRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                fabloLoading.hideProgress();
                if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                    dismiss();
                } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                    showError("Something went wrong", "We cannot process your request right now, please try again after some time.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException) {
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                } else {
                    showError("Something went wrong", "We cannot process your request right now, please try again after some time.");
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
