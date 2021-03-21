package com.roadstar_serviceprovider.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roadstar_serviceprovider.R;
import com.roadstar_serviceprovider.models.BaseData;
import com.roadstar_serviceprovider.models.serviceType.ServiceMediumData;

import java.util.List;

public class ServiceMediumSpinnerAdapter extends ArrayAdapter<ServiceMediumData> {

    private LayoutInflater flater;
    List<ServiceMediumData> list;

    public ServiceMediumSpinnerAdapter(Activity context, int resouceId, List<ServiceMediumData> list) {
        super(context, resouceId, list);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        ServiceMediumData rowItem = getItem(position);

        ServiceMediumSpinnerAdapter.viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new ServiceMediumSpinnerAdapter.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_dropdown_item, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.spinnertitle);
            rowview.setTag(holder);
        } else {
            holder = (ServiceMediumSpinnerAdapter.viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(rowItem.getName());
        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
    }

    public String getId(int position){
        return list.get(position).getId();
    }
}
