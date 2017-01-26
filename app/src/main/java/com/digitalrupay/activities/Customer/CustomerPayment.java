package com.digitalrupay.activities.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.activities.BaseActivity;
import com.digitalrupay.activities.PaymentSuccessActivity;
import com.digitalrupay.datamodels.CustomerModel;
import com.digitalrupay.datamodels.CustomerPaymentSuccessModel;
import com.digitalrupay.datamodels.CustomerPendingAmountModel;
import com.digitalrupay.datamodels.MaintainceModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.digitalrupay.payment.OnlinePaymentActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.digitalrupay.network.WsUrlConstants.CustloginTypes;

/**
 * Created by Audlink_sri on 12/28/2016.
 */

public class CustomerPayment extends BaseActivity implements AdapterView.OnItemSelectedListener,AsyncRequest.OnAsyncRequestComplete{
    public TextView tvName, tvAddress, tvMobileNumber, tvCurrentDue, tvPreviousDue, tvTotal,tvAmountDue,tvNextPaymentDate;
    String custNumber,custId,pendingAmt,totalAmtDue,amount,transactionType;
    EditText edtAmount;
    Spinner spiPaymentFor,transactionTypes;
    FrameLayout fl_payment,fl_ok;
    LinearLayout llTransactionType,layspiPaymentFor;
    int serviceRequest;
    public ArrayList<String> strings=new ArrayList<String>();
    MaintainceModel maintainceModel=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_payment);
        layspiPaymentFor = (LinearLayout) findViewById(R.id.layspiPaymentFor);
        tvName = (TextView) findViewById(R.id.txtName);
        tvAddress = (TextView) findViewById(R.id.txtAddress);
        tvMobileNumber = (TextView) findViewById(R.id.txtMobileNumber);
        tvCurrentDue = (TextView) findViewById(R.id.txtCurrentDue);
        tvPreviousDue = (TextView) findViewById(R.id.txtPrevMonthDue);
        tvTotal = (TextView) findViewById(R.id.txtTotalDue);
        tvAmountDue = (TextView) findViewById(R.id.txtAmountDue);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        transactionTypes = (Spinner) findViewById(R.id.transactionType);
        pendingAmt =getIntent().getExtras().getString("padingamount");
        transactionTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transactionType = (String) parent.getItemAtPosition(position);
                Log.d("transaction type", transactionType);
                if (transactionType.equalsIgnoreCase("Cheque")) {
                    llTransactionType.setVisibility(View.VISIBLE);
                } else {
                    llTransactionType.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fl_payment = (FrameLayout) findViewById(R.id.fl_payment);
        fl_ok = (FrameLayout) findViewById(R.id.fl_ok);
        CustomerModel customerData = SessionData.getCustomerLoginResult();
        if (customerData != null) {
            custNumber = customerData.getcustom_customer_no();
            custId = customerData.getcust_id();
            String firstName = "", lastName = "", customerNumber = "";
            if (customerData.getfirst_name() != null) {
                firstName = customerData.getfirst_name().trim();
            }
            if (customerData.getlast_name() != null) {
                lastName = customerData.getlast_name().trim();
            }
            if (customerData.getcustom_customer_no() != null) {
                customerNumber = customerData.getcustom_customer_no();
            }
            tvName.setText(firstName + " " + lastName + " - (" + customerNumber + ")");
            String add2 = customerData.getaddr2();
            if (add2 != null) {
                tvAddress.setText(customerData.getaddr1() + ", \n" + customerData.getaddr2());
            } else {
                tvAddress.setText(customerData.getaddr1());
            }
            tvMobileNumber.setText(customerData.getmobile_no());
            if (custloginType.equals(CustloginTypes[1])) {
                layspiPaymentFor.setVisibility(View.VISIBLE);
                serviceRequest=1;
                AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
                asyncRequest.execute(WsUrlConstants.events_info);
            }else {
                if (pendingAmt != null) {
                    tvAmountDue.setText("Rs." + pendingAmt + "/-");
                } else {
                    tvAmountDue.setText("Rs.0/-");
                    pendingAmt = "0";
                }
                if (!pendingAmt.contains("-"))
                    edtAmount.setText(pendingAmt);
                    totalAmtDue = pendingAmt;
            }
        }
        fl_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidRequest = false;
                amount = edtAmount.getText().toString().trim();
                    AlertDialog paymentAlertDialog = new AlertDialog.Builder(CustomerPayment.this).setTitle("Payment")
                            .setMessage("Are you sure want to pay the bill?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent paymentActivity = new Intent(CustomerPayment.this, OnlinePaymentActivity.class);
                                    Bundle bundle = new Bundle();
                                    paymentActivity.putExtra(WsUrlConstants.CUSTLOGIN_TYPE, custloginType);
                                    bundle.putString("amount",amount);
                                    paymentActivity.putExtras(bundle);
                                    startActivity(paymentActivity);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void asyncResponse(String response) {
        parseCustomerDetails1(response);
    }

    private void parseCustomerDetails1(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            Iterator<String> keys = customersObj.keys();
            String message = customersObj.getString("message");
            String text = customersObj.getString("text");
        if(serviceRequest == 2){
                try {
                    strings.add("Maintenance");
                    if (serviceRequest == 1) {
                        if (message.equalsIgnoreCase("success")) {
                            while (keys.hasNext()) {
                                String key = keys.next();
                                if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                    maintainceModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                            new TypeToken<MaintainceModel>() {
                                            }.getType());
                                    setCustomerData1(maintainceModel);
                                }
                            }
                            ArrayAdapter<String> spiPaymentForspi=new ArrayAdapter<String>(CustomerPayment.this,android.R.layout.simple_expandable_list_item_1,strings);
                            spiPaymentFor.setAdapter(spiPaymentForspi);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setCustomerData1(MaintainceModel DataModel) {
        String ecat_name=DataModel.getEcat_name();
        strings.add(ecat_name);
    }
}
