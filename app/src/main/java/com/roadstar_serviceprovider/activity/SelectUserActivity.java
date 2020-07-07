package com.roadstar_serviceprovider.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivitySelectUserBinding;

public class SelectUserActivity extends BaseActivity {

    ActivitySelectUserBinding mBinding;

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
    }

    private void initView() {
        mBinding.screenBannerView.screenBannerImageview.setImageDrawable(getResources().getDrawable(R.drawable.select_user_bg));
    }

    private void initToolbar() {
        mBinding.screenToolbar.ivBack.setVisibility(View.GONE);
        mBinding.screenToolbar.toolbarTitleTv.setText(getString(R.string.register));
    }

    private void initClicklistners() {
        mBinding.registerUserBtn.actionBtnParent.setOnClickListener(this);
        mBinding.registerDriverBtn.actionBtnParent.setOnClickListener(this);
        mBinding.registerProviderBtn.actionBtnParent.setOnClickListener(this);
    }


    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_user_btn:
                RegisterCompanyActivity.startActivity(mThis);
                break;
            case R.id.register_driver_btn:
                RegisterProviderActivity.startActivity(mThis);
                break;

            case R.id.register_provider_btn:
                RegisterActivity.startActivity(mThis);
                break;
        }
    }
}
