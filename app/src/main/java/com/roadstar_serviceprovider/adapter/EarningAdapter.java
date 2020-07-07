package com.roadstar_serviceprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.callback.RecyclerItemClickListener;
import com.roadstar_serviceprovider.databinding.BookingsChilviewBinding;
import com.roadstar_serviceprovider.databinding.EarningChilviewBinding;
import com.roadstar_serviceprovider.models.BookingModel;
import com.roadstar_serviceprovider.models.EarningModel;

import java.util.List;

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.TripJourneyHolder> {

    private List<EarningModel> mTripJourney_itemModelList;
    private final Context mContext;
    private RecyclerItemClickListener<EarningModel> mCallback;
    private LinearLayoutManager linearLayoutManager = null;
    private String  mDelayDeparture = "", mDelayArrival = "";

    /*constructor  for setting travel option adapter
     * data : bind list of available transport medium
     * callback : item clicklistner callback
     * */
    public EarningAdapter(Context context, List<EarningModel> data, RecyclerItemClickListener<EarningModel> callback) {
        mTripJourney_itemModelList = data;
        this.mContext = context;
        this.mCallback = callback;
    }


    //get jorney plan list
    public void setmBooking_itemModelList(List<EarningModel> mTripJourney_itemModelList) {
        this.mTripJourney_itemModelList = mTripJourney_itemModelList;
    }


    //set jorney plan list
    @Override
    public EarningAdapter.TripJourneyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EarningChilviewBinding tripJourneyChilviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.earning_chilview, parent, false);
        return new EarningAdapter.TripJourneyHolder(tripJourneyChilviewBinding);
    }

    /* binds view according to position in holder class*/
    @Override
    public void onBindViewHolder(EarningAdapter.TripJourneyHolder holder, int position) {
        holder.bindRow(mTripJourney_itemModelList.get(position), position);
    }

    /*get item count*/
    @Override
    public int getItemCount() {
        return mTripJourney_itemModelList.size();
    }

    /*set data in view*/
    public class TripJourneyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final EarningChilviewBinding itemViewBinding;

        public TripJourneyHolder(EarningChilviewBinding itemView) {
            super(itemView.getRoot());
            this.itemViewBinding = itemView;
        }

        public void bindRow(EarningModel bean, final int position) {

        }

        /*perform click*/
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainLin:
                    if (null != mCallback)
                        mCallback.onItemClick(getAdapterPosition(), mTripJourney_itemModelList.get(getAdapterPosition()), itemViewBinding.getRoot());
                    break;
            }
        }
    }
}
