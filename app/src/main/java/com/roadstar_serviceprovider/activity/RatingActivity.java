package com.roadstar_serviceprovider.activity;

import androidx.databinding.DataBindingUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.databinding.ActivityRatingBinding;

public class RatingActivity extends BaseActivity {

    ActivityRatingBinding mBinding;


    /*Start home activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, RatingActivity.class);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rating);
        initToolbar();
        initView();
        initClickListeners();
    }

    private void initToolbar() {
        setHeaderTitle(getString(R.string.rating));
        setBackEnabled(true);

    }

    private void initView() {

    }

    private void initClickListeners() {
    }
}