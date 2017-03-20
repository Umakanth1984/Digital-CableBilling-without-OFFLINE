package com.digitalrupay.payment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.digitalrupay.R;
import com.digitalrupay.activities.BaseActivity;
import com.digitalrupay.activities.Customer.CustomerHome;
import com.digitalrupay.activities.Customer.CustomerPayment;
import com.digitalrupay.activities.Customer.CustomerPaymentSuccessActivity;
import com.digitalrupay.activities.PaymentSuccessActivity;
import com.digitalrupay.datamodels.CustomerModel;
import com.digitalrupay.datamodels.CustomerPaymentSuccessModel;
import com.digitalrupay.datamodels.MaintainceModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OnlinePaymentActivity extends BaseActivity implements PaymentResultListener,AsyncRequest.OnAsyncRequestComplete {
    private static final String TAG = OnlinePaymentActivity.class.getSimpleName();
    String custId,amount,chequeNumber,bankName,branchName,date,transactionType,trxnType,email,mobile,business_name,business_email,businessLogo,invoice_id;
    TextView tv_amount,tv_bname;
    AsyncRequest asyncRequest;
    ImageView ivLogo;
    int requstcode,payamount;
    CustomerModel customerData=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tv_amount=(TextView)findViewById(R.id.tv_amount);
        tv_bname=(TextView)findViewById(R.id.tv_bname);
        ivLogo = (ImageView) findViewById(R.id.logo);
        customerData = SessionData.getCustomerLoginResult();
        Button button = (Button) findViewById(R.id.btn_pay);
        custId = customerData.getcust_id();
        amount=getIntent().getExtras().getString("amount");
        email=customerData.getemail_id();
        mobile=customerData.getmobile_no();
        invoice_id=customerData.getcust_id();
        tv_amount.setText("Rs." + amount + "/-");
        payamount=Integer.parseInt(amount)*100;
        getBusinessLogo();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            String BaseURL=WsUrlConstants.baseEmployeeUrl;
            JSONObject options = new JSONObject();
            options.put("name", business_name);
            options.put("description", "Monthly  Billing");
            options.put("image",businessLogo);
            options.put("currency", "INR");
            options.put("amount", payamount);
            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", mobile);
            preFill.put("base_url",BaseURL);
            preFill.put("invoice_id",invoice_id);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            requstcode=1;
            boolean isValidRequest = false;
                transactionType = "0";
                chequeNumber = "";
                bankName = "";
                branchName = "";
                date = "";
                sendPayment(custId, amount, transactionType, chequeNumber, bankName, branchName, date);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }
    public void sendPayment(final String custId, final String amount, final String trxnType, final String chequeNumber, final String bankName, final String branchName, final String date) {
        requstcode=1;
        AsyncRequest asyncRequest = new AsyncRequest(OnlinePaymentActivity.this, "GET", new ArrayList<NameValuePair>(), "Processing Request..");
        asyncRequest.execute(WsUrlConstants.CustomercablePaymentUrl
                .replace(WsUrlConstants.CUSTOMER_ID, custId)
                .replace(WsUrlConstants.AMOUNT, amount)
                .replace(WsUrlConstants.TRANSACTION_TYPE, trxnType)
                .replace(WsUrlConstants.CHEQUE_NUMBER, chequeNumber)
                .replace(WsUrlConstants.BANK_NAME, bankName)
                .replace(WsUrlConstants.BRANCH_NAME, branchName)
                .replace(WsUrlConstants.TRANSACTION_DATE,date)
                .replace(WsUrlConstants.REMARKS, "Remarks"));
    }
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
    @Override
    public void asyncResponse(String response) {
        parseCustomerDetails1(response);
    }

    private void parseCustomerDetails1(String response) {
        try {
            if(requstcode==1) {
                JSONObject customersObj = new JSONObject(response);
                Iterator<String> keys = customersObj.keys();
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                if (message.equalsIgnoreCase("success")) {
                    Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
                    CustomerPaymentSuccessModel customerPaymentSuccessModel = null;
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            customerPaymentSuccessModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                    new TypeToken<CustomerPaymentSuccessModel>() {
                                    }.getType());
                            Intent paymentActivity = new Intent(this, CustomerPaymentSuccessActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(WsUrlConstants.PAYMENT_DATA, customerPaymentSuccessModel);
                            bundle.putSerializable(WsUrlConstants.CUSTOMER_DATA, customerData);
                            bundle.putString(WsUrlConstants.CUSTLOGIN_TYPE, custloginType);
                            paymentActivity.putExtras(bundle);
                            startActivityForResult(paymentActivity, 1000);
                        }
                    }
                }
//            }else if(serviceRequest == 2){
//                try {
//                    strings.add("Maintenance");
//                    if (serviceRequest == 1) {
//                        if (message.equalsIgnoreCase("success")) {
//                            while (keys.hasNext()) {
//                                String key = keys.next();
//                                if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
//                                    maintainceModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
//                                            new TypeToken<MaintainceModel>() {
//                                            }.getType());
//                                    setCustomerData1(maintainceModel);
//                                }
//                            }
//                            ArrayAdapter<String> spiPaymentForspi=new ArrayAdapter<String>(CustomerPayment.this,android.R.layout.simple_expandable_list_item_1,strings);
//                            spiPaymentFor.setAdapter(spiPaymentForspi);
//                        }
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
            }else if(requstcode==2){
                JSONObject customersObj = new JSONObject(response);
                JSONObject inOBJ=customersObj.getJSONObject("0");
                business_name=inOBJ.getString("business_name");
                tv_bname.setText(business_name);
                String address1=inOBJ.getString("address1");
                String address2="";
                if(!inOBJ.isNull("address2")){
                    address2 =inOBJ.getString("address2");
                }
                business_email="";
                if(!inOBJ.isNull("email")){
                    business_email=inOBJ.getString("email");
                }
                String city=inOBJ.getString("city");
                String state=inOBJ.getString("state");
                String mobile=inOBJ.getString("mobile");

                businessLogo = customersObj.getString("business_logo");
                JSONObject businessObj = new JSONObject(customersObj.getJSONObject("0").toString());
                WsUrlConstants.SERVICES_name = businessObj.getString("business_name");
                WsUrlConstants.SERVICE_MOBILE_NUMBER = businessObj.getString("mobile");
                WsUrlConstants.Adderss=address1+"\n "+city;
                Glide.with(OnlinePaymentActivity.this).load(businessLogo).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .signature(new StringSignature("login_updating")).placeholder(R.mipmap.ic_digital_rupay)
                        .error(R.mipmap.ic_digital_rupay).into(new BitmapImageViewTarget(ivLogo) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        SessionData.getSessionDataInstance().setLogoBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        Log.d("error", e.getMessage());
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getBusinessLogo() {
        requstcode=2;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.businessLogoUrl);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
