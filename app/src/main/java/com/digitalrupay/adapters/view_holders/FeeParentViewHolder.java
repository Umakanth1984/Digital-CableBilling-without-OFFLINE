package com.digitalrupay.adapters.view_holders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.digitalrupay.R;
import com.digitalrupay.datamodels.WardDataModel;

/**
 * Created by Santosh on 7/28/2016.
 */
public class FeeParentViewHolder extends ParentViewHolder {
    public TextView tvName, tvAdmnNumber, tvSection, tvStandard, tvBranchName;
    View view;

    public FeeParentViewHolder(View v) {
        super(v);

        this.view = v;
        tvName = (TextView) v.findViewById(R.id.txtName);
        tvAdmnNumber = (TextView) v.findViewById(R.id.txtAdmnNumber);
        tvSection = (TextView) v.findViewById(R.id.txtSection);
        tvStandard = (TextView) v.findViewById(R.id.txtStandard);
        tvBranchName = (TextView) v.findViewById(R.id.txtBranchName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    collapseView();
                } else {
                    expandView();
                }
            }
        });
    }

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }

    public void bind(WardDataModel wardData) {
        tvName.setText("Name: " + wardData.getStudentsDataModel().getName());
        tvAdmnNumber.setText("Admission Number: " + wardData.getStudentsDataModel().getAdmission_number());
        tvSection.setText("Section: " + wardData.getSection().getName());
        tvStandard.setText("Standard: " + wardData.getStandard().getName());
        tvBranchName.setText("Branch Name: " + wardData.getBranch().getName());
    }
}
