package com.roadstar_serviceprovider.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.api.ApiClient;
import com.roadstar_serviceprovider.api.ApiInterface;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivitySelectUserBinding;
import com.roadstar_serviceprovider.models.state.StatesData;
import com.roadstar_serviceprovider.models.state.StatesResponse;
import com.roadstar_serviceprovider.utils.AppUtils;
import com.roadstar_serviceprovider.utils.Lg;
import com.roadstar_serviceprovider.utils.NetworkHandler;
import com.roadstar_serviceprovider.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectUserActivity extends BaseActivity {

    ActivitySelectUserBinding mBinding;
    private String TAG = "SelectUserActivity";
    ArrayList<StatesData> arrayList = new ArrayList<>();

    /*Start welcome activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, SelectUserActivity.class);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_user);
        initToolbar();

        initClicklistners();
        initView();
        fetchStateList();

    }

    private void fetchStateList() {
        if (NetworkHandler.isConnected(mThis)) {
            showHideProgressDialog(true);
            AppUtils.hideKeyboard(mThis);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<StatesResponse> call = apiService.getStates(SessionManager.get().getCountryCode());

            call.enqueue(new Callback<StatesResponse>() {
                @Override
                public void onResponse(Call<StatesResponse> call, Response<StatesResponse> response) {
                    showHideProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            arrayList = response.body().getData();

                        } else {
                            AppUtils.showToast(mThis, response.body().getMessage());

                        }

                    } else {
                        showHideProgressDialog(false);
                        AppUtils.showToast(R.string.server_error);
                    }
                }

                @Override
                public void onFailure(Call<StatesResponse> call, Throwable t) {
                    showHideProgressDialog(false);
                    Lg.e(TAG, t.getMessage());
                    AppUtils.showToast(R.string.server_error);
                }
            });
        }
    }

    private void initView() {
        mBinding.screenBannerView.screenBannerImageview.setImageDrawable(getResources().getDrawable(R.drawable.select_user_bg));
    }

    private void initToolbar() {
        mBinding.screenToolbar.ivBack.setVisibility(View.GONE);
        mBinding.screenToolbar.toolbarTitleTv.setText(getString(R.string.register));
    }

    private void initClicklistners() {
        mBinding.registerCompanyBtn.actionBtnParent.setOnClickListener(this);
        mBinding.registerDriverBtn.actionBtnParent.setOnClickListener(this);
        mBinding.registerProviderBtn.actionBtnParent.setOnClickListener(this);
    }


    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_company_btn:
                RegisterCompanyActivity.startActivity(mThis, arrayList);
                break;

            case R.id.register_driver_btn:
                RegisterDriverActivity.startActivity(mThis, arrayList);
                break;

            case R.id.register_provider_btn:
                RegisterProviderActivity.startActivity(mThis, arrayList);
                break;
        }
    }
}
