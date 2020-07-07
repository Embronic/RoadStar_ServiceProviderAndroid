package com.roadstar_serviceprovider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.adapter.CustomSpinnerAdapter;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.callback.DialogCallback;
import com.roadstar_serviceprovider.databinding.ActivityRequestDetailsBinding;
import com.roadstar_serviceprovider.models.CustomSpinnerModel;
import com.roadstar_serviceprovider.utils.AppUtils;
import com.roadstar_serviceprovider.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

public class RequestDetailsActivity extends BaseActivity implements DialogCallback {

    ActivityRequestDetailsBinding mBinding;
    private String mSelectedCategory, mProductTypeCategory;

    /*Start welcome activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, RequestDetailsActivity.class);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_request_details);

        init();
    }

    private void init() {
        initToolbar();
        initClicklistner();
        setCategorySpinnerAdapter();
        setProductTypeSpinnerAdapter();
    }


    private void initToolbar() {
        setHeaderTitle(getString(R.string.request_details));
        setBackEnabled(true);

    }

    private void initClicklistner() {
        mBinding.acceptButton.actionBtnParent.setOnClickListener(this);
        mBinding.cancelBtn.actionBtnParent.setOnClickListener(this);
    }

    private void setCategorySpinnerAdapter() {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(mThis, R.layout.spinner_dropdown_item, getCategorySpinnerData());

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBinding.spinner.setAdapter(adapter);

        mBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCategory = ((TextView) view.findViewById(R.id.spinnertitle)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setProductTypeSpinnerAdapter() {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(mThis, R.layout.spinner_dropdown_item, getProductTypeSpinnerData());

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBinding.productTypeSpinner.setAdapter(adapter);

        mBinding.productTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProductTypeCategory = ((TextView) view.findViewById(R.id.spinnertitle)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //get subscription spinner data
    private List<CustomSpinnerModel> getCategorySpinnerData() {
        List<CustomSpinnerModel> subscriptionList = new ArrayList<>();
        for (int i = 0; i < AppUtils.getCategorySpinnerData(mThis).length; i++) {
            CustomSpinnerModel model = new CustomSpinnerModel();
            model.setTitle(AppUtils.getCategorySpinnerData(mThis)[i]);
            subscriptionList.add(model);
        }
        return subscriptionList;
    }

    //get subscription spinner data
    private List<CustomSpinnerModel> getProductTypeSpinnerData() {
        List<CustomSpinnerModel> subscriptionList = new ArrayList<>();
        for (int i = 0; i < AppUtils.getProductTypeSpinnerData(mThis).length; i++) {
            CustomSpinnerModel model = new CustomSpinnerModel();
            model.setTitle(AppUtils.getProductTypeSpinnerData(mThis)[i]);
            subscriptionList.add(model);
        }
        return subscriptionList;
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                DialogUtil.showCancelRequestAlertDialog(mThis, getSupportFragmentManager(), this);
                break;
            case R.id.accept_button:
                LiveTrackingActivity.startActivity(mThis, "");
                break;

        }
    }

    @Override
    public void okPressed() {

    }

    @Override
    public void cancelPressed() {

    }
}
