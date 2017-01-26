package com.digitalrupay.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.adapters.PaymentsListAdapter;
import com.digitalrupay.adapters.view_holders.PaymentsListHolder;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.CustomerPaymentSuccessModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.MaintainceModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.AsyncRequestEx;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Santosh on 10/6/2016.
 */

public class PaymentsActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete,AdapterView.OnItemSelectedListener,AsyncRequestEx.OnAsyncRequestComplete{
    TextView tvError;
    EditText searchCustomer;

    int serviceRequest;
    String employeeId;
    ImageView ivSearch;
    CustomerDataModel customerDataModel = null;
    PaymentsListAdapter complaintsListAdapter;
    RecyclerView PaymentList;
    ArrayList<CustomerDataModel> CustomerDataModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customers);
        setTitle("Customer Payment", true);
        PaymentList = (RecyclerView)findViewById(R.id.PaymentList);
        tvError = (TextView) findViewById(R.id.errMsg);

        ivSearch = (ImageView) findViewById(R.id.imgSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentsListHolder.strings.clear();
                CustomerDataModelArrayList.clear();
                String searchString = searchCustomer.getText().toString().trim();
                if (searchCustomer != null && searchCustomer.length() > 0) {
                    tvError.setVisibility(View.GONE);
                    searchCustomer(searchString);
                } else {
                    Toast.makeText(PaymentsActivity.this, "Please Enter Customer ID or Mobile number", Toast.LENGTH_SHORT).show();
                }
                hideKeyBoard(v);
            }
        });
        searchCustomer = (EditText) findViewById(R.id.searchCustomer);
        searchCustomer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchString = v.getText().toString().trim();
                    Log.d("search string", searchString);
                    if (searchCustomer != null && searchCustomer.length() > 0) {
                        tvError.setVisibility(View.GONE);
                        searchCustomer(searchString);
                    } else {
                        Toast.makeText(PaymentsActivity.this, "Please Enter Customer ID or Mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        EmployeeDataModel employeeDataModel;
        if(SessionData.getSessionDataInstance().getApartmentLoginResult()!= null) {
            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
        }else {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
        }
        employeeId = employeeDataModel.getEmp_id();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void searchCustomer(String customerID) {
        serviceRequest = 1;
        Log.d("search customer: ", customerID);
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.customerDetailsUrl.replace(WsUrlConstants.CUSTOMER_ID, customerID).replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
       if(loginType.equals(loginTypes[4])) {
           AsyncRequestEx asyncRequest1 = new AsyncRequestEx(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
           asyncRequest1.execute(WsUrlConstants.events_info);
       }
    }
    @Override
    public void asyncResponse1(String response) {
        parseCustomerDetails1(response);
    }
    private void parseCustomerDetails1(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            Iterator<String> keys = customersObj.keys();
            String message = customersObj.getString("message");
            String text = customersObj.getString("text");
            PaymentsListHolder.strings.add("Maintenance");
            if (serviceRequest == 1) {
                if (message.equalsIgnoreCase("success")) {
                    MaintainceModel maintainceModel=new MaintainceModel();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            maintainceModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                    new TypeToken<MaintainceModel>() {
                                    }.getType());
                            setCustomerData1(maintainceModel);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setCustomerData1(MaintainceModel DataModel) {
        String ecat_name=DataModel.getEcat_name();
        PaymentsListHolder.strings.add(ecat_name);
    }
    @Override
    public void asyncResponse(String response) {
        parseCustomerDetails(response);
    }
    void parseCustomerDetails(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            Iterator<String> keys = customersObj.keys();
            String message = customersObj.getString("message");
            String text = customersObj.getString("text");
            if (serviceRequest == 1) {
                if (message.equalsIgnoreCase("success")) {
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            customerDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                    new TypeToken<CustomerDataModel>() {
                                    }.getType());
                            CustomerDataModelArrayList.add(customerDataModel);
                        }
                    }
                }
                    setCustomerData();
            } else if (serviceRequest == 2) {
                if (message.equalsIgnoreCase("success")) {
                    Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
                    CustomerPaymentSuccessModel customerPaymentSuccessModel = null;
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            customerPaymentSuccessModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                    new TypeToken<CustomerPaymentSuccessModel>() {
                                    }.getType());
                        }
                    }
                    Intent paymentActivity = new Intent(this, PaymentSuccessActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(WsUrlConstants.PAYMENT_DATA, customerPaymentSuccessModel);
                    bundle.putSerializable(WsUrlConstants.CUSTOMER_DATA, customerDataModel);
                    bundle.putString(WsUrlConstants.LOGIN_TYPE, loginType);
                    paymentActivity.putExtras(bundle);
                    startActivityForResult(paymentActivity, 1000);
                } else {
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                }
            } else {
                if (message.equalsIgnoreCase("success")) {
                    AlertDialog messageDialog = new AlertDialog.Builder(this).setTitle("Success")
                            .setMessage("Next Payment Date Updated Successfully")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
//                                    resetAllViews();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info
                            ).setCancelable(false).show();
                }
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCustomerData() {
        complaintsListAdapter = new PaymentsListAdapter(this, CustomerDataModelArrayList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        PaymentList.setLayoutManager(llm);
        PaymentList.setAdapter(complaintsListAdapter);

    }
    public void sendPayment(final String custId, final String amount, final String trxnType, final String chequeNumber, final String bankName, final String branchName, final String date) {
                        serviceRequest = 2;
                        AsyncRequest asyncRequest = new AsyncRequest(PaymentsActivity.this, "GET", new ArrayList<NameValuePair>(), "Processing Request..");
                        asyncRequest.execute(WsUrlConstants.cablePaymentUrl.replace(WsUrlConstants.EMPLOYEE_ID, employeeId)
                                .replace(WsUrlConstants.CUSTOMER_ID, custId)
                                .replace(WsUrlConstants.AMOUNT, amount)
                                .replace(WsUrlConstants.TRANSACTION_TYPE, trxnType)
                                .replace(WsUrlConstants.CHEQUE_NUMBER, chequeNumber)
                                .replace(WsUrlConstants.BANK_NAME, bankName)
                                .replace(WsUrlConstants.BRANCH_NAME, branchName)
                                .replace(WsUrlConstants.TRANSACTION_DATE,date)
                                .replace(WsUrlConstants.REMARKS, "Remarks"));
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resetAllViews();
    }
    void resetAllViews() {
        searchCustomer.setText("");
        CustomerDataModelArrayList.clear();
        complaintsListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resetAllViews();
    }
}
