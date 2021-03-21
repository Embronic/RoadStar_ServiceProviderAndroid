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
import com.roadstar_serviceprovider.databinding.ActivityRegisterBinding;
import com.roadstar_serviceprovider.models.state.StatesData;
import com.roadstar_serviceprovider.models.otp.OTPResponse;
import com.roadstar_serviceprovider.utils.AppUtils;
import com.roadstar_serviceprovider.utils.Constants;
import com.roadstar_serviceprovider.utils.Lg;
import com.roadstar_serviceprovider.utils.NetworkHandler;
import com.roadstar_serviceprovider.utils.SessionManager;
import com.roadstar_serviceprovider.utils.ValidUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    ActivityRegisterBinding mBinding;
    private String TAG = "RegisterActivity";

    /*Start register activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, RegisterActivity.class);
        context.startActivity(homeIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initToolbar();
        initView();
        initClicklistners();
    }

    private void initClicklistners() {
        mBinding.registerBtn.actionBtnParent.setOnClickListener(this);
    }

    private void initView() {
        mBinding.screenBannerView.screenBannerImageview.setImageDrawable(getResources().getDrawable(R.drawable.resgister_scooter));


    }


    private void initToolbar() {
        setHeaderTitle(getString(R.string.register));
        setBackEnabled(true);
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_btn:
                if (isAllFieldsValid()) {
                    onRegisterButtonClicked();
                }
                break;
        }
    }

    private boolean isAllFieldsValid() {
        if (ValidUtils.isBlank(mBinding.mobileView.etMobile)) {
            AppUtils.showToast(R.string.error_blank_mobile);
            return false;
        }
        /*if (ValidUtils.validatePhoneLength(mBinding.mobileView.etMobile)) {
            AppUtils.showToast(R.string.error_invalid_mobile);
            return false;
        }*/
        return true;
    }

    private void onRegisterButtonClicked() {
        if (NetworkHandler.isConnected(mThis)) {
            showHideProgressDialog(true);
            AppUtils.hideKeyboard(mThis);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<OTPResponse> call = apiService.getOTP(mBinding.mobileView.etMobile.getText().toString(),
                    mBinding.mobileView.countryPicker.getSelectedCountryCodeWithPlus(),
                    mBinding.mobileView.countryPicker.getSelectedCountryName(),
                    mBinding.mobileView.countryPicker.getSelectedCountryNameCode());

            call.enqueue(new Callback<OTPResponse>() {
                @Override
                public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                    showHideProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            SessionManager.get().setNumber(mBinding.mobileView.etMobile.getText().toString());
                            SessionManager.get().setCountryCode(response.body().getData().getCountry_id());

                            OTPActivity.startActivity(mThis, mBinding.mobileView.countryPicker.getSelectedCountryCodeWithPlus(),
                                    mBinding.mobileView.countryPicker.getSelectedCountryName(),
                                    mBinding.mobileView.countryPicker.getSelectedCountryNameCode(),
                                    response.body().getData().getOtp());

                        } else {
                            AppUtils.showToast(mThis, response.body().getMessage());

                        }

                    } else {
                        showHideProgressDialog(false);
                        AppUtils.showToast(R.string.server_error);
                    }
                }

                @Override
                public void onFailure(Call<OTPResponse> call, Throwable t) {
                    showHideProgressDialog(false);
                    Lg.e(TAG, t.getMessage());
                    AppUtils.showToast(R.string.server_error);
                }
            });
        }
    }
}