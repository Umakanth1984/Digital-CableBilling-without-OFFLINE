package com.digitalrupay.activities.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.activities.BaseActivity;
import com.digitalrupay.activities.ChangeMobileNumberActivity;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.CustomerModel;
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

/**
 * Created by Audlink_sri on 12/11/2016.
 */

public class UpdateMobile extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete {
    String customermobile,STBnumber,customerID;
    EditText et_customermobile,et_STBnumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatemobile);
        et_customermobile = (EditText) findViewById(R.id.cut_edt_change_mobile_number);
        et_STBnumber = (EditText) findViewById(R.id.edt_STB_Number);
        CustomerModel customerModel= SessionData.getCustomerLoginResult();
        customermobile=customerModel.getmobile_no();
        STBnumber=customerModel.getstb_no();
        customerID=customerModel.getcust_id();
        et_customermobile.setText(customermobile);
        et_STBnumber.setText(STBnumber);
    }
    public void updateMobile(View view){
       String getcustomermobile = et_customermobile.getText().toString();
        String getSTBnumber =et_STBnumber.getText().toString();
        if (getSTBnumber != null && getcustomermobile.length() > 0) {
            AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Updating Mobile Number..");
            asyncRequest.execute(WsUrlConstants.Customerupdate.replace(WsUrlConstants.Customerid, customerID).replace(WsUrlConstants.CustmobileNumber, getcustomermobile));
        } else {
            Toast.makeText(UpdateMobile.this, "Please Enter Empty Fields", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void asyncResponse(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            CustomerDataModel customerDataModel = null;
            Iterator<String> keys = customersObj.keys();
            String message = customersObj.getString("message");
            String text = customersObj.getString("text");
            if (message.equalsIgnoreCase("success")) {
                Toast.makeText(UpdateMobile.this, "Updates are complete successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
