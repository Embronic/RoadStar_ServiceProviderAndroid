package com.roadstar_serviceprovider.activity;

import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivitySplashBinding;
import com.roadstar_serviceprovider.utils.Constants;
import com.roadstar_serviceprovider.utils.SessionManager;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding activitySplashBinding;

    private Runnable runnable = () -> {
        SessionManager.get().clear();
        WelcomeActivity.startActivity(this);
        finish();
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        moveToNext();
    }

    private void moveToNext() {
        Handler handler = new Handler();
        handler.postDelayed(runnable, Constants.SPLASH_DURATION);
    }
}
