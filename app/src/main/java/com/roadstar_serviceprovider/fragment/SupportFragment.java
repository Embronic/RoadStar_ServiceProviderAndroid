package com.roadstar_serviceprovider.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseFragment;
import com.roadstar_serviceprovider.databinding.FragmentSupportBinding;


public class SupportFragment extends BaseFragment {
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String mHeaderTitle = "";
    private FragmentSupportBinding mBinding;


    public SupportFragment() {
        // Required empty public constructor
    }


    public static SupportFragment newInstance(String headerTitle, String param2) {
        SupportFragment fragment = new SupportFragment();
        Bundle args = new Bundle();
        args.putString("HeaderTitle", headerTitle);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_support, container, false);

        getBundle();
        initClicklistners();
        initView();
        initToolbar();

        return mBinding.getRoot();
    }


    private void getBundle() {
        if (getArguments() != null) {
            mHeaderTitle = getArguments().getString("HeaderTitle");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initToolbar() {
        setBackEnabled(false, mBinding.getRoot());
        setHeaderTitle(mHeaderTitle, mBinding.getRoot());
    }


    private void initClicklistners() {
        mBinding.sendIc.setOnClickListener(this);
    }


    private void initView() {
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send_ic:
                mBinding.messageEdittext.setText("");
                break;

        }
    }

}
