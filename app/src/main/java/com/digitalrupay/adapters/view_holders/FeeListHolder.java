package com.digitalrupay.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.digitalrupay.R;
import com.digitalrupay.datamodels.WardDataModel;

/**
 * Created by Santosh on 7/5/2016.
 */
public class FeeListHolder extends RecyclerView.ViewHolder {

    public TextView tvName, tvAdmnNumber, tvSection, tvStandard, tvBranchName, tvFee;
    View view;

    public FeeListHolder(View v, View.OnClickListener listener) {
        super(v);
        this.view = v;
        tvName = (TextView) v.findViewById(R.id.txtName);
        tvAdmnNumber = (TextView) v.findViewById(R.id.txtAdmnNumber);
        tvSection = (TextView) v.findViewById(R.id.txtSection);
        tvStandard = (TextView) v.findViewById(R.id.txtStandard);
        tvBranchName = (TextView) v.findViewById(R.id.txtBranchName);

        view.setOnClickListener(listener);
    }

    public void setWardData(WardDataModel wardData) {
        view.setTag(wardData.getFee().get(0).getId());
        tvName.setText("Name: " + wardData.getStudentsDataModel().getName());
        tvAdmnNumber.setText("Admission Number: " + wardData.getStudentsDataModel().getAdmission_number());
        tvSection.setText("Section: " + wardData.getSection().getName());
        tvStandard.setText("Standard: " + wardData.getStandard().getName());
        tvBranchName.setText("Branch Name: " + wardData.getBranch().getName());
        tvFee.setText("Total Fee: " + wardData.getFee().get(0).getTotal() + "");
    }
}
