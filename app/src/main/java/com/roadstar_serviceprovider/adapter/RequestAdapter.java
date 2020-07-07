package com.roadstar_serviceprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.callback.RecyclerItemClickListener;
import com.roadstar_serviceprovider.databinding.RequestChilviewBinding;
import com.roadstar_serviceprovider.models.RequestModel;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ServiceHolder> implements View.OnClickListener {

    private List<RequestModel> mTripJourney_itemModelList;
    private final Context mContext;
    private RecyclerItemClickListener<RequestModel> mCallback;

    public RequestAdapter(Context context, List<RequestModel> data, RecyclerItemClickListener<RequestModel> callback) {
        mTripJourney_itemModelList = data;
        this.mContext = context;
        this.mCallback = callback;
    }


    //get list
    public void setmBooking_itemModelList(List<RequestModel> mTripJourney_itemModelList) {
        this.mTripJourney_itemModelList = mTripJourney_itemModelList;
    }


    //set list
    @Override
    public RequestAdapter.ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RequestChilviewBinding requestChilviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.request_chilview, parent, false);
        return new RequestAdapter.ServiceHolder(requestChilviewBinding);
    }

    /* binds view according to position in holder class*/
    @Override
    public void onBindViewHolder(RequestAdapter.ServiceHolder holder, int position) {

    }

    /*get item count*/
    @Override
    public int getItemCount() {
        return mTripJourney_itemModelList.size();
    }

    @Override
    public void onClick(View v) {

    }

    /*set data in view*/
    public class ServiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final RequestChilviewBinding itemViewBinding;

        public ServiceHolder(RequestChilviewBinding itemView) {
            super(itemView.getRoot());
            this.itemViewBinding = itemView;
            itemView.mainLin.actionBtnParent.setBackground(mContext.getResources()
                    .getDrawable(R.drawable.global_red_bg_without_corners));

            itemView.mainLin.actionBtnParent.setOnClickListener(this);
            itemView.mainLin.actionBtnParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mCallback)
                        mCallback.onItemClick(getAdapterPosition(), mTripJourney_itemModelList.get(getAdapterPosition()), itemViewBinding.getRoot());
                }
            });
        }

        public void bindRow(RequestModel bean, final int position) {

        }

        /*perform click*/
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainLin:
                    if (null != mCallback)
                        mCallback.onItemClick(getAdapterPosition(), mTripJourney_itemModelList.get(getAdapterPosition()), itemViewBinding.getRoot());
                    break;

                case R.id.register_btn:

                    break;
            }
        }
    }
}
