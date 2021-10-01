package com.fabloplatforms.business;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fabloplatforms.business.auth.PhoneActivity;
import com.fabloplatforms.business.databinding.ActivitySplashBinding;
import com.fabloplatforms.business.store.utils.StoreFabloAlert;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    private Context context;
    private StoreFabloAlert storeFabloAlert;

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = SplashActivity.this;
        storeFabloAlert = new StoreFabloAlert();
        addDelay();
    }

    private void addDelay() {
        new Handler().postDelayed(this::checkAuth, 1000);
    }

    private void checkAuth() {
        gotoLoginScreen();


//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null) {
//            gotoAuthStateScreen();
//        } else {
//
//        }
    }

    private void gotoLoginScreen() {
        Intent intent = new Intent(SplashActivity.this, PhoneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void gotoAuthStateScreen() {
        Intent intent = new Intent(SplashActivity.this, GatewayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}