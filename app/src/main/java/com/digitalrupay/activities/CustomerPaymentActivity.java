package com.digitalrupay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.adapters.view_holders.CustomerListHolder;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.CustomerPaymentSuccessModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Santosh on 10/1/2016.
 */

public class CustomerPaymentActivity extends BaseActivity implements View.OnClickListener, AsyncRequest.OnAsyncRequestComplete {

    LinearLayout llCustomerDtls, llTransactionType;
    RelativeLayout rlCustomerMain;
    AsyncRequest asyncRequest = null;
    String employeeId, transactionType, remarks = "", date = "", bankName = "", branchName = "", chequeNumber = "", nextPaymentDate = "";
    CustomerDataModel customerDataModel;
    Spinner transactionTypes;
    EditText edtAmount, edtChequeNumber, edtBankName, edtBranchName, edtDate, edtRemarks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_payment);

        setTitle("Customer Payment", true);
        Bundle bundle = getIntent().getExtras();
        customerDataModel = (CustomerDataModel) bundle.getSerializable(WsUrlConstants.CUSTOMER_DATA);

        rlCustomerMain = (RelativeLayout) findViewById(R.id.customer_item);
        rlCustomerMain.setVisibility(View.VISIBLE);
        llCustomerDtls = (LinearLayout) findViewById(R.id.llCustProvideDtls);
        llTransactionType = (LinearLayout) findViewById(R.id.llTransactionType);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        edtChequeNumber = (EditText) findViewById(R.id.edtChequeNumber);
        edtBankName = (EditText) findViewById(R.id.edtBank);
        edtBranchName = (EditText) findViewById(R.id.edtBranch);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtRemarks = (EditText) findViewById(R.id.edtRemarks);
        transactionTypes = (Spinner) findViewById(R.id.transactionType);
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

        llCustomerDtls.setVisibility(View.VISIBLE);

        View view = rlCustomerMain;

        CustomerListHolder customerListHolder = new CustomerListHolder(view, this);
        customerListHolder.tvPaymentName.setText("Pay Now");

        customerListHolder.setCustomerData(customerDataModel);
        if(loginType.equals(loginTypes[4])) {
            employeeId = SessionData.getApartmentLoginResult().getEmp_id();
        }else if(loginType.equals(loginTypes[2])) {
            employeeId = SessionData.getEmployeeLoginResult().getEmp_id();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_payment:
                boolean isValidRequest = false;
                String amount = edtAmount.getText().toString().trim();
                if (amount.length() == 0) {
                    amount = customerDataModel.getAmount();
                }
                chequeNumber = edtChequeNumber.getText().toString().trim();
                bankName = edtBankName.getText().toString().trim();
                branchName = edtBranchName.getText().toString().trim();
                date = edtDate.getText().toString().trim();
                remarks = edtRemarks.getText().toString().trim();
                if (transactionType.equalsIgnoreCase("Cash")) {
                    chequeNumber = "";
                    bankName = "";
                    branchName = "";
                    date = "";
                    isValidRequest = true;
                } else {
                    if (chequeNumber != null && chequeNumber.length() > 0) {
                        if (bankName != null && bankName.length() > 0) {
                            if (branchName != null && branchName.length() > 0) {
                                if (date != null && date.length() > 0) {
                                    isValidRequest = true;
                                } else {
                                    Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Please enter branch name", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Please enter bank name", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please enter cheque number", Toast.LENGTH_SHORT).show();
                    }
                }

                if (isValidRequest) {
                    asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Processing Request..");
                    asyncRequest.execute(WsUrlConstants.cablePaymentUrl.replace(WsUrlConstants.EMPLOYEE_ID, employeeId)
                            .replace(WsUrlConstants.CUSTOMER_ID, customerDataModel.getCust_id())
                            .replace(WsUrlConstants.AMOUNT, amount)
                            .replace(WsUrlConstants.TRANSACTION_TYPE, transactionType)
                            .replace(WsUrlConstants.CHEQUE_NUMBER, chequeNumber)
                            .replace(WsUrlConstants.BANK_NAME, bankName)
                            .replace(WsUrlConstants.BRANCH_NAME, branchName)
                            .replace(WsUrlConstants.TRANSACTION_DATE, date)
                            .replace(WsUrlConstants.REMARKS, remarks));
                }
                break;
        }
    }

    @Override
    public void asyncResponse(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            CustomerPaymentSuccessModel customerPaymentSuccessModel = null;
            Iterator<String> keys = customersObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                    customerPaymentSuccessModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                            new TypeToken<CustomerPaymentSuccessModel>() {
                            }.getType());
                }
            }
            String message = customersObj.getString("message");
            String text = customersObj.getString("text");
            if (message.equalsIgnoreCase("success")) {
                Intent paymentActivity = new Intent(this, PaymentSuccessActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(WsUrlConstants.PAYMENT_DATA, customerPaymentSuccessModel);
                paymentActivity.putExtras(bundle);
                startActivity(paymentActivity);
            } else {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
