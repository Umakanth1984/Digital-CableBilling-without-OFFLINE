package com.digitalrupay.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalrupay.R;
import com.digitalrupay.adapters.view_holders.ComplaintsListHolder;
import com.digitalrupay.adapters.view_holders.PaymentHistoryHolder;
import com.digitalrupay.datamodels.ComplaintsDataModel;
import com.digitalrupay.datamodels.PaymentHistoryDataModel;

import java.util.ArrayList;

/**
 * Created by Audlink_sri on 12/12/2016.
 */

public class PaymentHistoryAdapter  extends RecyclerView.Adapter<PaymentHistoryHolder> {

    private Context context;
    private ArrayList<PaymentHistoryDataModel> complaintsDataModelArrayList;
    private View.OnClickListener listener;
    private LayoutInflater mInflator;

    public PaymentHistoryAdapter(Context context, ArrayList<PaymentHistoryDataModel> complaintsDataModelArrayList) {
        this.context = context;
        this.complaintsDataModelArrayList = complaintsDataModelArrayList;
        mInflator = LayoutInflater.from(context);
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public PaymentHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentAnimalView = mInflator.inflate(R.layout.complaints_item, parent, false);
        return new PaymentHistoryHolder(parentAnimalView, listener);
    }

    @Override
    public void onBindViewHolder(PaymentHistoryHolder holder, int position) {
        PaymentHistoryHolder complaintsListHolder = (PaymentHistoryHolder) holder;
        complaintsListHolder.setComplaintsData(complaintsDataModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return complaintsDataModelArrayList.size();
    }
}