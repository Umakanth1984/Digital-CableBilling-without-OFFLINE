package com.digitalrupay.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.analogics.thermalAPI.Bluetooth_Printer_2inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.crittercism.internal.c;
import com.digitalrupay.R;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.CustomerPaymentSuccessModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.WsUrlConstants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Santosh on 10/2/2016.
 */

public class PaymentSuccessActivity extends BaseActivity {

    TextView tvInvoice, tvTransactionType;
    CustomerPaymentSuccessModel customerPaymentSuccessModel;
    String customerData = null;
    AnalogicsThermalPrinter conn = new AnalogicsThermalPrinter();
    String address = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_payment_success_layout);
        tvInvoice = (TextView) findViewById(R.id.txtInvoiceNumber);
        tvTransactionType = (TextView) findViewById(R.id.txtPaymentMode);
        customerPaymentSuccessModel = ((CustomerPaymentSuccessModel) getIntent().getExtras().getSerializable(WsUrlConstants.PAYMENT_DATA));
        address = SessionData.getSessionDataInstance().getBluetoothAddress();
        tvInvoice.setText(customerPaymentSuccessModel.getInvoice());
        if (customerPaymentSuccessModel.getTransaction_type().equalsIgnoreCase("1")) {
            tvTransactionType.setText("By Cash");
        } else {
            tvTransactionType.setText("By Cheque");
        }

        setTitle("Payment Success", false);

        Bundle bundleData = getIntent().getExtras();
        CustomerDataModel customerDataModel = ((CustomerDataModel) bundleData.getSerializable(WsUrlConstants.CUSTOMER_DATA));
//        CustomerPaymentSuccessModel customerPaymentSuccessModel = ((CustomerPaymentSuccessModel) bundleData.getSerializable(WsUrlConstants.CUSTOMER_PAYMENT));

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        String currentDate = sdf.format(System.currentTimeMillis());

        String firstName = "", lastName = "", customerNumber = "", custAddress = "", balance = "0";
        if (customerDataModel.getFirst_name() != null) {
            firstName = customerDataModel.getFirst_name().trim();
        }
        if (customerDataModel.getLast_name() != null) {
            lastName = customerDataModel.getLast_name().trim();
        }
        if (customerDataModel.getCustom_customer_no() != null) {
            customerNumber = customerDataModel.getCustom_customer_no();
        }
        if (customerDataModel.getAddr2() != null) {
            custAddress = customerDataModel.getAddr2();
        }
        if (customerDataModel.getCity() != null) {
            if (custAddress.length() > 0) {
                custAddress = custAddress + ", " + customerDataModel.getCity();
            } else {
                custAddress = customerDataModel.getCity();
            }
        }
        Integer pendingAmt = Integer.parseInt(customerDataModel.getPending_amount());
        if (pendingAmt > 0) {
            Integer paidAmt = Integer.parseInt(customerPaymentSuccessModel.getAmount_paid());
            if (pendingAmt < paidAmt) {
                balance = (paidAmt - pendingAmt) + "";
            } else {
                balance = (pendingAmt - paidAmt) + "";
            }
        } else if (pendingAmt < 0) {
            Integer paidAmt = Integer.parseInt(customerPaymentSuccessModel.getAmount_paid());
            if (pendingAmt < paidAmt) {
                balance = "-" + (paidAmt - pendingAmt) + "";
            } else {
                balance = "-" + (pendingAmt - paidAmt) + "";
            }
        }

        String paymentMode = null;
        if (customerPaymentSuccessModel.getTransaction_type().equalsIgnoreCase("1")) {
            paymentMode = "By Cash";
        } else {
            paymentMode = "By Cheque";
        }
        EmployeeDataModel employeeDataModel;
        if(loginType.equals(loginTypes[4])) {
            String amount;
            String getspiPaymentFor=getIntent().getExtras().getString("getspiPaymentFor");
            if(getspiPaymentFor.equalsIgnoreCase("2")){
                amount="Maintenance";
            }else {
                amount=getspiPaymentFor;
            }
            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
            if(amount.equalsIgnoreCase("Maintenance")) {
                customerData = "     MONTHLY BILL      " +
                        "\n------------------------\n " +
                        WsUrlConstants.SERVICES_name + "\n" +
                        " " + WsUrlConstants.SERVICE_MOBILE_NUMBER + "\n" +
                        " " + WsUrlConstants.Adderss + "\n" +
//                "\n ----------------------- \n "+
                        " Date : " + currentDate +
                        "\n------------------------\n" +
                        "Cust.No : " + customerNumber +
                        "\nName    : " + firstName + " " + lastName +
                        "\nAddr    : " + custAddress +
                        "\nMobile  : " + customerDataModel.getMobile_no() +
//                "\n\nMonthly Bill : "+customerDataModel.getMonthlybill()+
                        "\n\nCurrent Due  : " + customerDataModel.getPending_amount() +
                        "\n\n------------------------" +
                        "  "+amount +
                        "\n Amount paid  : " + customerPaymentSuccessModel.getAmount_paid()
                        + "\n Outstanding  : " + balance +
                        "\n------------------------\n" +
                        "\n      Receipt No : \n" + customerPaymentSuccessModel.getInvoice() +
                        "\n\n       " + employeeDataModel.getEmp_first_name() + " " + employeeDataModel.getEmp_last_name() + "\n" +
                        "       " + employeeDataModel.getEmp_mobile_no() + "\n\n\n\n\n\n" +
                        "\n";
            }else {
                customerData = "        RECEIPT     " +
                        "\n------------------------\n " +
                        WsUrlConstants.SERVICES_name + "\n" +
                        " " + WsUrlConstants.SERVICE_MOBILE_NUMBER + "\n" +
                        " " + WsUrlConstants.Adderss + "\n" +
//                "\n ----------------------- \n "+
                        " Date : " + currentDate +
                        "\n------------------------\n" +
                        "Flat No : " + customerNumber +
                        "\nName    : " + firstName + " " + lastName +
                        "\nAddr    : " + custAddress +
                        "\nMobile  : " + customerDataModel.getMobile_no() +
                        "\n\n------------------------" +
                        amount.toUpperCase() +
                        "\n Amount paid  : " + customerPaymentSuccessModel.getAmount_paid()+
                        "\n------------------------\n" +
                        "      Receipt No : \n" + customerPaymentSuccessModel.getInvoice() +
                        "\n\n       " + employeeDataModel.getEmp_first_name() + " " + employeeDataModel.getEmp_last_name() + "\n" +
                        "       " + employeeDataModel.getEmp_mobile_no() + "\n\n\n\n\n\n" +
                        "\n";
            }
        }else {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
            customerData = "     MONTHLY BILL      "+
                    "\n------------------------\n "+
                    WsUrlConstants.SERVICES_name +"\n"+
                    " "+WsUrlConstants.SERVICE_MOBILE_NUMBER+ "\n" +
                    " "+WsUrlConstants.Adderss+"\n"+
//                "\n ----------------------- \n "+
                    " Date : " + currentDate +
                    "\n------------------------\n"+
                    "Cust.No : "+customerNumber+
                    "\nName    : "+firstName+" "+lastName +
                    "\nAddr    : " + custAddress +
                    "\nMobile  : " + customerDataModel.getMobile_no() +
//                "\n\nMonthly Bill : "+customerDataModel.getMonthlybill()+
                    "\n\nCurrent Due  : "+ customerDataModel.getPending_amount() +
                    "\n\n------------------------" +
                    "\n Amount paid  : " + customerPaymentSuccessModel.getAmount_paid()
                    +"\n Outstanding  : " + balance +
                    "\n------------------------\n" +
                    "      Receipt No : \n"+customerPaymentSuccessModel.getInvoice() +
                    "\n\n   " + employeeDataModel.getEmp_first_name()+ " "+employeeDataModel.getEmp_last_name()+"\n"+
                    "     "+employeeDataModel.getEmp_mobile_no()+"\n\n\n\n\n\n" +
                    "\n";
        }

    }

    public void redirectToCustomers(View view) {
//        Intent customers = new Intent(this, Menu_2ST_Activity.class);
//        customers.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(customers);
        finish();
    }

    public void printInvoice(View view){
//        CustomerDataModel customerDataModel = ((CustomerDataModel) getIntent().getExtras().getSerializable(WsUrlConstants.CUSTOMER_DATA));
//        Intent customers = new Intent(this, Menu_2ST_Activity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(WsUrlConstants.CUSTOMER_DATA, customerDataModel);
//        bundle.putSerializable(WsUrlConstants.CUSTOMER_PAYMENT, customerPaymentSuccessModel);
//        customers.putExtras(bundle);
//        startActivity(customers);
//        finish();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                PaymentSuccessActivity.this);

        // set title
        alertDialogBuilder.setTitle("Print Invoice");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do You Want to Print")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        try {
                            SessionData.saveBluetoothAddress(address);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            conn.openBT(address);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Bluetooth_Printer_2inch_ThermalAPI printer = new Bluetooth_Printer_2inch_ThermalAPI();
                        String data = "";
                        data = customerData;
                        String printdata = printer.font_Courier_24(data);
                        conn.printData(printdata);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    @Override
    public void onPause() {
        super.onPause();
        try {
            conn.closeBT();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
