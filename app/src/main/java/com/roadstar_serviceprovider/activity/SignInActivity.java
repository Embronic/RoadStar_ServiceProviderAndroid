package com.roadstar_serviceprovider.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivitySignInBinding;

public class SignInActivity extends BaseActivity {

    ActivitySignInBinding mBinding;

    /*Start SignIn activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, SignInActivity.class);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        initToolbar();

        initClickListeners();
        initView();
    }

    private void initToolbar() {
        setHeaderTitle(getString(R.string.signin));
        setBackEnabled(true);

    }

    private void initClickListeners() {
        mBinding.registerBtn.actionBtnParent.setOnClickListener(this);
        mBinding.registerNowLbl.setOnClickListener(this);
        mBinding.forgotPasswordLbl.setOnClickListener(this);
    }

    private void initView() {
        mBinding.screenBannerView.screenBannerImageview.setImageDrawable(getResources().getDrawable(R.drawable.sign_in_bg));
    }


    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_btn:
                MainActivity.startActivity(mThis);
                break;
            case R.id.register_now_lbl:
                RegisterDriverActivity.startActivity(mThis);
                break;
            case R.id.forgot_password_lbl:
                ForgotPasswordActivity.startActivity(mThis);
                break;
        }
    }
}
