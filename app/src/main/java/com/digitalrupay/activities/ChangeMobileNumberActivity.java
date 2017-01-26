package com.digitalrupay.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
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
 * Created by Santosh on 10/6/2016.
 */

public class ChangeMobileNumberActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete {

    LinearLayout llCustomerInfo;
    EditText edtMobileNumber, searchCustomer,edtmacid,edtstbno,edtcardno,edtpendingamount;
    TextView tvError, tvName, tvAddress, tvMobileNumber;
    String customerID, employeeId;
    boolean isCustomer;
    ImageView ivSearch;
    FrameLayout fl_change_number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile_number);

        setTitle("Update Mobile", true);
        tvName = (TextView) findViewById(R.id.txtName);
        tvAddress = (TextView) findViewById(R.id.txtAddress);
        tvMobileNumber = (TextView) findViewById(R.id.txtMobileNumber);
        tvError = (TextView) findViewById(R.id.errMsg);
        llCustomerInfo = (LinearLayout) findViewById(R.id.ll_customer_info);
        fl_change_number=(FrameLayout)findViewById(R.id.fl_change_number);
        llCustomerInfo.setVisibility(View.GONE);
        fl_change_number.setVisibility(View.GONE);

        edtMobileNumber = (EditText) findViewById(R.id.edt_change_mobile_number);
        edtmacid = (EditText) findViewById(R.id.edt_change_mac_id);
        edtstbno = (EditText) findViewById(R.id.edt_change_stb_no);
        edtcardno = (EditText) findViewById(R.id.edt_change_card_no);
        edtpendingamount=(EditText)findViewById(R.id.edt_pending_amount);
        ivSearch = (ImageView) findViewById(R.id.imgSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = searchCustomer.getText().toString().trim();
                Log.d("search string", searchString);
                if (searchCustomer != null && searchCustomer.length() > 0) {
                    tvError.setVisibility(View.GONE);
                    searchCustomer(searchString);
                } else {
                    Toast.makeText(ChangeMobileNumberActivity.this, "Please Enter Customer ID or Mobile number", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ChangeMobileNumberActivity.this, "Please Enter Customer ID or Mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        EmployeeDataModel employeeDataModel = null;
        if(loginType.equals(loginTypes[4])) {
            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
        }else if(loginType.equals(loginTypes[2])){
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
        }
        employeeId = employeeDataModel.getEmp_id();
    }

    public void updateMobile(View view) {
        String mobileNumber = edtMobileNumber.getText().toString().trim();
        String macNumber = edtmacid.getText().toString().trim();
        String stbNumber = edtstbno.getText().toString().trim();
        String cardNumber = edtcardno.getText().toString().trim();
        String pendingamount=edtpendingamount.getText().toString().trim();
        if (mobileNumber != null && mobileNumber.length() > 0) {
            AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Updating Mobile Number..");
            asyncRequest.execute(WsUrlConstants.updateUserMobileUrl.replace(WsUrlConstants.CUSTOMER_ID, customerID).replace(WsUrlConstants.MOBILE_NUMBER, mobileNumber)
            .replace(WsUrlConstants.MAC_ID, macNumber).replace(WsUrlConstants.STB_NO, stbNumber).replace(WsUrlConstants.CARD_NO, cardNumber).replace(WsUrlConstants.PendingAmount,pendingamount));
        } else {
            Toast.makeText(ChangeMobileNumberActivity.this, "Please Enter Mobile number", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchCustomer(String customerID) {
        isCustomer = true;
        Log.d("search customer: ", customerID);
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.customerDetailsUrl.replace(WsUrlConstants.CUSTOMER_ID, customerID).replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    @Override
    public void asyncResponse(String response) {
        parseCustomerDetails(response);
    }

    void parseCustomerDetails(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            CustomerDataModel customerDataModel = null;
            Iterator<String> keys = customersObj.keys();
            String message = customersObj.getString("message");
            String text = customersObj.getString("text");
            if (message.equalsIgnoreCase("success")) {
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                        if (isCustomer) {
                            customerDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                    new TypeToken<CustomerDataModel>() {
                                    }.getType());
                        } else {

                        }
                    }
                }
            }

            if (customerDataModel != null) {
                llCustomerInfo.setVisibility(View.VISIBLE);
                fl_change_number.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
                setCustomerData(customerDataModel);
            } else {
                llCustomerInfo.setVisibility(View.GONE);
                fl_change_number.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(text);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setCustomerData(CustomerDataModel customerData) {
        customerID = customerData.getCustom_customer_no();
        String firstName = "", lastName = "", customerNumber = "";
        if (customerData.getFirst_name() != null) {
            firstName = customerData.getFirst_name().trim();
        }
        if (customerData.getLast_name() != null) {
            lastName = customerData.getLast_name().trim();
        }
        if (customerData.getCustom_customer_no() != null) {
            customerNumber = customerData.getCustom_customer_no();
        }
        tvName.setText(firstName + " " + lastName + " - (" + customerNumber + ")");
        String add2=customerData.getAddr2();
        if(add2!=null) {
            tvAddress.setText(customerData.getAddr1() + "," + customerData.getAddr2() + ", " + customerData.getCity());
        }else {
            tvAddress.setText(customerData.getAddr1() + ", " + customerData.getCity());
        }
        tvMobileNumber.setText(customerData.getMobile_no());
        edtMobileNumber.setText(customerData.getMobile_no());
        edtmacid.setText(customerData.getMac_id());
        edtstbno.setText(customerData.getStb_no());
        edtcardno.setText(customerData.getCard_no());
        edtpendingamount.setText(customerData.getPending_amount());
    }

}
