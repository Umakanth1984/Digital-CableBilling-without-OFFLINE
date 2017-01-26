package com.digitalrupay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.digitalrupay.R;
import com.digitalrupay.activities.Customer.CustomerHome;
import com.digitalrupay.datamodels.CustomerModel;
import com.digitalrupay.datamodels.DetailsModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.LoginResultModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.datamodels.UserModel;
import com.digitalrupay.network.WsUrlConstants;

/**
 * Created by Santosh on 8/19/2016.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (SessionData.getSessionDataInstance().getLoginResult() != null ||
                 SessionData.getSessionDataInstance().getEmployeeLoginResult() != null || SessionData.getSessionDataInstance().getCustomerLoginResult() != null || SessionData.getSessionDataInstance().getApartmentLoginResult()!= null) {
                    redirectToFeeListActivity();
                } else {
                    redirectToLoginActivity();
                }
            }
        }, 5000);
    }

    public void redirectToFeeListActivity() {
        LoginResultModel loginResultModel = SessionData.getSessionDataInstance().getLoginResult();
        EmployeeDataModel employeeDataModel;
        if(SessionData.getSessionDataInstance().getApartmentLoginResult()!= null) {
             employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
        }else {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
        }
        CustomerModel customerModel=SessionData.getSessionDataInstance().getCustomerLoginResult();
        String parentId = null;
        Intent feeList = null;
        if (SessionData.getSessionDataInstance().getEmployeeLoginResult()!= null) {
            parentId = employeeDataModel.getEmp_id();
            loginType = "cable";
            feeList = new Intent(this, CableBillingActivity.class);
            feeList.putExtra(WsUrlConstants.PARENT_ID, parentId);
            feeList.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
            startActivity(feeList);
            finish();
        }else if(SessionData.getSessionDataInstance().getApartmentLoginResult()!= null){
            parentId = employeeDataModel.getEmp_id();
            loginType = "Apartment";
            feeList = new Intent(this, CableBillingActivity.class);
            feeList.putExtra(WsUrlConstants.PARENT_ID, parentId);
            feeList.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
            startActivity(feeList);
            finish();
        }else if (loginResultModel != null) {
            DetailsModel detailsModel = ((DetailsModel) loginResultModel.getDetails());
            if (detailsModel != null) {
                UserModel userModel = detailsModel.getUserModel();
                parentId = userModel.getId();
                loginType = "school";
                feeList = new Intent(this, FeeListActivity.class);
                feeList.putExtra(WsUrlConstants.PARENT_ID, parentId);
                feeList.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
                startActivity(feeList);
                finish();
            } else {
                redirectToLoginActivity();
                return;
            }

        }else if(customerModel!=null){
            parentId = customerModel.getcust_id();
            custloginType = "cable";
            Intent cust = new Intent(this, CustomerHome.class);
            cust.putExtra(WsUrlConstants.PARENT_ID, parentId);
            cust.putExtra(WsUrlConstants.CUSTLOGIN_TYPE, custloginType);
            startActivity(cust);
            finish();
        }
    }

    public void redirectToLoginActivity() {
        Intent login = new Intent(this, HomeActivity.class);
        startActivity(login);
        finish();
    }
}
