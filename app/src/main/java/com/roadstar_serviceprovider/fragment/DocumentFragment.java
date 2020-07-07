package com.roadstar_serviceprovider.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.base.BaseFragment;
import com.roadstar_serviceprovider.databinding.FragmentDocumentBinding;
import com.roadstar_serviceprovider.databinding.FragmentManageVehicleBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocumentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentFragment extends BaseFragment {

    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String mHeaderTitle = "";
    private FragmentDocumentBinding mBinding;

    public DocumentFragment() {
        // Required empty public constructor
    }

    public static DocumentFragment newInstance(String headerTitle, String param2) {
        DocumentFragment fragment = new DocumentFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_document, container, false);

        getBundle();
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


    private void initView() {
        mBinding.vehicleView.tvTitle.setText(R.string.vehicle_image);
        mBinding.licenceView.tvTitle.setText(R.string.licence);
        mBinding.insuranceView.tvTitle.setText(R.string.insurance);
        mBinding.residenceView.tvTitle.setText(R.string.residence_image);
    }

}
