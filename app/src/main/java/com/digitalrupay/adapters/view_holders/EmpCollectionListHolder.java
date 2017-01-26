package com.digitalrupay.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.digitalrupay.R;
import com.digitalrupay.datamodels.ComplaintsDataModel;
import com.digitalrupay.datamodels.EmpCollectionDataModel;
import com.digitalrupay.datamodels.TodayCollectionEmployeeDataModel;

/**
 * Created by Audlink_sri on 12/26/2016.
 */

public class EmpCollectionListHolder extends RecyclerView.ViewHolder {

    public TextView tvEmployeeName, tvCollectionAmt;
    ImageView ivArrow;
    View view;

    public EmpCollectionListHolder(View v, View.OnClickListener listener) {
        super(v);
        this.view = v;
        tvEmployeeName = (TextView) v.findViewById(R.id.tvEmployeeName);
        tvCollectionAmt = (TextView) v.findViewById(R.id.tvCollectionAmt);
    }

    public void setComplaintsData(EmpCollectionDataModel complaintsDataModel) {
        view.setTag(complaintsDataModel);
        tvEmployeeName.setText(complaintsDataModel.getGroup_name());
        tvCollectionAmt.setText("Rs." + complaintsDataModel.getTotal() + "/-");
    }
}