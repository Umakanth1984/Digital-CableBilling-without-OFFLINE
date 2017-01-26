package com.digitalrupay.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.activities.CustomerPaymentActivity;
import com.digitalrupay.adapters.view_holders.CustomerListHolder;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.interfaces.CommunicationListener;
import com.digitalrupay.network.WsUrlConstants;

/**
 * Created by Santosh on 10/1/2016.
 */

public class CustomersFragment extends Fragment implements View.OnClickListener {

    CommunicationListener communicationListener;
    CustomerListHolder customerListHolder;
    TextView tvError;
    RelativeLayout rlCustomer;
    CustomerDataModel customerDataModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicationListener = (CommunicationListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customers, container, false);

        rlCustomer = (RelativeLayout) view.findViewById(R.id.customer_item);
        tvError = (TextView) view.findViewById(R.id.errMsg);
        customerListHolder = new CustomerListHolder(view, this);
        final EditText searchCustomer = (EditText) view.findViewById(R.id.searchCustomer);
        searchCustomer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchString = v.getText().toString().trim();
                    Log.d("search string", searchString);
                    if (searchCustomer != null && searchCustomer.length() > 0) {
                        tvError.setVisibility(View.GONE);
                        communicationListener.searchCustomer(searchString);
                    } else {
                        Toast.makeText(getActivity(), "Please Enter Customer ID or Mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        return view;
    }

    public void setCustomerData(CustomerDataModel customerData, String message, String errorText) {
        if (customerData != null) {
            customerDataModel = customerData;
            rlCustomer.setVisibility(View.VISIBLE);
            customerListHolder.setCustomerData(customerData);
        } else {
            rlCustomer.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(errorText);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_payment:
                redirectToCustomerPaymentActivity();
                break;
        }
    }

    void redirectToCustomerPaymentActivity() {
        Intent customerPayment = new Intent(getActivity(), CustomerPaymentActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(WsUrlConstants.CUSTOMER_DATA, customerDataModel);
        customerPayment.putExtras(b);
        customerPayment.putExtra(WsUrlConstants.EMPLOYEE_ID, getActivity().getIntent().getStringExtra(WsUrlConstants.EMPLOYEE_ID));
        startActivity(customerPayment);
    }
}
