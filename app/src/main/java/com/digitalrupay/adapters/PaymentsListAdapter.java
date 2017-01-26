package com.digitalrupay.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalrupay.R;
import com.digitalrupay.adapters.view_holders.PaymentHistoryHolder;
import com.digitalrupay.adapters.view_holders.PaymentsListHolder;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.PaymentHistoryDataModel;

import java.util.ArrayList;

/**
 * Created by Audlink_sri on 12/27/2016.
 */

public class PaymentsListAdapter extends RecyclerView.Adapter<PaymentsListHolder> {

    private Context context;
    private ArrayList<CustomerDataModel> CustomerDataModel;
    private View.OnClickListener listener;
    private LayoutInflater mInflator;
    public PaymentsListAdapter(Context con, ArrayList<CustomerDataModel> customerDataModel) {
        this.context = con;
        this.CustomerDataModel = customerDataModel;
        mInflator = LayoutInflater.from(context);
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public PaymentsListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentAnimalView = mInflator.inflate(R.layout.adapter_payment_list, parent, false);
        return new PaymentsListHolder(parentAnimalView,context);
    }

    @Override
    public void onBindViewHolder(PaymentsListHolder holder, int position) {
        PaymentsListHolder complaintsListHolder = (PaymentsListHolder) holder;
        complaintsListHolder.setComplaintsData(CustomerDataModel.get(position));
    }

    @Override
    public int getItemCount() {
        return CustomerDataModel.size();
    }
}