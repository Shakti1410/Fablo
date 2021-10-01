package com.fabloplatforms.business.onboard.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentContactPersonBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.ContactPersonInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.bottomsheet.ContactPersonTypeBottomSheet;
import com.fabloplatforms.business.onboard.model.BusinessType;
import com.fabloplatforms.business.onboard.model.ContactPersonRequest;
import com.fabloplatforms.business.onboard.model.ContactPersonType;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.LoginPref;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactPersonFragment extends Fragment implements View.OnClickListener {

    FragmentContactPersonBinding binding;

    private boolean statusName = false;
    private boolean statusType = false;
    private boolean statusEmail = false;
    private boolean statusInvoiceEmail = false;
    ContactPersonType contactPersonData;
    private Context context;

    ContactPersonTypeBottomSheet contactPersonBottomSheet;
    private FabloAlert fabloAlert;
    private FabloLoading fabloLoading;



    public ContactPersonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactPersonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {
        fabloAlert = FabloAlert.getInstance();
        fabloLoading = FabloLoading.getInstance();
        contactPersonBottomSheet = new ContactPersonTypeBottomSheet();
        binding.btnProceed.setOnClickListener(this);
        binding.etContactType.setOnClickListener(this);
        binding.ivBackContact.setOnClickListener(this);

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String firstName = binding.etName.getText().toString().trim();
                if (firstName.isEmpty()) {
                    statusName = false;
                } else {
                    statusName = true;
                }
                checkValidation();
            }
        });



        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = binding.etEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    statusEmail = true;
                    binding.tiEmail.setErrorEnabled(false);
                } else {
                    statusEmail = false;
                    binding.tiEmail.setErrorEnabled(true);
                    binding.tiEmail.setError("Please enter valid email");
                }
                checkValidation();
            }
        });
        binding.etInvoiceEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = binding.etInvoiceEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    statusInvoiceEmail = true;
                    binding.tiInvoiceEmail.setErrorEnabled(false);
                } else {
                    statusInvoiceEmail = false;
                    binding.tiInvoiceEmail.setErrorEnabled(true);
                    binding.tiInvoiceEmail.setError("Please enter valid email");
                }
                checkValidation();
            }
        });

        binding.etContactType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String type = binding.etContactType.getText().toString().trim();
                if (!type.isEmpty()) {
                    statusType = true;
                } else {
                    statusType = false;
                }
//                if(type.equals("Owner"))
//                {
//                    binding.etName.setHint("Enter Owner Name");
//                }
//                else
//                {
//                    binding.etName.setHint("Enter Manager Name");
//                }
                checkValidation();
            }
        });

    }

    private void checkValidation() {
        if (statusType && statusName && statusEmail && statusInvoiceEmail ) {
            binding.btnProceed.setEnabled(true);
        } else {
            binding.btnProceed.setEnabled(false);
        }
    }

    private void gatherData() {
        ContactPersonRequest contactPersonRequest = new ContactPersonRequest();
        LoginPref loginPref = new LoginPref(context);
        String version = loginPref.getAppVersion();
        String token = loginPref.getToken();
        if (getContext() != null) {

            String name = binding.etName.getText().toString().trim();
            String type = binding.etContactType.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String invoiceEmail = binding.etInvoiceEmail.getText().toString().trim();

            if (!token.isEmpty() && !name.isEmpty() && !type.isEmpty() && !email.isEmpty() && !invoiceEmail.isEmpty()) {

                contactPersonRequest.setToken(token);
                contactPersonRequest.setName(name);
                contactPersonRequest.setType(type.toLowerCase(Locale.ROOT));
                contactPersonRequest.setEmail(email);
                contactPersonRequest.setInvoicingEmail(invoiceEmail);
                contactPersonRequest.setAppVersion(version);


                fabloLoading.showProgress(getContext());
                addContactPerson(contactPersonRequest);

            }
        }
    }

    private void addContactPerson(ContactPersonRequest contactPersonRequest) {
        ContactPersonInterface contactPersonInterface = RestClient.getRetrofitServiceInstance(getContext()).create(ContactPersonInterface.class);
        if (contactPersonRequest != null) {
            Call<ResponseBody> call = contactPersonInterface.addContactPerson(contactPersonRequest);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        fabloLoading.hideProgress();
                    if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                        if (getContext() != null) {
                            if (response.body() != null) {
                                ((BusinessSetupActivity)getContext()).selectStep("bank");
                            }
                        }
                    } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                        showError("Incorrect details", "Seems this business already have contact person added.");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    fabloLoading.hideProgress();
                    if (t instanceof NoConnectivityException){
                        GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                        EventBus.getDefault().post(globalError);
                    }
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnProceed) {
            gatherData();
        }
        if (v == binding.etContactType) {
            contactPersonBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            contactPersonBottomSheet.show(getChildFragmentManager(), "type");
        }
        if(v== binding.ivBackContact)
        {
            getActivity().onBackPressed();
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

    public void showSuccess(String title, String description) {
        fabloAlert.showAlert(getContext(), Constant.ALERT_SUCCESS, false, title, description, "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ContactPersonType type) {
        if (type != null) {
            contactPersonData = type;
            binding.etContactType.setText(type.getType());
            contactPersonBottomSheet.dismiss();
        }
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