package com.digitalrupay.adapters.view_holders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.digitalrupay.R;
import com.digitalrupay.datamodels.TodayCollectionEmployeeDataModel;

/**
 * Created by Santosh on 7/28/2016.
 */
public class EmployeeViewHolder extends ParentViewHolder {
    public TextView tvEmployeeName, tvCollectionAmt;
    View view;

    public EmployeeViewHolder(View v) {
        super(v);

        this.view = v;
        tvEmployeeName = (TextView) v.findViewById(R.id.tvEmployeeName);
        tvCollectionAmt = (TextView) v.findViewById(R.id.tvCollectionAmt);

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

    public void bind(TodayCollectionEmployeeDataModel employeeDataModel) {
        tvEmployeeName.setText(employeeDataModel.getEmp_name());
        tvCollectionAmt.setText("Rs." + employeeDataModel.getPaid_amt() + "/-");
    }
}
