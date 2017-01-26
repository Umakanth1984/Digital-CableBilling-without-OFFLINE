package com.digitalrupay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.digitalrupay.R;
import com.digitalrupay.activities.Customer.CustomerLogin;
import com.digitalrupay.network.WsUrlConstants;

import static com.digitalrupay.network.WsUrlConstants.CustloginTypes;
import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Umakanth on 9/4/2016.
 */
public class LoginTypeActivity extends BaseActivity {

    TextView tvTitle, tvSchool;
    FrameLayout flParent, flSchool, flAgent,flcustomer;
    String Custtype;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_fee_system);

        tvTitle = (TextView) findViewById(R.id.tv_school_fee_system);
        tvSchool = (TextView) findViewById(R.id.tv_school);
        flParent = (FrameLayout) findViewById(R.id.fl_parent_login);
        flSchool = (FrameLayout) findViewById(R.id.fl_school_login);
        flAgent = (FrameLayout) findViewById(R.id.fl_agent_login);
        flcustomer=(FrameLayout)findViewById(R.id.fl_customer_login);
        if (loginType.equals(loginTypes[2])) {
            tvTitle.setText(R.string.cable_billing_system);
            tvSchool.setText(R.string.employee_login);
            Custtype=CustloginTypes[0];
        }else if(loginType.equals(loginTypes[4])) {
            tvTitle.setText(R.string.Apartment_billing_system);
            tvSchool.setText(R.string.employee_login);
            flcustomer.setVisibility(View.GONE);
            Custtype=CustloginTypes[1];
        }
        else {
            tvTitle.setText(R.string.school_fee_system);
            tvSchool.setText(R.string.parent_login);
        }
        flParent.setVisibility(View.GONE);
        flAgent.setVisibility(View.GONE);
    }
    public void navigateToSchool(View view) {
        Intent feeList = new Intent(this, LoginActivity.class);
        feeList.putExtra(WsUrlConstants.LOGIN_TYPE, getIntent().getStringExtra(WsUrlConstants.LOGIN_TYPE));
        startActivity(feeList);
    }
    public void  navigateToCustomer(View view) {
        Intent feeList = new Intent(this, CustomerLogin.class);
        feeList.putExtra(WsUrlConstants.LOGIN_TYPE, "Customer");
        feeList.putExtra(WsUrlConstants.CUSTLOGIN_TYPE, Custtype);
        startActivity(feeList);
    }
}
