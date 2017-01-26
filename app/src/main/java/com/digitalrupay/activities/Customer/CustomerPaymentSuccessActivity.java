package com.digitalrupay.activities.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.analogics.thermalAPI.Bluetooth_Printer_2inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.digitalrupay.R;
import com.digitalrupay.activities.BaseActivity;
import com.digitalrupay.activities.PaymentSuccessActivity;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.CustomerPaymentSuccessModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.WsUrlConstants;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Audlink_sri on 12/29/2016.
 */

public class CustomerPaymentSuccessActivity extends BaseActivity {

    TextView tvInvoice;
    CustomerPaymentSuccessModel customerPaymentSuccessModel;
    String customerData = null;
    String address = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_payment_online_layout);

        setTitle("Payment Success", false);

        tvInvoice = (TextView) findViewById(R.id.txtInvoiceNumber);
        customerPaymentSuccessModel = ((CustomerPaymentSuccessModel) getIntent().getExtras().getSerializable(WsUrlConstants.PAYMENT_DATA));
        tvInvoice.setText(customerPaymentSuccessModel.getInvoice());
    }

    public void redirectToCustomers(View view) {
        Intent customers = new Intent(this, CustomerHome.class);
        customers.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(customers);
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
