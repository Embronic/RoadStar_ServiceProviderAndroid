package com.roadstar_serviceprovider.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.api.ApiClient;
import com.roadstar_serviceprovider.api.ApiInterface;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivitySignInBinding;
import com.roadstar_serviceprovider.models.login.LoginResponse;
import com.roadstar_serviceprovider.models.serviceMedium.ServiceMediumResponse;
import com.roadstar_serviceprovider.utils.AppUtils;
import com.roadstar_serviceprovider.utils.Lg;
import com.roadstar_serviceprovider.utils.NetworkHandler;
import com.roadstar_serviceprovider.utils.ValidUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends BaseActivity {

    private static final String TAG = "SignInActivity";
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
                if(isAllFieldsValid()){
                    onButtonClicked();
                }
                break;
            case R.id.register_now_lbl:
                SelectUserActivity.startActivity(mThis);
                finishAffinity();
                break;
            case R.id.forgot_password_lbl:
                ForgotPasswordActivity.startActivity(mThis);
                break;
        }
    }

    private boolean isAllFieldsValid() {
        if (ValidUtils.isBlank(mBinding.etEmail)) {
            AppUtils.showToast(R.string.error_blank_email);
            return false;
        }

        if (ValidUtils.isBlank(mBinding.etPassword)) {
            AppUtils.showToast(R.string.error_blank_password);
            return false;
        }

        return true;
    }

    private void onButtonClicked() {
        if (NetworkHandler.isConnected(mThis)) {
            AppUtils.hideKeyboard(mThis);
            showHideProgressDialog(true);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<LoginResponse> call = apiService.Login(mBinding.etEmail.getText().toString(), mBinding.etPassword.getText().toString());

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    showHideProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            MainActivity.startActivity(mThis);
                            finish();

                        } else {
                            AppUtils.showToast(mThis, response.body().getMessage());

                        }

                    } else {
                        AppUtils.showToast(R.string.server_error);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    showHideProgressDialog(false);
                    Lg.e(TAG, t.getMessage());
                    AppUtils.showToast(R.string.server_error);
                }
            });
        }
    }
}
