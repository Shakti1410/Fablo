package com.fabloplatforms.business.onboard.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentBankAccountBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BankInterface;
import com.fabloplatforms.business.interfaces.IfscInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.bottomsheet.SelectDocTypeBottomSheet;
import com.fabloplatforms.business.onboard.model.BankAccountRequest;
import com.fabloplatforms.business.onboard.model.BankDocType;
import com.fabloplatforms.business.onboard.model.Document;
import com.fabloplatforms.business.onboard.model.Ifsc;
import com.fabloplatforms.business.retrofit.RazorpayIfscClient;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.store.utils.StoreConstant;
import com.fabloplatforms.business.store.utils.StoreGlobalError;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.LoginPref;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankAccountFragment extends Fragment implements View.OnClickListener {

    private FragmentBankAccountBinding binding;
    private SelectDocTypeBottomSheet docTypeBottomSheet;

    private FabloLoading fabloLoading;
    private FabloAlert fabloAlert;
//confirm account number
    private Uri imageUri;
    private String myUrl = "",docext="";
    private StorageReference storageReference;
    private StorageTask uploadtask;
    private boolean statusBeneficiaryName = false;
    private boolean statusAccountNumber = false;
    private boolean myUrlStatus = false;
    private boolean statusRepeatAccountNumber = false;
    private boolean statusIfscCode = false;
    private boolean statusBankName = false;
    private Context context;

    private BankDocType bankDocType;

    private static final String TAG = "BankAccountFragment";

    public BankAccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBankAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {
        fabloLoading = FabloLoading.getInstance();
        fabloAlert = FabloAlert.getInstance();
        docTypeBottomSheet = new SelectDocTypeBottomSheet();
        binding.etBankDocs.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference().child("fablo_business").child("merchant");
        binding.etBeneficiaryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String beneficiaryName = binding.etBeneficiaryName.getText().toString().trim();
                if (!beneficiaryName.isEmpty()) {
                    statusBeneficiaryName = true;
                } else {
                    statusBeneficiaryName = false;
                }
                checkValidation();
            }
        });

        binding.etAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String accountNumber = binding.etAccountNumber.getText().toString().trim();
                String repeatAccountNumber = binding.etReAccountNumber.getText().toString().trim();
                if (!accountNumber.isEmpty()) {
                    if (!repeatAccountNumber.isEmpty()) {
                        if (accountNumber.equals(repeatAccountNumber)) {
                            statusAccountNumber = true;
                            binding.tiAccountNumber.setErrorEnabled(false);
                            statusRepeatAccountNumber = true;
                            binding.tiReAccountNumber.setErrorEnabled(false);
                        } else {
                            statusAccountNumber = false;
                            binding.tiAccountNumber.setErrorEnabled(true);
                            binding.tiAccountNumber.setError("Account number did not match");
                        }
                    }
                    statusAccountNumber = true;
                } else {
                    statusAccountNumber = false;
                }
                checkValidation();
            }
        });

        binding.etReAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String accountNumber = binding.etAccountNumber.getText().toString().trim();
                String repeatAccountNumber = binding.etReAccountNumber.getText().toString().trim();
                if (!repeatAccountNumber.isEmpty()) {
                    if (repeatAccountNumber.equals(accountNumber)) {
                        statusRepeatAccountNumber = true;
                        binding.tiReAccountNumber.setErrorEnabled(false);
                        statusAccountNumber = true;
                        binding.tiAccountNumber.setErrorEnabled(false);
                    } else {
                        statusRepeatAccountNumber = false;
                        binding.tiReAccountNumber.setErrorEnabled(true);
                        binding.tiReAccountNumber.setError("Account number did not match");
                    }
                } else {
                    statusRepeatAccountNumber = false;
                }
                checkValidation();
            }
        });

        binding.etIfscCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                if (!input.equals(input.toUpperCase())) {
                    input = input.toUpperCase();
                    binding.etIfscCode.setText(input);
                    binding.etIfscCode.setSelection(binding.etIfscCode.length());
                    statusIfscCode = true;
                }
                if (input.length() > 10) {
                    checkIfsc(input);
                } else {
                    statusIfscCode = false;
                    binding.etBankBranch.setText("");
                    binding.etBankName.setText("");
                }
                checkValidation();
            }
        });

        binding.etBankName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String bankName = binding.etBankName.getText().toString().trim();
                if (!bankName.isEmpty()) {
                    statusBankName = true;
                } else {
                    statusBankName = false;
                }
                checkValidation();
            }
        });

        binding.btnProceed.setOnClickListener(this);
        binding.btnUpload.setOnClickListener(this);
        binding.ivBackBank.setOnClickListener(this);

    }

    private void checkIfsc(String ifsc) {
        IfscInterface ifscInterface = RazorpayIfscClient.getRetrofitServiceInstance(getContext()).create(IfscInterface.class);
        Call<Ifsc> call = ifscInterface.checkIfsc(ifsc);
        call.enqueue(new Callback<Ifsc>() {
            @Override
            public void onResponse(@NotNull Call<Ifsc> call, @NotNull Response<Ifsc> response) {
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    if (response.body() != null) {
                        binding.etBankName.setText(response.body().getBank());
                        binding.etBankBranch.setText(response.body().getBranch());
                        if (response.body().getBank().length() > 0) {
                            statusIfscCode = true;
                        } else {
                            statusIfscCode = false;
                        }
                        binding.tiIfscCode.setErrorEnabled(false);
                    }
                } else if (response.code() == Constant.HTTP_RESOURCE_NOT_FOUND) {
                        binding.tiIfscCode.setErrorEnabled(true);
                        binding.tiIfscCode.setError("IFSC code not found");
                        statusIfscCode = false;
                }

                checkValidation();

            }

            @Override
            public void onFailure(@NotNull Call<Ifsc> call, @NotNull Throwable t) {
                fabloLoading.hideProgress();
                if (t instanceof NoConnectivityException) {
                    StoreGlobalError storeGlobalError = new StoreGlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", StoreConstant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(storeGlobalError);
                }
            }
        });
    }

    private void gatherData() {
        fabloLoading.showProgress(getContext());
        LoginPref loginPref = new LoginPref(context);
        String version = loginPref.getAppVersion();
        String token = loginPref.getToken();
//        GlobalPref globalPref = new GlobalPref(getContext());
//        SharedPreferences preferences = getContext().getSharedPreferences("temp", Context.MODE_PRIVATE);
//        String business_id = preferences.getString("id", "none");
        String type = bankDocType.getType().trim();
        BankAccountRequest bankAccountRequest = new BankAccountRequest();
        Document document = new Document();
        document.setFileType(type.toLowerCase());
        document.setFile(myUrl);

        String beneficiaryName = binding.etBeneficiaryName.getText().toString().trim();
        String accountNumber = binding.etReAccountNumber.getText().toString().trim();
        String ifscCode = binding.etIfscCode.getText().toString().trim();
        String bankName = binding.etBankName.getText().toString().trim();
        String branchName = binding.etBankBranch.getText().toString().trim();

        bankAccountRequest.setToken(token);
        bankAccountRequest.setBeneficiaryName(beneficiaryName);
        bankAccountRequest.setAccountNumber(accountNumber);
        bankAccountRequest.setIfscCode(ifscCode);
        bankAccountRequest.setBankName(bankName);
        bankAccountRequest.setBranchName(branchName);
        bankAccountRequest.setDocument(document);
        bankAccountRequest.setAppVersion(version); //globalPref.getAppVersion()

        addBankAccount(bankAccountRequest);
    }

    private void addBankAccount(BankAccountRequest bankAccountRequest) {
        if (bankAccountRequest != null) {
            BankInterface bankInterface = RestClient.getRetrofitServiceInstance(getContext()).create(BankInterface.class);
            Call<ResponseBody> call = bankInterface.createBank(bankAccountRequest);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    fabloLoading.hideProgress();
                    if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                        fabloLoading.hideProgress();
                        ((BusinessSetupActivity)getContext()).selectStep("tax");
                    } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                        fabloLoading.hideProgress();
                        Toast.makeText(getContext(), "There is some issue while adding bank account details.", Toast.LENGTH_SHORT).show();
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
        Log.e(TAG, "checkValidation: "+statusBeneficiaryName+" "+statusAccountNumber+" "+statusRepeatAccountNumber+ statusIfscCode+" "+statusBankName);
        if (statusBeneficiaryName && statusAccountNumber && statusRepeatAccountNumber && statusIfscCode && statusBankName && myUrlStatus) {
            binding.btnProceed.setEnabled(true);
        } else {
            binding.btnProceed.setEnabled(false);
        }
    }

    private void networkMsg() {
        try {
            fabloAlert.showAlert(context,1,false,"Network Alert","Internet not available, Cross check your internet connectivity and try again",TAG);

        } catch (Exception e) {
            Toast.makeText(context, "Error :" + e, Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){

            return false;
        }
        return true;
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
public void onMessageEvent(BankDocType docType) {
    if (docType != null) {
        bankDocType = docType;
        binding.etBankDocs.setText(docType.getType());
        docTypeBottomSheet.dismiss();
        binding.btnUpload.setEnabled(true);
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
    @Override
    public void onClick(View v) {
        if (v == binding.btnProceed) {
            gatherData();
        }
        if (v == binding.etBankDocs) {
            Bundle args = new Bundle();
            args.putString("docType", "BANK");
            docTypeBottomSheet.setArguments(args);
            docTypeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            docTypeBottomSheet.show(getChildFragmentManager(), "docType");
            myUrlStatus = false;
            binding.docStatus.setText("No file Chosen");
            checkValidation();
        }
        if (v == binding.btnUpload) {
            if(isOnline()) {
                UploadDoc();
            }
            else {
                networkMsg();
            }
        }
        if (v == binding.ivBackBank) {
            getActivity().onBackPressed();
        }


    }

    private void UploadDoc() {
        if(bankDocType.getType().equals("Document"))
        {
            docext = "bankdocument.pdf";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("application/pdf"); //
            someActivityResultLauncher.launch(galleryIntent);

        }
        else if(bankDocType.getType().equals("Image"))
        {
            docext ="bankdocument.jpg";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            someActivityResultLauncher.launch(galleryIntent);
        }

      //  startActivityForResult(galleryIntent, 1);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        // There are no request codes
                        Uri uri  = result.getData().getData();
                        String uriString = uri.toString();
                        File myFile = new File(uriString);
                        String path = myFile.getAbsolutePath();
                        String displayName = null;
                        displayName = myFile.getName();
                        if (uriString.startsWith("content://")) {
                            Cursor cursor = null;
                            try {
                                cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            } finally {
                                cursor.close();
                            }
                        } else if (uriString.startsWith("file://")) {
                            displayName = myFile.getName();
                        }
                        fabloLoading.showProgress(getContext());
                        binding.docStatus.setText(displayName);
                        binding.btnUpload.setText("Reset");

                        firebaseUpload(uri);
                       // Toast.makeText(getContext(), "File Name"+ path, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void firebaseUpload(Uri uri) {
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        String t1 = encryptThisString(token);
        if(t1!=null)
        {
            final StorageReference fileref = storageReference.child(t1).child(docext);
            uploadtask = fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myUrl = uri.toString();
                            myUrlStatus = true;
                            fabloLoading.hideProgress();
                          //  Toast.makeText(getContext(), "MyUrl "+myUrl, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFireBaseSuccess: "+ myUrl);
                            checkValidation();
                        }
                    });
                }
            });
        }



    }
    public static String encryptThisString(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
//    public static String toSHA1(byte[] convertme) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("SHA-1");
//        return Base64.getEncoder().encodeToString(md.digest(convertme));
//    }
}