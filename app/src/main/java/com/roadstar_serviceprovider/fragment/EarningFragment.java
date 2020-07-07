package com.roadstar_serviceprovider.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.adapter.BookingAdapter;
import com.roadstar_serviceprovider.adapter.EarningAdapter;
import com.roadstar_serviceprovider.base.BaseFragment;
import com.roadstar_serviceprovider.callback.RecyclerItemClickListener;
import com.roadstar_serviceprovider.databinding.FragmentBookingsBinding;
import com.roadstar_serviceprovider.databinding.FragmentEarningBinding;
import com.roadstar_serviceprovider.models.BookingModel;
import com.roadstar_serviceprovider.models.EarningModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EarningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EarningFragment extends BaseFragment implements RecyclerItemClickListener<EarningModel> {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentEarningBinding mBinding;
    private String mHeaderTitle = "";
    private EarningAdapter mEarningAdapter;
    private List<EarningModel> mEarningItemModelList = new ArrayList<>();


    public EarningFragment() {
        // Required empty public constructor
    }


    public static BookingsFragment newInstance(String headerTitle, String param2) {
        BookingsFragment fragment = new BookingsFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_earning, container, false);

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
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.earningRv.setLayoutManager(linearLayoutManager1);

    }

    private void bindStaticData() {
        mEarningItemModelList.add(new EarningModel());
        mEarningItemModelList.add(new EarningModel());
        mEarningItemModelList.add(new EarningModel());
        mEarningItemModelList.add(new EarningModel());
        mEarningItemModelList.add(new EarningModel());

    }

    //Set Trip Journey data adapter for RecyclerView
    private void setUpBookingAdapter() {
//        mBinding.swipeRefresh.setRefreshing(false);
        if (null == mEarningAdapter) {
            mEarningAdapter = new EarningAdapter(getActivity(), mEarningItemModelList, this);
            mBinding.earningRv.setAdapter(mEarningAdapter);
        } else {
            mEarningAdapter.setmBooking_itemModelList(mEarningItemModelList);
            mEarningAdapter.notifyDataSetChanged();
        }
    }

    //perform onClick
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

        }
    }

    @Override
    public void onItemClick(int position, EarningModel item, View v) {

    }
}
