package com.roadstar_serviceprovider.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends BaseActivity {

    ActivityWelcomeBinding mBinding;


    /*Start welcome activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, WelcomeActivity.class);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        initToolbar();

        initClicklistners();
        initView();
    }

    private void initView() {
        mBinding.screenBannerView.screenBannerImageview.setImageDrawable(getResources().getDrawable(R.drawable.welcome_bg));
    }

    private void initToolbar() {
        mBinding.screenToolbar.ivBack.setVisibility(View.GONE);
        mBinding.screenToolbar.toolbarTitleTv.setText(getString(R.string.welcome));
    }

    private void initClicklistners() {
        mBinding.registerBtn.actionBtnParent.setOnClickListener(this);
        mBinding.signInBtn.actionBtnParent.setOnClickListener(this);
    }


    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_btn:
                RegisterActivity.startActivity(mThis);
                break;
            case R.id.signIn_btn:
                SignInActivity.startActivity(mThis);
                break;
        }
    }
}
