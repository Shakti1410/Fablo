package com.fabloplatforms.business.onboard.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentCreateBusinessBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BusinessInterface;
import com.fabloplatforms.business.interfaces.LocationInterface;
import com.fabloplatforms.business.onboard.BusinessSetupActivity;
import com.fabloplatforms.business.onboard.bottomsheet.AreaBottomSheet;
import com.fabloplatforms.business.onboard.bottomsheet.BusinessTypeBottomSheet;
import com.fabloplatforms.business.onboard.bottomsheet.CategoryBottomSheet;
import com.fabloplatforms.business.onboard.bottomsheet.CityBottomSheet;
import com.fabloplatforms.business.onboard.bottomsheet.CountryBottomSheet;
import com.fabloplatforms.business.onboard.bottomsheet.SelectDocTypeBottomSheet;
import com.fabloplatforms.business.onboard.bottomsheet.StateBottomSheet;
import com.fabloplatforms.business.onboard.model.AadhaarBackDocType;
import com.fabloplatforms.business.onboard.model.Address;
import com.fabloplatforms.business.onboard.model.AreaResponse;
import com.fabloplatforms.business.onboard.model.BusinessType;
import com.fabloplatforms.business.onboard.model.Category;
import com.fabloplatforms.business.onboard.model.City;
import com.fabloplatforms.business.onboard.model.Country;
import com.fabloplatforms.business.onboard.model.CreateBusinessError;
import com.fabloplatforms.business.onboard.model.CreateBusinessRequest;
import com.fabloplatforms.business.onboard.model.CreateBusinessResponse;

import com.fabloplatforms.business.onboard.model.StateResponse;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.FabloLoading;
import com.fabloplatforms.business.utils.GlobalError;
import com.fabloplatforms.business.utils.LoginPref;
import com.fabloplatforms.business.utils.TempModel;
import com.fabloplatforms.business.utils.TempPref;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBusinessFragment extends Fragment implements View.OnClickListener {

    FragmentCreateBusinessBinding binding;

    private BusinessTypeBottomSheet businessTypeBottomSheet;
    private CategoryBottomSheet categoryBottomSheet;
    private CityBottomSheet cityBottomSheet;
    private CountryBottomSheet countryBottomSheet;
    private StateBottomSheet stateBottomSheet;
    private AreaBottomSheet areaBottomSheet;
    private Uri imageUri;
    private String myLogoUrl = "", logoext="";
    private StorageReference storageReference;
    private boolean statusBusinessName = false;
    private boolean statusPhonenumber = false;
    private boolean statusPincode = false;
    private boolean statusAddress1 = false;
    private boolean statusAddress2 = false;
    private boolean statusBusinessCategory = false;
    private boolean statusCity = false, statusCountry=false;
    private boolean statusArea =false,statusState=false;
    private boolean statusBusinessType = false;
    private boolean statusLogo = false;
    private SelectDocTypeBottomSheet docTypeBottomSheet;


    private String uid;

    private Category.Item categoryData;
    private City.Item cityData;
    private Country.Item countryData;
    private StateResponse.Item stateData;
    private AreaResponse.Item areaData;
    private BusinessType businessTypeData;
    private StorageTask uploadtask;
    private FabloLoading fabloLoading;
    private FabloAlert fabloAlert;
    private Context context;

    private static final String TAG = "CreateBusinessFragment";

    public CreateBusinessFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateBusinessBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null) {
//            uid = firebaseAuth.getCurrentUser().getUid();
//        }
        LoginPref loginPref = new LoginPref(context);
        String phn = loginPref.getPhone();
        fabloLoading = FabloLoading.getInstance();
        fabloAlert = FabloAlert.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("fablo_business").child("merchant");
        docTypeBottomSheet = new SelectDocTypeBottomSheet();
        binding.etBusinessType.setOnClickListener(this);
        businessTypeBottomSheet = new BusinessTypeBottomSheet();
        categoryBottomSheet = new CategoryBottomSheet();
        cityBottomSheet = new CityBottomSheet();
        countryBottomSheet = new CountryBottomSheet();
        stateBottomSheet = new StateBottomSheet();
        areaBottomSheet = new AreaBottomSheet();


        binding.etPhoneNumber.setText(phn);
        statusPhonenumber = true;
        binding.etCity.setOnClickListener(this);
        binding.etCountry.setOnClickListener(this);
        binding.etBusinessCategory.setOnClickListener(this);
        binding.btnProceed.setOnClickListener(this);
        binding.etState.setOnClickListener(this);
        binding.etArea.setOnClickListener(this);
        binding.ivBackBasic.setOnClickListener(this);

        binding.btnlogoDocType.setOnClickListener(this);

        binding.etBusinessName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String businessName = binding.etBusinessName.getText().toString();
                if (businessName.isEmpty()) {
                    statusBusinessName = false;
                } else {
                    statusBusinessName = true;
                }
                checkValidation();
            }
        });
       /* binding.etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = binding.etPhoneNumber.getText().toString();
                if (phone.isEmpty()) {
                    statusPhonenumber = false;
                } else {
                    statusPhonenumber = true;
                }
                checkValidation();
            }
        });*/
        binding.etBusinessType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String businessType = binding.etBusinessType.getText().toString();
                if (businessType.isEmpty()) {
                    statusBusinessType = false;
                } else {
                    statusBusinessType = true;
                }
                checkValidation();
            }
        });

        binding.etBusinessCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String businessCategory = binding.etBusinessCategory.getText().toString();
                if (businessCategory.isEmpty()) {
                    statusBusinessCategory = false;
                } else {
                    statusBusinessCategory = true;
                }
                checkValidation();
            }
        });

        binding.etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String city = binding.etCity.getText().toString();
                if (city.isEmpty()) {
                    statusCity = false;
                } else {
                    statusCity = true;
                }
                checkValidation();
            }
        });
        binding.etCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String country = binding.etCountry.getText().toString();
                if (country.isEmpty()) {
                    statusCountry = false;
                } else {
                    statusCountry = true;
                }
                checkValidation();
            }
        });
        binding.etState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String state = binding.etState.getText().toString();
                if (state.isEmpty()) {
                    statusState = false;
                } else {
                    statusState = true;
                }
                checkValidation();
            }
        });
        binding.etArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String area = binding.etArea.getText().toString();
                if (area.isEmpty()) {
                    statusArea = false;
                } else {
                    statusArea = true;
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
                String agentcode = binding.etPincode.getText().toString();
                if (agentcode.isEmpty()) {
                    statusPincode = false;
                } else {
                    statusPincode = true;
                }
                checkValidation();
            }
        });
        binding.etAddress1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String address1 = binding.etAddress1.getText().toString();
                if (address1.isEmpty()) {
                    statusAddress1 = false;
                } else {
                    statusAddress1 = true;
                }
                checkValidation();
            }
        });
        binding.etAddress2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String address2 = binding.etAddress2.getText().toString();
                if (address2.isEmpty()) {
                    statusAddress2 = false;
                } else {
                    statusAddress2 = true;
                }
                checkValidation();
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == binding.etBusinessType) {
            binding.etBusinessName.clearFocus();
            binding.tiBusinessName.clearFocus();
            businessTypeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            businessTypeBottomSheet.show(getChildFragmentManager(), "type");
        }
        if (v == binding.etBusinessCategory) {
            binding.etBusinessName.clearFocus();
            binding.tiBusinessName.clearFocus();
            categoryBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            categoryBottomSheet.show(getChildFragmentManager(), "category");
        }
        if (v == binding.etCountry) {
            binding.etBusinessName.clearFocus();
            binding.tiBusinessName.clearFocus();
            countryBottomSheet.setStyle(DialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogStyle);
            countryBottomSheet.show(getChildFragmentManager(),"country");

        }
        if (v == binding.etState) {
            if(countryData!= null)
            {
                binding.etBusinessName.clearFocus();
                binding.tiBusinessName.clearFocus();
                int id = countryData.getId();
                Bundle args = new Bundle();
                args.putString("countryId", String.valueOf(id) );
                stateBottomSheet.setArguments(args);
                stateBottomSheet.setStyle(DialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogStyle);
                stateBottomSheet.show(getChildFragmentManager(),"state");
            }

        }



        if(v == binding.btnlogoDocType)
        {
            if(isOnline())
            {
                UploadLogo();
            }
            else
            {
                networkMsg();
            }
        }

        if (v == binding.etCity) {
            binding.etBusinessName.clearFocus();
            binding.tiBusinessName.clearFocus();
            String id = stateData.getId();
            Bundle args = new Bundle();
            args.putString("stateId", String.valueOf(id) );
            cityBottomSheet.setArguments(args);
            cityBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
            cityBottomSheet.show(getChildFragmentManager(), "city");
        }
        if (v == binding.etArea) {
            if(cityData!= null)
            {
                binding.etBusinessName.clearFocus();
                binding.tiBusinessName.clearFocus();
                String id = cityData.getId();
                Bundle args = new Bundle();
                args.putString("cityId", String.valueOf(id) );
                areaBottomSheet.setArguments(args);
                areaBottomSheet.setStyle(DialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogStyle);
                areaBottomSheet.show(getChildFragmentManager(),"state");
            }



        }
//        if (v == binding.etPincode) {
//            binding.etPincode.clearFocus();
//            binding.tiPincode.clearFocus();
//            businessTypeBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
//            businessTypeBottomSheet.show(getChildFragmentManager(), "type");
//        }
        if (v == binding.btnProceed) {
            binding.etBusinessName.clearFocus();
            binding.tiBusinessName.clearFocus();
            fabloLoading.showProgress(getContext());
            gatherData();
        }
        if(v==binding.ivBackBasic)
        {
            getActivity().onBackPressed();
        }
    }

    private void UploadLogo() {
        logoext ="businesslogo.jpg";
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        logoActivityResultLauncher.launch(galleryIntent);

    }

    private void checkValidation() {
        if (statusBusinessName && statusPhonenumber && statusBusinessCategory && statusBusinessType && statusCountry && statusState && statusCity && statusArea && statusPincode && statusAddress1 && statusAddress2 && statusLogo) {
            binding.btnProceed.setEnabled(true);
        } else {
            binding.btnProceed.setEnabled(false);
        }
    }

    ActivityResultLauncher<Intent> logoActivityResultLauncher = registerForActivityResult(
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
                        binding.tvlogoStatus.setText(displayName);
                        binding.btnlogoDocType.setText("Reset");
                        firebaseFrontUpload(uri);
                        // Toast.makeText(getContext(), "File Name"+ path, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    private void firebaseFrontUpload(Uri uri) {
        LoginPref loginPref = new LoginPref(context);
        String token = loginPref.getToken();
        String t1 = encryptThisString(token);
        if(t1!=null)
        {
            final StorageReference fileref = storageReference.child(t1).child(logoext);
            uploadtask = fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myLogoUrl = uri.toString();
                            binding.logoImg.setVisibility(View.VISIBLE);
                            binding.tvLogoInstruction.setVisibility(View.GONE);
                            Glide.with(context)
                                    .load(myLogoUrl)
                                    .into(binding.logoImg);
                            statusLogo = true;
                            fabloLoading.hideProgress();
                            //  Toast.makeText(getContext(), "MyUrl "+myUrl, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFireBaseSuccess: "+ myLogoUrl);
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
    public void onMessageEvent(City.Item city) {
        if (city != null) {
            cityData = city;
            binding.etCity.setText(city.getName());
            binding.etArea.setEnabled(true);
            cityBottomSheet.dismiss();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Country.Item country) {
        if (country != null) {
            countryData = country;
            binding.etCountry.setText(country.getName());
            binding.etState.setEnabled(true);
            countryBottomSheet.dismiss();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AreaResponse.Item area) {
        if (area != null) {
            areaData = area;
            binding.etArea.setText(area.getAreaName());
            areaBottomSheet.dismiss();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StateResponse.Item state) {
        if (state != null) {
            stateData = state;
            binding.etState.setText(state.getName());
            binding.etCity.setEnabled(true);
            stateBottomSheet.dismiss();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Category.Item category) {
        if (category != null) {
            categoryData = category;
            String en_hi_category = categoryData.getNameEnglish() + " (" + category.getNameHindi() + ")";
            binding.etBusinessCategory.setText(en_hi_category);
            categoryBottomSheet.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BusinessType businessType) {
        if (businessType != null) {
            businessTypeData = businessType;
            binding.etBusinessType.setText(businessType.getType());
            businessTypeBottomSheet.dismiss();
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
    private void gatherData() {
        CreateBusinessRequest createBusinessRequest = new CreateBusinessRequest();
        Address address = new Address();
        LoginPref loginPref = new LoginPref(context);
        String version = loginPref.getAppVersion();
        String token = loginPref.getToken();

//        try {
//            if (getContext() != null) {
//                PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
//                String version = pInfo.versionName;
//                Log.e(TAG, "gatherData: "+version);
//                createBusinessRequest.setAppVersion(version);
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null) {
//            String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
//            if (phone != null) {
//                String trimPhone = phone.replace("+91", "");
//                createBusinessRequest.setPhone(trimPhone);
//            }
//        }

        String businessName = binding.etBusinessName.getText().toString();
        String phone = binding.etPhoneNumber.getText().toString();
        String pincode = binding.etPincode.getText().toString();
        String address1 = binding.etAddress1.getText().toString();
        String address2 = binding.etAddress2.getText().toString();
        String countryId = String.valueOf(countryData.getId());
        address.setLineOne(address1);
        address.setLineTwo(address2);
        createBusinessRequest.setToken(token);
        createBusinessRequest.setBusinessLogo(myLogoUrl);
        createBusinessRequest.setAppVersion(version);
        createBusinessRequest.setBusinessName(businessName);
        createBusinessRequest.setPhone(phone);
        createBusinessRequest.setPincode(pincode);
        createBusinessRequest.setAddress(address);
        createBusinessRequest.setCategoryId(categoryData.getId());
        createBusinessRequest.setBusinessType(businessTypeData.getId());
        createBusinessRequest.setBusinessTypeName(businessTypeData.getType());
        createBusinessRequest.setCategoryEnglish(categoryData.getNameEnglish());
        createBusinessRequest.setCategoryHindi(categoryData.getNameHindi());
        createBusinessRequest.setCityName(cityData.getName());
        createBusinessRequest.setCityId(cityData.getId());
        createBusinessRequest.setAreaId(areaData.getId());
        createBusinessRequest.setAreaName(areaData.getAreaName());
        createBusinessRequest.setCountryId(countryId);
        createBusinessRequest.setCountryName(countryData.getName());
        createBusinessRequest.setStateId(stateData.getId());
        createBusinessRequest.setStateName(stateData.getName());





        createBusiness(createBusinessRequest);
    }

    private void createBusiness(CreateBusinessRequest createBusinessRequest) {
        BusinessInterface businessInterface = RestClient.getRetrofitServiceInstance(getContext()).create(BusinessInterface.class);
        Call<CreateBusinessResponse> call = businessInterface.createBusiness(createBusinessRequest);
        call.enqueue(new Callback<CreateBusinessResponse>() {
            @Override
            public void onResponse(@NotNull Call<CreateBusinessResponse> call, @NotNull Response<CreateBusinessResponse> response) {

                fabloLoading.hideProgress();

                if (response.code() == Constant.HTTP_RESOURCE_CREATED) {
                    if (getContext() != null) {
                        if (response.body() != null) {
//                            SharedPreferences preferences = getContext().getSharedPreferences("temp", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("id", response.body().getItems().getId());
//                            editor.apply();
                            String id = response.body().getItems().getId();
                            TempModel model = new TempModel();
                            model.setId(id);
                            TempPref tempPref = new TempPref(context);
                            tempPref.setId(model);
                            ((BusinessSetupActivity)getContext()).selectStep("contact");
                        }
                    }
                } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                    Gson gson = new GsonBuilder().create();
                    CreateBusinessError createBusinessError = new CreateBusinessError();
                    try {
                        createBusinessError = gson.fromJson(response.errorBody().string(), CreateBusinessError.class);
                        if (createBusinessError.getItems().getKeyPattern().getPhone() == 1) {
                            showError("Incorrect details", "Seems that the phone number you have entered is already in use.");
                        }
                    } catch (IOException e) { // handle failure at error parse }
                        //showError("Incorrect details", "Seems there is some issue with the details you have entered.");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CreateBusinessResponse> call, @NotNull Throwable t) {
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