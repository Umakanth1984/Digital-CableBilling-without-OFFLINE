package com.digitalrupay.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalrupay.R;
import com.digitalrupay.adapters.view_holders.CustomerListHolder;
import com.digitalrupay.datamodels.CustomerDataModel;

import java.util.ArrayList;

/**
 * Created by Santosh on 10/1/2016.
 */

public class CustomersListAdapter extends RecyclerView.Adapter<CustomerListHolder> {

    private Context context;
    private ArrayList<CustomerDataModel> customerDataModelArrayList;
    private View.OnClickListener listener;
    private LayoutInflater mInflator;

    public CustomersListAdapter(Context context, ArrayList<CustomerDataModel> customerDataModelArrayList) {
        this.context = context;
        this.customerDataModelArrayList = customerDataModelArrayList;
        mInflator = LayoutInflater.from(context);
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public CustomerListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentAnimalView = mInflator.inflate(R.layout.customer_item, parent, false);
        return new CustomerListHolder(parentAnimalView, listener);
    }

    @Override
    public void onBindViewHolder(CustomerListHolder holder, int position) {
        CustomerListHolder customerListHolder = (CustomerListHolder) holder;
        customerListHolder.setCustomerData(customerDataModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return customerDataModelArrayList.size();
    }
}
