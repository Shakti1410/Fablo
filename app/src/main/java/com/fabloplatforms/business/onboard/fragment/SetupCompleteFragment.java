package com.fabloplatforms.business.onboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.fabloplatforms.business.MainActivity;
import com.fabloplatforms.business.databinding.FragmentSetupCompleteBinding;
import com.fabloplatforms.business.exception.NoConnectivityException;
import com.fabloplatforms.business.interfaces.BusinessInterface;
import com.fabloplatforms.business.onboard.model.QrRequest;
import com.fabloplatforms.business.retrofit.RestClient;
import com.fabloplatforms.business.utils.Constant;
import com.fabloplatforms.business.utils.FabloAlert;
import com.fabloplatforms.business.utils.GlobalError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.WINDOW_SERVICE;

public class SetupCompleteFragment extends Fragment implements View.OnClickListener {

    FragmentSetupCompleteBinding binding;
    private Context context;
    private FabloAlert fabloAlert;

    public SetupCompleteFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSetupCompleteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if (getContext() != null) {
            context = getContext();
        }
        initView();
        return view;
    }

    private void initView() {
        fabloAlert = FabloAlert.getInstance();
        binding.btnGetStarted.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnGetStarted) {
            binding.btnGetStarted.setVisibility(View.GONE);
            binding.progressGetStarted.setVisibility(View.VISIBLE);

            SharedPreferences preferences = context.getSharedPreferences("temp", Context.MODE_PRIVATE);
            String business_id = preferences.getString("id", "none");

            generateQR(business_id);
        }
    }

    private void generateQR(String business_id) {
        WindowManager manager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = Math.min(width, height);
        smallerDimension = smallerDimension * 3 / 4;
        QRGEncoder qrgEncoder = new QRGEncoder(business_id, null, QRGContents.Type.TEXT, smallerDimension);
        Bitmap bitmap = qrgEncoder.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        uploadQR(business_id, data);
    }

    private void uploadQR(String business_id, byte[] data) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("merchant/qr/" + business_id + ".jpg");
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageRef.getDownloadUrl().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        if (task1.getResult() != null) {
                            String qr = task1.getResult().toString();
                            registerGlobalQr(business_id, qr);
                        }
                    }
                });
            }
        });
    }

    private void registerGlobalQr(String business_id, String qr) {
        QrRequest qrRequest = new QrRequest();
        qrRequest.setQr(qr);
        BusinessInterface businessInterface = RestClient.getRetrofitServiceInstance(context).create(BusinessInterface.class);
        Call<ResponseBody> call = businessInterface.activateGlobalQr(business_id, qrRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.code() == Constant.HTTP_RESPONSE_SUCCESS) {
                    gotoMainScreen();
                } else if (response.code() == Constant.HTTP_CLIENT_ERROR) {
                    showError("Something went wrong", "We cannot process your request right now, please try again after some time.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    GlobalError globalError = new GlobalError("Internet error", "Seems you don't have proper internet connection right now, please try again later.", Constant.STATUS_NO_ERROR);
                    EventBus.getDefault().post(globalError);
                    binding.btnGetStarted.setVisibility(View.VISIBLE);
                    binding.progressGetStarted.setVisibility(View.GONE);
                }
            }
        });
    }

    private void gotoMainScreen() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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