package com.digitalrupay.activities.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.activities.BaseActivity;
import com.digitalrupay.datamodels.CustomerModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.digitalrupay.network.WsUrlConstants.CustloginTypes;

/**
 * Created by Audlink_sri on 12/6/2016.
 */

public class CustomerLogin extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete{
    TextInputLayout userName, password, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(WsUrlConstants.CUSTLOGIN_TYPE, custloginType);
        userName = (TextInputLayout) findViewById(R.id.text_input_layout_user_name);
        password = (TextInputLayout) findViewById(R.id.text_input_layout_password);
        email = (TextInputLayout) findViewById(R.id.text_input_layout_email);
        userName.setVisibility(View.GONE);
    }
    public void checkLoginCredentials(View view) {
        String username = email.getEditText().getText().toString().trim();
        String pwd = password.getEditText().getText().toString().trim();
                if (checkNetworkConnection()) {
                    AsyncRequest getPosts = new AsyncRequest(this, "GET", null, "Validating User..");
                    getPosts.execute(WsUrlConstants.CustomerLogin.replace(WsUrlConstants.EMAIL, username).replace(WsUrlConstants.PASSWORD, pwd));
                }
    }
    @Override
    public void asyncResponse(String response) {
        try{
            JSONObject custObj=new JSONObject(response);
            CustomerModel customerModel;
            Iterator<String> keys = custObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                    customerModel = new Gson().fromJson(custObj.getJSONObject(key).toString(), new TypeToken<CustomerModel>() {}.getType());
                    SessionData.getSessionDataInstance().saveCustomerLoginResponse(customerModel);
                    Intent intent=new Intent(CustomerLogin.this,CustomerHome.class);
                    intent.putExtra(WsUrlConstants.CUSTLOGIN_TYPE, custloginType);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
