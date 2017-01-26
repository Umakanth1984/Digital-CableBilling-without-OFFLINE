package com.digitalrupay.adapters.view_holders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.digitalrupay.R;
import com.digitalrupay.datamodels.GroupDataModel;

/**
 * Created by Santosh on 7/28/2016.
 */
public class GroupViewHolder extends ChildViewHolder {
    public TextView tvGroupName, tvCollectionAmt;
    View view;

    public GroupViewHolder(View v) {
        super(v);

        this.view = v;
        tvGroupName = (TextView) v.findViewById(R.id.tvGroupName);
        tvCollectionAmt = (TextView) v.findViewById(R.id.tvGroupCollectionAmt);
    }

    public void bind(GroupDataModel groupDataModel) {
        tvGroupName.setText(groupDataModel.getGroup_name());
        tvCollectionAmt.setText("Rs." + groupDataModel.getPaid_amt() + "/-");
    }
}
