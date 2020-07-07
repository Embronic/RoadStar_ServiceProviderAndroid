package com.roadstar_serviceprovider.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseFragment;
import com.roadstar_serviceprovider.databinding.FragmentYourPackageBinding;

public class YourPackageFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentYourPackageBinding mBinding;
    private String mHeaderTitle = "";


    public YourPackageFragment() {
        // Required empty public constructor
    }


    public static YourPackageFragment newInstance(String headerTitle, String param2) {
        YourPackageFragment fragment = new YourPackageFragment();
        Bundle args = new Bundle();
        args.putString("HeaderTitle", headerTitle);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_your_package, container, false);
        initClicklistners();
        initView();
        initToolbar();

        return mBinding.getRoot();
    }

    private void initToolbar() {
        setBackEnabled(false, mBinding.getRoot());
        setHeaderTitle(mHeaderTitle, mBinding.getRoot());
    }


    private void initClicklistners() {
        mBinding.nextBtn.actionBtnParent.setOnClickListener(this);
    }


    private void initView() {
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.next_btn:
                break;

        }
    }
}
