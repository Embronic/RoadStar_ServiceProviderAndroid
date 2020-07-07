package com.roadstar_serviceprovider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.adapter.RequestAdapter;
import com.roadstar_serviceprovider.base.BaseActivity;
import com.roadstar_serviceprovider.callback.RecyclerItemClickListener;
import com.roadstar_serviceprovider.databinding.ActivityRegisterBinding;
import com.roadstar_serviceprovider.databinding.ActivityRequestBinding;
import com.roadstar_serviceprovider.models.RequestModel;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends BaseActivity implements RecyclerItemClickListener<RequestModel> {

    ActivityRequestBinding mBinding;

    private RequestAdapter mRequestAdapter;
    private List<RequestModel> mRequestModelList = new ArrayList<>();

    /*Start Request activity intent*/
    public static void startActivity(Context context) {
        Intent homeIntent = new Intent(context, RequestActivity.class);
        context.startActivity(homeIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_request);

        initView();
        initToolbar();
    }

    private void initToolbar() {
        setHeaderTitle(getString(R.string.request));
        setBackEnabled(true);
    }

    private void initView() {
        /*Set refresh listner on list*/
        mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mBinding.swipeRefresh.setRefreshing(false);
            }
        });


        setUpBookingRecycler();
        bindStaticData();
        setUpBookingAdapter();
    }


    //SetUp Trip Journey Data For RecyclerView
    private void setUpBookingRecycler() {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mThis);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.requestRv.setLayoutManager(linearLayoutManager1);

    }

    private void bindStaticData() {
        mRequestModelList.add(new RequestModel());
        mRequestModelList.add(new RequestModel());
        mRequestModelList.add(new RequestModel());
        mRequestModelList.add(new RequestModel());
        mRequestModelList.add(new RequestModel());

    }

    //Set Trip Journey data adapter for RecyclerView
    private void setUpBookingAdapter() {
//        mBinding.swipeRefresh.setRefreshing(false);
        if (null == mRequestAdapter) {
            mRequestAdapter = new RequestAdapter(mThis, mRequestModelList, this);
            mBinding.requestRv.setAdapter(mRequestAdapter);
        } else {
            mRequestAdapter.setmBooking_itemModelList(mRequestModelList);
            mRequestAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position, RequestModel item, View v) {
        RequestDetailsActivity.startActivity(mThis);
    }
}