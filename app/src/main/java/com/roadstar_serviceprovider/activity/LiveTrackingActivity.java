package com.roadstar_serviceprovider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivityLiveTrackingBinding;

public class LiveTrackingActivity extends BaseActivity {

    /*Start home activity intent*/
    public static void startActivity(Context context, String deliveryType) {
        Intent homeIntent = new Intent(context, LiveTrackingActivity.class);
        context.startActivity(homeIntent);
    }


    ActivityLiveTrackingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_live_tracking);
        initToolbar();
        initView();
        initClickListeners();
    }

    private void initToolbar() {
        setHeaderTitle("");
        setBackEnabled(true);

    }

    private void initView() {

    }

    private void initClickListeners() {
        mBinding.nextBtn.actionBtnParent.setOnClickListener(this);
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.next_btn:
                RatingActivity.startActivity(mThis);
                break;
        }
    }
}
