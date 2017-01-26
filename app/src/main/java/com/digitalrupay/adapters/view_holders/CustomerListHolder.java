package com.digitalrupay.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalrupay.R;
import com.digitalrupay.datamodels.CustomerDataModel;

/**
 * Created by Santosh on 10/1/2016.
 */

public class CustomerListHolder extends RecyclerView.ViewHolder {

    public TextView tvName, tvAddress, tvAdmnNumber, tvSection, tvStandard, tvBranchName, tvPaymentName;
    ImageView ivArrow;
    View view;
    FrameLayout flPayment;

    public CustomerListHolder(View v, View.OnClickListener listener) {
        super(v);
        this.view = v;
        tvName = (TextView) v.findViewById(R.id.txtName);
        tvAddress = (TextView) v.findViewById(R.id.txtAddress);
        tvAdmnNumber = (TextView) v.findViewById(R.id.txtMobileNumber);
        tvSection = (TextView) v.findViewById(R.id.txtSection);
        tvStandard = (TextView) v.findViewById(R.id.txtStandard);
        tvBranchName = (TextView) v.findViewById(R.id.txtBranchName);
        tvPaymentName = (TextView) v.findViewById(R.id.tvPaymentName);
        ivArrow = (ImageView) v.findViewById(R.id.imgArrow);

        flPayment = (FrameLayout) v.findViewById(R.id.fl_payment);
        flPayment.setOnClickListener(listener);
    }

    public void setCustomerData(CustomerDataModel customerData) {
        tvName.setText(customerData.getFirst_name() + " " + customerData.getLast_name() + " - (" + customerData.getCustom_customer_no() + ")");
        tvAddress.setText(customerData.getAddr2() + ", " + customerData.getCity());
        tvAdmnNumber.setText(customerData.getMobile_no());
        String pendingAmt = customerData.getPending_amount();
        if (pendingAmt != null) {
            tvSection.setText("Rs." + pendingAmt + "/-");
        } else {
            tvSection.setText("Rs.0/-");
        }
    }
}
