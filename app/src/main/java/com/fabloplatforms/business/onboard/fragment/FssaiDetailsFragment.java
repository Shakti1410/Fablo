package com.fabloplatforms.business.onboard.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentFssaiDetailsBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.FssaiInterface;
import com.fabloplatforms.business.interfaces.TaxInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.bottomsheet.SelectDocTypeBottomSheet;
import com.fabloplatforms.business.onboard.model.Acknowledgement;
import com.fabloplatforms.business.onboard.model.FssaiDetailsRequest;
import com.fabloplatforms.business.onboard.model.FssaiDocType;
import com.fabloplatforms.business.onboard.model.GstDocType;
import com.fabloplatforms.business.onboard.model.License;
import com.fabloplatforms.business.onboard.model.PanDocType;
import com.fabloplatforms.business.retrofit.RestClient;
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

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FssaiDetailsFragment extends Fragment implements View.OnClickListener{
    private FragmentFssaiDetailsBinding binding;
    private SelectDocTypeBottomSheet docTypeBottomSheet;

    private FabloLoading fabloLoading;
    private FabloAlert fabloAlert;
    //confirm account number
    private Uri imageUri;
    private String myFssaiUrl = "",fssaidocext="";
    private StorageReference storageReference;
    private StorageTask uploadtask;
    private boolean statusFssaiRegNumber = false;
    private boolean statusFssaiAckNumber= false;
    private boolean statusFssaiDoc = false;
    private boolean statusFssaiDate = false;
    private boolean fssaiCheck= true;
    private Context context;


    private FssaiDocType fssaiDocType;

    private static final String TAG = "TaxDetailsFragment";

    public FssaiDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFssaiDetailsBinding.inflate(inflater,container,false);
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
        binding.btnFssaiUpload.setOnClickListener(this);
        binding.btnProceed.setOnClickListener(this);
        binding.etFssaiDocType.setOnClickListener(this);
        binding.etFssaiDate.setOnClickListener(this);
        binding.etFssaiDate.setOnClickListener(this);
        binding.ivBackFssai.setOnClickListener(this);
        binding.etFssaiNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String fssaiNumber = binding.etFssaiNumber.getText().toString().trim();
                if (!fssaiNumber.isEmpty()) {
                    statusFssaiRegNumber = true;
                } else {
                    statusFssaiRegNumber = false;
                }
                checkValidation();
            }
        });

        binding.etFssaiAckNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ackNumber = binding.etFssaiAckNumber.getText().toString().trim();
                if (!ackNumber.isEmpty()) {
                    statusFssaiAckNumber = true;
                } else {
                    statusFssaiAckNumber = false;
                }
                checkValidation();
            }
        });
        binding.etFssaiDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String date = binding.etFssaiDate.getText().toString().trim();
                if (!date.isEmpty()) {
                    statusFssaiDate = true;
                } else {
                    statusFssaiDate = false;
                }
                checkValidation();
            }
        });

        binding.radioGroupFssai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkRadio(checkedId);

            }
        });
    }

    private void checkRadio(int checkedId) {
        if(checkedId == binding.fssaiRadioYes.getId())
        {
            binding.layoutFssaiYes.setVisibility(View.VISIBLE);
            binding.layoutFssaiNo.setVisibility(View.GONE);
            binding.etFssaiNumber.setText("");
            binding.etFssaiDocType.setText("");
            binding.btnFssaiUpload.setEnabled(false);
            binding.tvfssaiDocStatus.setText("No file Chosen");
            statusFssaiDoc = false;
            myFssaiUrl ="";
            statusFssaiRegNumber = false;
//            statusFssaiRegNumber =false;
//            statusFssaiAckNumber = false;
            fssaiCheck = true;
            checkValidation();


        }
        else if(checkedId == binding.fssaiRadioNo.getId())
        {
            binding.layoutFssaiYes.setVisibility(View.GONE);
            binding.layoutFssaiNo.setVisibility(View.VISIBLE);
            binding.etFssaiAckNumber.setText("");
            binding.etFssaiDate.setText("");

            myFssaiUrl="";
//            statusFssaiRegNumber =false;
//            statusFssaiAckNumber = false;
            fssaiCheck = false;
            checkValidation();
        }
    }
    private void checkValidation() {
        Log.e(TAG, "checkValidation: "+statusFssaiRegNumber+" "+statusFssaiAckNumber+" "+statusFssaiDoc+""+ statusFssaiDate);
        if(fssaiCheck)
        {
            if (statusFssaiRegNumber && statusFssaiDoc )
            {
                binding.btnProceed.setEnabled(true);
            }
            else {
                binding.btnProceed.setEnabled(false);
            }

        }
        else
        {
            if (statusFssaiAckNumber && statusFssaiDate) {

                binding.btnProceed.setEnabled(true);
            } else {
                binding.btnProceed.setEnabled(false);
            }
        }

    }
    @Override
    public void onClick(View v) {
        if (v == binding.etFssaiDocType) {
            Bundle args = new Bundle();
            args.putString("docType", "FSSAI");
            docTypeBottomSheet.setArguments(args);
            docTypeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            docTypeBottomSheet.show(getChildFragmentManager(), "docType");
            statusFssaiDoc = false;
            binding.tvfssaiDocStatus.setText("No file Chosen");
            checkValidation();
        }
        if (v == binding.btnFssaiUpload) {
            if(isOnline())
            {
                UploadFssaiDoc();
            }
            else {
                networkMsg();
            }

        }
        if (v == binding.etFssaiDate) {
           selectDate();
        }
        if (v == binding.btnProceed) {
            gatherData();
        }
        if(v == binding.ivBackFssai)
        {
            getActivity().onBackPressed();
        }
    }

    private void gatherData() {
        fabloLoading.showProgress(getContext());
        LoginPref loginPref = new LoginPref(context);
        String version = loginPref.getAppVersion();
        String token = loginPref.getToken();
        String fssaiNo = "";
        String doctype = "";
        String fssaiAck = "";
        String date = "";
        if(fssaiCheck)
        {
            fssaiNo = binding.etFssaiNumber.getText().toString().trim();
            doctype = fssaiDocType.getType().trim().toLowerCase();
        }
        else
        {
          fssaiAck = binding.etFssaiAckNumber.getText().toString().trim();
          date = binding.etFssaiDate.getText().toString().trim();
        }
        FssaiDetailsRequest request = new FssaiDetailsRequest();
        License license = new License();
        Acknowledgement acknowledgement = new Acknowledgement();
        license.setFile(myFssaiUrl);
        license.setFileType(doctype.toLowerCase());
        license.setNumber(fssaiNo);
        acknowledgement.setApplyDate(date);
        acknowledgement.setNumber(fssaiAck);
        request.setAcknowledgement(acknowledgement);
        request.setLicense(license);
        request.setAppVersion(version);
        request.setAvailability(fssaiCheck);
        request.setToken(token);
        addFssaiDetails(request);
    }

    private void addFssaiDetails(FssaiDetailsRequest request) {
        if(request!=null)
        {
            FssaiInterface fssaiInterface = RestClient.getRetrofitServiceInstance(getContext()).create(FssaiInterface.class);
            Call<ResponseBody> call = fssaiInterface.createFssaiDetails(request);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    fabloLoading.hideProgress();
                    if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                        fabloLoading.hideProgress();
                        ((BusinessSetupActivity)getContext()).selectStep("kyc");
                    } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                        fabloLoading.hideProgress();
                        Toast.makeText(getContext(), "There is some issue while adding FSSAI details.", Toast.LENGTH_SHORT).show();
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

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {


                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("dd/MM/yy", calendar1).toString(); //, MMM d, yyyy
                binding.etFssaiDate.setText(dateText);

            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void UploadFssaiDoc() {
        if(fssaiDocType.getType().equals("Document"))
        {
            fssaidocext = "fssaidocument.pdf";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("application/pdf"); //
            fssaiActivityResultLauncher.launch(galleryIntent);

        }
        else if(fssaiDocType.getType().equals("Image"))
        {
            fssaidocext ="fssaidocument.jpg";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            fssaiActivityResultLauncher.launch(galleryIntent);
        }
    }

    ActivityResultLauncher<Intent> fssaiActivityResultLauncher = registerForActivityResult(
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
                        binding.tvfssaiDocStatus.setText(displayName);
                        binding.btnFssaiUpload.setText("Reset");

                        firebaseFssaiUpload(uri);
                        // Toast.makeText(getContext(), "File Name"+ path, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    private void firebaseFssaiUpload(Uri uri) {
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        String t1 = encryptThisString(token);
        if(t1!=null)
        {
            final StorageReference fileref = storageReference.child(t1).child(fssaidocext);
            uploadtask = fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myFssaiUrl = uri.toString();
                            statusFssaiDoc = true;
                            fabloLoading.hideProgress();
                            //  Toast.makeText(getContext(), "MyUrl "+myUrl, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFireBaseSuccess: "+ myFssaiUrl);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GlobalError globalError) {
        if (globalError != null) {
            showError(globalError.getTitle(), globalError.getDescription());
        }
    }

    public void showError(String title, String description) {
        fabloAlert.showAlert(getContext(), Constant.ALERT_ERROR, false, title, description, "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FssaiDocType docType) {
        if (docType != null) {
            fssaiDocType = docType;
            binding.etFssaiDocType.setText(docType.getType());
            docTypeBottomSheet.dismiss();
            binding.btnFssaiUpload.setEnabled(true);
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
}