package com.digitalrupay.activities.Customer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.crittercism.internal.bu;
import com.digitalrupay.R;
import com.digitalrupay.activities.BaseActivity;
import com.digitalrupay.activities.CableBillingActivity;
import com.digitalrupay.activities.ComplaintsActivity;
import com.digitalrupay.datamodels.CustomerModel;
import com.digitalrupay.datamodels.CustomerPendingAmountModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.MaintainceModel;
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
 * Created by Audlink_sri on 12/7/2016.
 */

public class CustomerHome extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete{
    String response;
    TextView tvcustName,tvcustMobile,tv_Pending;
    AsyncRequest asyncRequest;
    ImageView ivLogo;
    int servercode;
    String customerID;
    CustomerPendingAmountModel customerPendingAmountModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerhome);
        tvcustName = (TextView) findViewById(R.id.tvEmpName);
        tvcustMobile = (TextView) findViewById(R.id.tvEmpMobile);
        tv_Pending=(TextView) findViewById(R.id.tv_Pending);
        ivLogo = (ImageView) findViewById(R.id.logo);
        CustomerModel employeeDataModel = SessionData.getCustomerLoginResult();
        tvcustName.setText(employeeDataModel.getfirst_name() + " " + employeeDataModel.getlast_name());
        tvcustMobile.setText("(" + employeeDataModel.getmobile_no() + ")");
        customerID=employeeDataModel.getcust_id();
        getBusinessLogo();
        getPendingAmount();
    }

    private void getPendingAmount() {
        servercode=1;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.CustGetPendingAmount.replace(WsUrlConstants.CUSTOMER_ID,customerID));
    }
    public void getBusinessLogo() {
        servercode=2;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.businessCustomerLogoUrl);
    }
    public void navigateToUpdateMobile(View view){
        Intent complaints = new Intent(this, UpdateMobile.class);
        startActivity(complaints);
    }
    public void navigateToComplaintsHistory(View view){
        Intent complaints = new Intent(this, ComplaintsHistory.class);
        startActivity(complaints);
    }
    public void navigateToPaymentHistory(View view){
        Intent complaints = new Intent(this, PaymentHistory.class);
        startActivity(complaints);
    }
    public void navigateToPayment(View view){
        Intent Payment = new Intent(this, CustomerPayment.class);
        Payment.putExtra(WsUrlConstants.CUSTLOGIN_TYPE, custloginType);
        Payment.putExtra("padingamount",customerPendingAmountModel.getpending_amount());
        startActivity(Payment);
    }
    @Override
    public void asyncResponse(String response) {
        parseCustomerDetails(response);
    }

    void parseCustomerDetails(final String response) {
        try {
            if(servercode==2) {
                JSONObject customersObj = new JSONObject(response);
                JSONObject inOBJ = customersObj.getJSONObject("0");
                String business_name = inOBJ.getString("business_name");
                String address1 = inOBJ.getString("address1");
                String address2 = "";
                if (!inOBJ.isNull("address2")) {
                    address2 = inOBJ.getString("address2");
                }
                String email = "";
                if (!inOBJ.isNull("email")) {
                    email = inOBJ.getString("email");
                }
                String city = inOBJ.getString("city");
                String state = inOBJ.getString("state");
                String mobile = inOBJ.getString("mobile");

                final String businessLogo = customersObj.getString("business_logo");
                JSONObject businessObj = new JSONObject(customersObj.getJSONObject("0").toString());
                WsUrlConstants.SERVICES_name = businessObj.getString("business_name");
                WsUrlConstants.SERVICE_MOBILE_NUMBER = businessObj.getString("mobile");
                WsUrlConstants.Adderss = address1 + "\n " + city;
                Glide.with(CustomerHome.this).load(businessLogo).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .signature(new StringSignature("login_updating")).placeholder(R.mipmap.ic_digital_rupay)
                        .error(R.mipmap.ic_digital_rupay).into(new BitmapImageViewTarget(ivLogo) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        Log.d("sucess", resource.toString());
                        SessionData.getSessionDataInstance().setLogoBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        Log.d("error", e.getMessage());
                    }
                });
            }else if(servercode==1) {
                JSONObject customersObj = new JSONObject(response);
                Iterator<String> keys = customersObj.keys();
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                if (message.equalsIgnoreCase("success")) {
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            customerPendingAmountModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                    new TypeToken<CustomerPendingAmountModel>() {
                                    }.getType());
                        }
                    }
                    tv_Pending.setText("Rs. "+customerPendingAmountModel.getpending_amount()+" /-");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
