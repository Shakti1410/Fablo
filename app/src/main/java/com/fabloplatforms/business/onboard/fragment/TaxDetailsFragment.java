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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentTaxDetailsBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BankInterface;
import com.fabloplatforms.business.interfaces.TaxInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.bottomsheet.SelectDocTypeBottomSheet;
import com.fabloplatforms.business.onboard.model.BankDocType;
import com.fabloplatforms.business.onboard.model.Gst;
import com.fabloplatforms.business.onboard.model.GstDocType;
import com.fabloplatforms.business.onboard.model.PanDocType;
import com.fabloplatforms.business.onboard.model.Pancard;
import com.fabloplatforms.business.onboard.model.TaxDetailsRequest;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TaxDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentTaxDetailsBinding binding;
    private SelectDocTypeBottomSheet docTypeBottomSheet;

    private FabloLoading fabloLoading;
    private FabloAlert fabloAlert;
    //confirm account number
    private Uri imageUri;
    private String myPanUrl = "",pandocext="",gstdocext="",myGstUrl="";
    private StorageReference storageReference;
    private StorageTask uploadtask;
    private boolean statusPanNumber = false;
    private boolean statusGstNumber = false;
    private boolean statusTurnOver = false;
    private boolean statusPanDoc = false;
    private boolean statusGstDoc = false;
    private boolean gstCheck= false;
    private Context context;


    private PanDocType panDocType;
    private GstDocType gstDocType;

    private static final String TAG = "TaxDetailsFragment";
    public TaxDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaxDetailsBinding.inflate(inflater, container, false);
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
        binding.btnPanUpload.setOnClickListener(this);
        binding.btnUploadGst.setOnClickListener(this);
        binding.btnProceed.setOnClickListener(this);
        binding.etPanDocType.setOnClickListener(this);
        binding.etGstDocType.setOnClickListener(this);
        binding.ivBackTax.setOnClickListener(this);
        binding.etPanNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String panNumber = binding.etPanNumber.getText().toString().trim();
                if (!panNumber.isEmpty()) {
                    if(checkPan(panNumber))
                    {
                        statusPanNumber = true;
                    }
                    else {
                        binding.etPanNumber.setError("Pan Number not valid");
                        statusPanNumber = false;
                    }

                } else {
                    statusPanNumber = false;
                }
                checkValidation();
            }
        });
        binding.etGstNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String gstNumber = binding.etGstNumber.getText().toString().trim();
                if (!gstNumber.isEmpty()) {
                    if(checkGst(gstNumber))
                    {

                        statusGstNumber = true;
                    }
                    else
                    {
                        binding.etGstNumber.setError("GSt Number not valid");
                        statusGstNumber = false;
                    }

                } else {
                    statusGstNumber = false;
                }
                checkValidation();
            }
        });
        binding.etTurnover.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String turnOver = binding.etTurnover.getText().toString().trim();
                if (!turnOver.isEmpty()) {
                    statusTurnOver = true;
                } else {
                    statusTurnOver = false;
                }
                checkValidation();
            }
        });
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkRadio(checkedId);

            }
        });


    }

    private boolean checkGst(String gstNumber) {
        Pattern pattern = Pattern.compile("[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}");
        Matcher matcher = pattern.matcher(gstNumber);
// Check if pattern matches
        if (matcher.matches()) {
            return  true;
        }
        else {
            return false;
        }
    }

    private boolean checkPan(String panNumber) {
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

        Matcher matcher = pattern.matcher(panNumber);
// Check if pattern matches
        if (matcher.matches()) {
            return  true;
        }
        else {
            return false;
        }
    }

    private void checkRadio(int checkedId) {
        if(checkedId == binding.gstRadioYes.getId())
        {
         binding.layoutGst.setVisibility(View.VISIBLE);
         gstCheck = true;
         checkValidation();


        }
        else if(checkedId == binding.gstRadioNo.getId())
        {
            binding.layoutGst.setVisibility(View.GONE);
            binding.etGstDocType.setText("");
            binding.etGstNumber.setText("");
            binding.btnUploadGst.setEnabled(false);
            binding.tvgstDocStatus.setText("No file Chosen");
            myGstUrl="";
            statusGstDoc =false;
            gstCheck = false;
            checkValidation();
        }



    }
    private void checkValidation() {
        Log.e(TAG, "checkValidation: "+statusPanNumber+" "+statusGstNumber+" "+statusTurnOver+" "+statusPanDoc+" "+statusGstDoc);
        if(gstCheck)
        {
            if (statusPanNumber && statusPanDoc && statusTurnOver && statusGstNumber && statusGstDoc)
            {
                binding.btnProceed.setEnabled(true);
            }
            else {
                binding.btnProceed.setEnabled(false);
            }

        }
        else
        {
            if (statusPanNumber && statusPanDoc && statusTurnOver) {

                binding.btnProceed.setEnabled(true);
            } else {
                binding.btnProceed.setEnabled(false);
            }
        }

    }
    @Override
    public void onClick(View v) {
        if (v == binding.btnProceed) {
            gatherData();
        }
        if (v == binding.etPanDocType) {
            Bundle args = new Bundle();
            args.putString("docType", "PAN");
            docTypeBottomSheet.setArguments(args);
            docTypeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            docTypeBottomSheet.show(getChildFragmentManager(), "docType");
            statusPanDoc = false;
            binding.tvpanDocStatus.setText("No file Chosen");
            checkValidation();
        }
        if (v == binding.btnPanUpload) {
            if(isOnline())
            {
                UploadPanDoc();
            }
            else
            {
                networkMsg();
            }

        }
        if (v == binding.etGstDocType) {
            Bundle args = new Bundle();
            args.putString("docType", "GST");
            docTypeBottomSheet.setArguments(args);
            docTypeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            docTypeBottomSheet.show(getChildFragmentManager(), "docType");
            statusGstDoc = false;
            binding.tvgstDocStatus.setText("No file Chosen");
            checkValidation();
        }
        if (v == binding.ivBackTax) {
            getActivity().onBackPressed();
        }
        if(v== binding.btnUploadGst)
        {
            if(isOnline())
            {
                UploadGstDoc();
            }
            else
            {
                networkMsg();
            }

        }
    }

    private void gatherData() {
        fabloLoading.showProgress(getContext());
        LoginPref loginPref = new LoginPref(context);
        String version = loginPref.getAppVersion();
        String token = loginPref.getToken();
        String pantype = panDocType.getType().trim();
        String gsttype = "";
        String panNo = binding.etPanNumber.getText().toString().trim();
        String gstNo = "";
        String turnOver = binding.etTurnover.getText().toString().trim();
        if(gstCheck)
        {
            gstNo = binding.etGstNumber.getText().toString().trim();
            gsttype = gstDocType.getType().trim().toLowerCase();
        }
        TaxDetailsRequest request = new TaxDetailsRequest();
        Pancard pancard = new Pancard();
        Gst gst = new Gst();
        pancard.setFile(myPanUrl);
        pancard.setFileType(pantype.toLowerCase());
        pancard.setNumber(panNo);
        gst.setIsAvailable(gstCheck);
        gst.setFile(myGstUrl);
        gst.setFileType(gsttype);
        gst.setNumber(gstNo);
        request.setAppVersion(version);
        request.setGst(gst);
        request.setPancard(pancard);
        request.setTurnOver(turnOver);
        request.setToken(token);
        addTaxDetails(request);
    }

    private void addTaxDetails(TaxDetailsRequest request) {
        if(request!=null)
        {
            TaxInterface taxInterface = RestClient.getRetrofitServiceInstance(getContext()).create(TaxInterface.class);
            Call<ResponseBody> call = taxInterface.createTaxDetails(request);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    fabloLoading.hideProgress();
                    if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                        fabloLoading.hideProgress();
                        ((BusinessSetupActivity)getContext()).selectStep("fssai");
                    } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                        fabloLoading.hideProgress();
                        Toast.makeText(getContext(), "There is some issue while adding tax details.", Toast.LENGTH_SHORT).show();
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

    private void UploadGstDoc() {
        if(gstDocType.getType().equals("Document"))
        {
            gstdocext = "gstdocument.pdf";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("application/pdf"); //
            gstActivityResultLauncher.launch(galleryIntent);

        }
        else if(gstDocType.getType().equals("Image"))
        {
            gstdocext ="gstdocument.jpg";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            gstActivityResultLauncher.launch(galleryIntent);
        }
    }
    ActivityResultLauncher<Intent> gstActivityResultLauncher = registerForActivityResult(
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
                        binding.tvgstDocStatus.setText(displayName);
                        binding.btnUploadGst.setText("Reset");

                        firebaseGstUpload(uri);
                        // Toast.makeText(getContext(), "File Name"+ path, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void firebaseGstUpload(Uri uri) {
        LoginPref loginPref = new LoginPref(context);

        String token = loginPref.getToken();
        String t1 = encryptThisString(token);
        if(t1!=null)
        {
            final StorageReference fileref = storageReference.child(t1).child(gstdocext);
            uploadtask = fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myGstUrl = uri.toString();
                            statusGstDoc = true;
                            fabloLoading.hideProgress();
                            //  Toast.makeText(getContext(), "MyUrl "+myUrl, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFireBaseSuccess: "+ myGstUrl);
                            checkValidation();
                        }
                    });
                }
            });
        }



    }


    private void UploadPanDoc() {
        if(panDocType.getType().equals("Document"))
        {
            pandocext = "pandocument.pdf";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("application/pdf"); //
            panActivityResultLauncher.launch(galleryIntent);

        }
        else if(panDocType.getType().equals("Image"))
        {
            pandocext ="pandocument.jpg";
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            panActivityResultLauncher.launch(galleryIntent);
        }

        //  startActivityForResult(galleryIntent, 1);
    }
    ActivityResultLauncher<Intent> panActivityResultLauncher = registerForActivityResult(
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
                        binding.tvpanDocStatus.setText(displayName);
                        binding.btnPanUpload.setText("Reset");
                        firebasePanUpload(uri);
                        // Toast.makeText(getContext(), "File Name"+ path, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    private void firebasePanUpload(Uri uri) {
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        String t1 = encryptThisString(token);
        if(t1!=null)
        {
            final StorageReference fileref = storageReference.child(t1).child(pandocext);
            uploadtask = fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myPanUrl = uri.toString();
                            statusPanDoc = true;
                            fabloLoading.hideProgress();
                            //  Toast.makeText(getContext(), "MyUrl "+myUrl, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFireBaseSuccess: "+ myPanUrl);
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
    public void onMessageEvent(PanDocType docType) {
        if (docType != null) {
            panDocType = docType;
            binding.etPanDocType.setText(docType.getType());
            docTypeBottomSheet.dismiss();
            binding.btnPanUpload.setEnabled(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GstDocType docType) {
        if (docType != null) {
            gstDocType = docType;
            binding.etGstDocType.setText(docType.getType());
            docTypeBottomSheet.dismiss();
            binding.btnUploadGst.setEnabled(true);
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