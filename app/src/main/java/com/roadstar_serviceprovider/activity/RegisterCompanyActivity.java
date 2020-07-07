package com.roadstar_serviceprovider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivityRegisterCompanyBinding;
import com.roadstar_serviceprovider.databinding.ActivityRegisterProviderBinding;

public class RegisterCompanyActivity extends BaseActivity {

    ActivityRegisterCompanyBinding mBinding;

    String[] values  = {"Tricycle", "Car", "Pickup Truck", "Haul Truck"};

    /*Start RegisterOne activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, RegisterCompanyActivity.class);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_company);
        initToolbar();
        initView();
        initClickListeners();
        setNoVehicleSpinnerAdapter();
    }

    private void setNoVehicleSpinnerAdapter() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBinding.noVehicleSpinner.setAdapter(adapter);
    }

    private void initView() {
        mBinding.screenBannerView.screenBannerImageview.setImageDrawable(getResources().getDrawable(R.drawable.sign_up_provider_bg));
    }

    private void initToolbar() {
        setHeaderTitle(getString(R.string.register));
        setBackEnabled(true);
    }

    private void initClickListeners() {
        mBinding.viewSignIn.setOnClickListener(this);
        mBinding.registerBtn.actionBtnParent.setOnClickListener(this);
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.viewSignIn:
                SignInActivity.startActivity(mThis);
                break;
            case R.id.register_btn:
                MainActivity.startActivity(mThis);
                break;
        }
    }

}
