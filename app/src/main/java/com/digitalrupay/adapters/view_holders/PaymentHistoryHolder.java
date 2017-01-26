package com.digitalrupay.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalrupay.R;
import com.digitalrupay.datamodels.ComplaintsDataModel;
import com.digitalrupay.datamodels.PaymentHistoryDataModel;

/**
 * Created by Audlink_sri on 12/12/2016.
 */

public class PaymentHistoryHolder extends RecyclerView.ViewHolder {

    public TextView tvCustomerName, tvCategory, tvComplaintMsg, tvStatus, tvRemarks;
    ImageView ivArrow;
    View view;

    public PaymentHistoryHolder(View v, View.OnClickListener listener) {
        super(v);
        this.view = v;
        tvCustomerName = (TextView) v.findViewById(R.id.txtCustomerName);
        tvCategory = (TextView) v.findViewById(R.id.txtCategory);
        tvComplaintMsg = (TextView) v.findViewById(R.id.txtComplaintMsg);
        tvStatus = (TextView) v.findViewById(R.id.txtStatus);
        tvRemarks = (TextView) v.findViewById(R.id.txtRemarks);

        view.setOnClickListener(listener);
    }

    public void setComplaintsData(PaymentHistoryDataModel complaintsDataModel) {
        view.setTag(complaintsDataModel);
        String current_due = "", monthly_bill = "", amount_paid = "";
        String dateCreated="";
        if (complaintsDataModel.getdateCreated() != null) {
            dateCreated = complaintsDataModel.getdateCreated().trim();
        }
        if (complaintsDataModel.getmonthly_bill() != null) {
            monthly_bill = complaintsDataModel.getmonthly_bill().trim();
        }
        if (complaintsDataModel.getamount_paid() != null) {
            amount_paid = complaintsDataModel.getamount_paid();
        }
        if (complaintsDataModel.getcurrent_due() != null) {
            current_due = complaintsDataModel.getcurrent_due();
        }
        tvCustomerName.setText(dateCreated);
        tvComplaintMsg.setText("Amount Paid : "+amount_paid);
        tvCategory.setText("Invoice: " + complaintsDataModel.getinvoice());
        String status = complaintsDataModel.gettransaction_type();
        if (status != null) {
            if (status.equalsIgnoreCase("1")) {
                tvStatus.setText("Transaction Type : Cash");
            } else if (status.equalsIgnoreCase("0")) {
                tvStatus.setText("Transaction Type : Online Banking");
            }else{
                tvStatus.setText("Transaction Type : Online Banking");
            }
        }
//        tvRemarks.setText("Payment for: " + complaintsDataModel.getpayment_for());
    }
}
