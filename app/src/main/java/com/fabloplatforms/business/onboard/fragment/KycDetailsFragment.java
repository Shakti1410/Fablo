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
import com.fabloplatforms.business.common.Business;
import com.fabloplatforms.business.databinding.FragmentKycDetailsBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.FssaiInterface;
import com.fabloplatforms.business.interfaces.KycInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.bottomsheet.SelectDocTypeBottomSheet;
import com.fabloplatforms.business.onboard.model.AadhaarBackDocType;
import com.fabloplatforms.business.onboard.model.AadhaarFrontDocType;
import com.fabloplatforms.business.onboard.model.AadharBack;
import com.fabloplatforms.business.onboard.model.AadharFront;
import com.fabloplatforms.business.onboard.model.GstDocType;
import com.fabloplatforms.business.onboard.model.KycDetailsRequest;
import com.fabloplatforms.business.onboard.model.KycResponse;
import com.fabloplatforms.business.onboard.model.PanDocType;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.BusinessPref;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.LoginPref;
import com.fabloplatforms.business.utils.TempModel;
import com.fabloplatforms.business.utils.TempPref;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KycDetailsFragment extends Fragment implements View.OnClickListener {
    private FragmentKycDetailsBinding binding;

    private SelectDocTypeBottomSheet docTypeBottomSheet;

    private FabloLoading fabloLoading;
    private FabloAlert fabloAlert;
    //confirm account number
    private Uri imageUri;
    private String myAfrontUrl = "", myAbackUrl = "", frontdocext = "", backdocext = "";
    private StorageReference storageReference;
    private StorageTask uploadtask;
    private boolean statusAadhaarNumber = false;
    private boolean statusFrontDoc = false;
    private boolean statusBackDoc = false;
    private Context context;


    private AadhaarFrontDocType frontDocType;
    private AadhaarBackDocType backDocType;

    private static final String TAG = "TaxDetailsFragment";

    public KycDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentKycDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {
        fabloLoading = FabloLoading.getInstance();
        fabloAlert = FabloAlert.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("fablo_business").child("merchant");
        docTypeBottomSheet = new SelectDocTypeBottomSheet();
        binding.btnAdharFrontUpload.setOnClickListener(this);
        binding.btnAdharBackUpload.setOnClickListener(this);
        binding.btnProceed.setOnClickListener(this);
        binding.ivBackKyc.setOnClickListener(this);
        binding.etAdharFrontDocType.setOnClickListener(this);
        binding.etAdharBackDocType.setOnClickListener(this);

        binding.etAadhaarNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String aNumber = binding.etAadhaarNumber.getText().toString().trim();
                if (aNumber.length() > 11) {
                    statusAadhaarNumber = true;
                } else {
                    statusAadhaarNumber = false;
                }
                checkValidation();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.etAdharFrontDocType) {
            Bundle args = new Bundle();
            args.putString("docType", "AADHAARFRONT");
            docTypeBottomSheet.setArguments(args);
            docTypeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            docTypeBottomSheet.show(getChildFragmentManager(), "docType");
            statusFrontDoc = false;
            binding.tvadharFrontDocStatus.setText("No file Chosen");
            checkValidation();
        }
        if (v == binding.etAdharBackDocType) {
            Bundle args = new Bundle();
            args.putString("docType", "AADHAARBACK");
            docTypeBottomSheet.setArguments(args);
            docTypeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            docTypeBottomSheet.show(getChildFragmentManager(), "docType");
            statusBackDoc = false;
            binding.tvadharBackDocStatus.setText("No file Chosen");
            checkValidation();
        }
        if (v == binding.btnProceed) {
            gatherData();
        }
        if (v == binding.btnAdharFrontUpload) {
            if (isOnline()) {
                UploadFrontDoc();
            } else {
                networkMsg();
            }

        }
        if (v == binding.btnAdharBackUpload) {
            if (isOnline()) {
                UploadBackDoc();
            } else {
                networkMsg();
            }

        }
        if (v == binding.ivBackKyc) {
            getActivity().onBackPressed();
        }
    }

    private void UploadFrontDoc() {
        if (frontDocType.getType().equals("Document")) {
            frontdocext = "aadhaarfrontdocument.pdf";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("application/pdf"); //
            frontActivityResultLauncher.launch(galleryIntent);

        } else if (frontDocType.getType().equals("Image")) {
            frontdocext = "aadhaarfrontdocument.jpg";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            frontActivityResultLauncher.launch(galleryIntent);
        }

        //  startActivityForResult(galleryIntent, 1);
    }

    ActivityResultLauncher<Intent> frontActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        // There are no request codes
                        Uri uri = result.getData().getData();
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
                        binding.tvadharFrontDocStatus.setText(displayName);
                        binding.btnAdharFrontUpload.setText("Reset");
                        firebaseFrontUpload(uri);
                        // Toast.makeText(getContext(), "File Name"+ path, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void firebaseFrontUpload(Uri uri) {
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        String t1 = encryptThisString(token);
        if (t1 != null) {
            final StorageReference fileref = storageReference.child(t1).child(frontdocext);
            uploadtask = fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myAfrontUrl = uri.toString();
                            statusFrontDoc = true;
                            fabloLoading.hideProgress();
                            //  Toast.makeText(getContext(), "MyUrl "+myUrl, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFireBaseSuccess: " + myAfrontUrl);
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

    private void UploadBackDoc() {
        if (backDocType.getType().equals("Document")) {
            backdocext = "aadhaarbackdocument.pdf";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("application/pdf"); //
            backActivityResultLauncher.launch(galleryIntent);

        } else if (backDocType.getType().equals("Image")) {
            backdocext = "aadhaarbackdocument.jpg";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            backActivityResultLauncher.launch(galleryIntent);
        }
    }

    ActivityResultLauncher<Intent> backActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        // There are no request codes
                        Uri uri = result.getData().getData();
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
                        binding.tvadharBackDocStatus.setText(displayName);
                        binding.btnAdharBackUpload.setText("Reset");
                        firebaseBackUpload(uri);
                        // Toast.makeText(getContext(), "File Name"+ path, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void firebaseBackUpload(Uri uri) {
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        String t1 = encryptThisString(token);
        if (t1 != null) {
            final StorageReference fileref = storageReference.child(t1).child(backdocext);
            uploadtask = fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myAbackUrl = uri.toString();
                            statusBackDoc = true;
                            fabloLoading.hideProgress();
                            //  Toast.makeText(getContext(), "MyUrl "+myUrl, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFireBaseSuccess: " + myAbackUrl);
                            checkValidation();
                        }
                    });
                }
            });
        }


    }

    private void networkMsg() {
        try {
            fabloAlert.showAlert(context, 1, false, "Network Alert", "Internet not available, Cross check your internet connectivity and try again", TAG);

        } catch (Exception e) {
            Toast.makeText(context, "Error :" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {

            return false;
        }
        return true;
    }

    private void gatherData() {
        fabloLoading.showProgress(getContext());
        LoginPref loginPref = new LoginPref(context);
        String version = loginPref.getAppVersion();
        String token = loginPref.getToken();
        String aadhaarNo = binding.etAadhaarNumber.getText().toString().trim();
        String frontType = frontDocType.getType();
        String backType = backDocType.getType();
        KycDetailsRequest request = new KycDetailsRequest();
        AadharFront aadharFront = new AadharFront();
        AadharBack aadharBack = new AadharBack();
        aadharFront.setFileType(frontType.toLowerCase());
        aadharFront.setFile(myAfrontUrl);
        aadharBack.setFileType(backType.toLowerCase());
        aadharBack.setFile(myAbackUrl);
        request.setToken(token);
        request.setAppVersion(version);
        request.setAadharBack(aadharBack);
        request.setAadharFront(aadharFront);
        request.setAadharNumber(aadhaarNo);

        addDetails(request);

    }

    private void addDetails(KycDetailsRequest request) {
        if (request != null) {
            KycInterface kycInterface = RestClient.getRetrofitServiceInstance(getContext()).create(KycInterface.class);

            Call<ResponseBody> call = kycInterface.addKycDetails(request);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    fabloLoading.hideProgress();
                    if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                        fabloLoading.hideProgress();
                        ((BusinessSetupActivity) getContext()).selectStep("commission");
                    } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                        fabloLoading.hideProgress();
                        Toast.makeText(getContext(), "There is some issue while adding Kyc details.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    fabloLoading.hideProgress();
                    if (t instanceof NoConnectivityException) {
                        GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                        EventBus.getDefault().post(globalError);
                    }
                }
            });
        }
    }

    private void checkValidation() {
        if (statusAadhaarNumber && statusFrontDoc && statusBackDoc) {
            binding.btnProceed.setEnabled(true);
        } else {
            binding.btnProceed.setEnabled(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AadhaarFrontDocType docType) {
        if (docType != null) {
            frontDocType = docType;
            binding.etAdharFrontDocType.setText(docType.getType());
            docTypeBottomSheet.dismiss();
            binding.btnAdharFrontUpload.setEnabled(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AadhaarBackDocType docType) {
        if (docType != null) {
            backDocType = docType;
            binding.etAdharBackDocType.setText(docType.getType());
            docTypeBottomSheet.dismiss();
            binding.btnAdharBackUpload.setEnabled(true);
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