package com.roadstar_serviceprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.callback.RecyclerItemClickListener;
import com.roadstar_serviceprovider.databinding.DocumentChildviewBinding;
import com.roadstar_serviceprovider.models.document.DocumentData;

import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.CompanyDocumentHolder> {

    private ArrayList<DocumentData> mCompanyDocument_itemModelList;
    private final Context mContext;
    private RecyclerItemClickListener<DocumentData> mCallback;

    public DocumentAdapter(Context context, ArrayList<DocumentData> data, RecyclerItemClickListener<DocumentData> callback) {
        mCompanyDocument_itemModelList = data;
        this.mContext = context;
        this.mCallback = callback;
    }


    //get jorney plan list
    public void setmDoucment_itemModelList(ArrayList<DocumentData> mCompanyDocument_itemModelList) {
        this.mCompanyDocument_itemModelList = mCompanyDocument_itemModelList;
    }

    public void updateItemDocument(int position, DocumentData documentData) {
        this.mCompanyDocument_itemModelList.set(position, documentData);
        notifyDataSetChanged();
    }

    public DocumentData getItem(int position) {
        return  this.mCompanyDocument_itemModelList.get(position);
    }

    public ArrayList<DocumentData> getItems() {
        return this.mCompanyDocument_itemModelList;
    }

    //set jorney plan list
    @Override
    public DocumentAdapter.CompanyDocumentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DocumentChildviewBinding childviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.document_childview, parent, false);
        return new DocumentAdapter.CompanyDocumentHolder(childviewBinding);
    }

    /* binds view according to position in holder class*/
    @Override
    public void onBindViewHolder(DocumentAdapter.CompanyDocumentHolder holder, int position) {
        holder.bindRow(mCompanyDocument_itemModelList.get(position), position);
    }

    /*get item count*/
    @Override
    public int getItemCount() {
        return mCompanyDocument_itemModelList.size();
    }

    /*set data in view*/
    public class CompanyDocumentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final DocumentChildviewBinding itemViewBinding;

        public CompanyDocumentHolder(DocumentChildviewBinding itemView) {
            super(itemView.getRoot());
            this.itemViewBinding = itemView;
        }

        public void bindRow(DocumentData bean, final int position) {
            itemViewBinding.tvTitle.setText(mCompanyDocument_itemModelList.get(position).getName());
            itemViewBinding.mainLin.setOnClickListener(this);

        }

        /*perform click*/
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainLin:
                    if (null != mCallback)
                        mCallback.onItemClick(getAdapterPosition(), mCompanyDocument_itemModelList.get(getAdapterPosition()), itemViewBinding.getRoot());
                    break;
            }
        }
    }
}
