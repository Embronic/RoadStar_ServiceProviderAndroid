package com.roadstar_serviceprovider.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.activity.MainActivity;
import com.roadstar_serviceprovider.activity.RequestActivity;
import com.roadstar_serviceprovider.base.BaseFragment;
import com.roadstar_serviceprovider.databinding.FragmentDashboardBinding;

public class DashboardFragment extends BaseFragment {

    FragmentDashboardBinding mBinding;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);

        initClicklistners();
        initToolbar();
        initView();

        return mBinding.getRoot();
    }

    private void initToolbar() {
        //setInnerviewBackEnabled(true,mBinding.getRoot());
    }


    private void initClicklistners() {
        mBinding.requestView.mainCardview.setOnClickListener(this);
        mBinding.earningView.mainCardview.setOnClickListener(this);
        mBinding.historyView.mainCardview.setOnClickListener(this);
    }

    private void initView() {
        mBinding.requestView.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_request));
        mBinding.earningView.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_earning));
        mBinding.historyView.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_history));
    }


    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.request_view:
                RequestActivity.startActivity(getActivity());
                break;
            case R.id.earning_view:
                ((MainActivity)getActivity()).EarningNavigation();
                break;
            case R.id.history_view:
                ((MainActivity)getActivity()).BookingHistoryNavigation();
                break;
        }
    }
}
