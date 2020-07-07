package com.roadstar_serviceprovider.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseFragment;
import com.roadstar_serviceprovider.databinding.FragmentManageVehicleBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageVehicle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageVehicle extends BaseFragment {

    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String mHeaderTitle = "";
    private FragmentManageVehicleBinding mBinding;

    public ManageVehicle() {
        // Required empty public constructor
    }

    public static ManageVehicle newInstance(String headerTitle, String param2) {
        ManageVehicle fragment = new ManageVehicle();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_vehicle, container, false);

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
        mBinding.saveBtn.actionBtnParent.setOnClickListener(this);
    }


    private void initView() {
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.save_btn:
                break;

        }
    }
}

