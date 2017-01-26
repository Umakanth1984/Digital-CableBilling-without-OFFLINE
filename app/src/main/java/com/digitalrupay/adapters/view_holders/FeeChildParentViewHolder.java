package com.digitalrupay.adapters.view_holders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.digitalrupay.R;
import com.digitalrupay.datamodels.FeeDataModel;

/**
 * Created by Santosh on 7/28/2016.
 */
public class FeeChildParentViewHolder extends ChildViewHolder {
    public TextView tvFeeTermMonth, tvTotalFee;
    View view;

    public FeeChildParentViewHolder(View v, View.OnClickListener listener) {
        super(v);

        this.view = v;
        tvFeeTermMonth = (TextView) v.findViewById(R.id.txtFeeTermMonth);
        tvTotalFee = (TextView) v.findViewById(R.id.txtFeeTotal);

        itemView.setOnClickListener(listener);
    }

    public void bind(FeeDataModel feeDataModel) {
        itemView.setTag(feeDataModel.getId());
        tvFeeTermMonth.setText("Term Month: " + feeDataModel.getTerm_month());
        tvTotalFee.setText("Total Fee: " + feeDataModel.getTotal() + "");
    }
}
