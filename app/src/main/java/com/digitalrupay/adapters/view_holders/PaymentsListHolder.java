package com.digitalrupay.adapters.view_holders;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.activities.PaymentSuccessActivity;
import com.digitalrupay.activities.PaymentsActivity;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.CustomerPaymentSuccessModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.MaintainceModel;
import com.digitalrupay.datamodels.PaymentHistoryDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.AsyncRequestEx;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Audlink_sri on 12/27/2016.
 */

public class PaymentsListHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {

    public TextView tvName, tvAddress, tvMobileNumber, tvCurrentDue, tvPreviousDue, tvTotal,tvAmountDue,tvNextPaymentDate;
    ImageView ivArrow;
    View view;
    String custNumber,custId,pendingAmt,totalAmtDue,serviceFormat,amount,chequeNumber,bankName,branchName,date,remarks,transactionType,trxnType,employeeId,nexPaymentDate;
    EditText edtAmount,edtRemarks,edtDate,edtBranchName,edtBankName,edtChequeNumber;
    Context context;
    int serviceRequest;
    CustomerDataModel customerDataModel;
    Calendar myCalendar;
    LinearLayout ivAmountDue, llTransactionType, llCalender,layspiPaymentFor;
    Spinner spiPaymentFor,transactionTypes;
    public static ArrayList<String> strings=new ArrayList<String>();
    String getspiPaymentFor;
    MaintainceModel maintainceModel=null;
    FrameLayout fl_payment,fl_ok;
    public PaymentsListHolder(View v, Context con) {
        super(v);
        this.context=con;
        this.view = v;
        layspiPaymentFor=(LinearLayout)v.findViewById(R.id.layspiPaymentFor);
        spiPaymentFor=(Spinner)v.findViewById(R.id.spiPaymentFor);
        llCalender=(LinearLayout)v.findViewById(R.id.llCalender);
        tvName = (TextView) v.findViewById(R.id.txtName);
        tvAddress = (TextView) v.findViewById(R.id.txtAddress);
        tvMobileNumber = (TextView) v.findViewById(R.id.txtMobileNumber);
        tvCurrentDue = (TextView) v.findViewById(R.id.txtCurrentDue);
        tvPreviousDue = (TextView) v.findViewById(R.id.txtPrevMonthDue);
        tvTotal = (TextView) v.findViewById(R.id.txtTotalDue);
        tvAmountDue = (TextView) v.findViewById(R.id.txtAmountDue);
        edtAmount = (EditText) v.findViewById(R.id.edtAmount);
        edtChequeNumber = (EditText) v.findViewById(R.id.edtChequeNumber);
        edtBankName = (EditText) v.findViewById(R.id.edtBank);
        edtBranchName = (EditText) v.findViewById(R.id.edtBranch);
        edtDate = (EditText) v.findViewById(R.id.edtDate);
        edtRemarks = (EditText) v.findViewById(R.id.edtRemarks);
        tvNextPaymentDate = (TextView) v.findViewById(R.id.tvNextPaymentDate);
        llTransactionType = (LinearLayout) v.findViewById(R.id.llTransactionType);
        edtChequeNumber = (EditText) v.findViewById(R.id.edtChequeNumber);
        edtBankName = (EditText) v.findViewById(R.id.edtBank);
        edtBranchName = (EditText) v.findViewById(R.id.edtBranch);
        edtDate = (EditText) v.findViewById(R.id.edtDate);
        edtRemarks = (EditText) v.findViewById(R.id.edtRemarks);
        transactionTypes = (Spinner) v.findViewById(R.id.transactionType);
        ivAmountDue=(LinearLayout)v.findViewById(R.id.ivAmountDue);
        transactionTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transactionType = (String) parent.getItemAtPosition(position);
                Log.d("transaction type", transactionType);
                if (transactionType.equalsIgnoreCase("Cheque")) {
                    llTransactionType.setVisibility(View.VISIBLE);
                } else {
                    llTransactionType.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        llCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        fl_payment=(FrameLayout)v.findViewById(R.id.fl_payment);
        fl_ok=(FrameLayout)v.findViewById(R.id.fl_ok);
        fl_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nexPaymentDate = tvNextPaymentDate.getText().toString();
                if (nexPaymentDate.length() > 0 && !nexPaymentDate.equals("Calender") && serviceFormat.length() > 0) {
                    serviceRequest = 3;
                    AsyncRequest asyncRequest = new AsyncRequest(context, "GET", new ArrayList<NameValuePair>(), "Processing Request..");
                    asyncRequest.execute(WsUrlConstants.nextPaymentDateUrl.replace(WsUrlConstants.CUSTOMER_ID, custId)
                            .replace(WsUrlConstants.EMPLOYEE_ID, employeeId).replace(WsUrlConstants.NEXT_PAYMENT_DATE, serviceFormat));
                } else {
                    Toast.makeText(context, "Please Select Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setComplaintsData(CustomerDataModel customerData) {
        view.setTag(customerData);
        if(((PaymentsActivity)context).loginType.equals(loginTypes[4])){
            layspiPaymentFor.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spiPaymentForspi=new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1,strings);
            spiPaymentFor.setAdapter(spiPaymentForspi);
            ivAmountDue.setVisibility(View.GONE);
        }
        if (customerData != null) {
           this.customerDataModel=customerData;
            custNumber = customerData.getCustom_customer_no();
            custId = customerData.getCust_id();
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
            EmployeeDataModel employeeDataModel;
            if(SessionData.getSessionDataInstance().getApartmentLoginResult()!= null) {
                employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
            }else {
                employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
            }
            employeeId = employeeDataModel.getEmp_id();
            tvName.setText(firstName + " " + lastName + " - (" + customerNumber + ")");
            String add2=customerData.getAddr2();
            if(add2!=null) {
                tvAddress.setText(customerData.getAddr1() + ", \n" + customerData.getAddr2() + ", " + customerData.getCity());
            }else {
                tvAddress.setText(customerData.getAddr1() +", " + customerData.getCity());
            }
            tvMobileNumber.setText(customerData.getMobile_no());
            pendingAmt = customerData.getPending_amount();
            if(!((PaymentsActivity)context).loginType.equals(loginTypes[4])) {
                if (pendingAmt != null) {
                    tvAmountDue.setText("Rs." + pendingAmt + "/-");
                } else {
                    tvAmountDue.setText("Rs.0/-");
                    pendingAmt = "0";
                }
                if (!pendingAmt.contains("-"))
                    edtAmount.setText(pendingAmt);
            }
            totalAmtDue = pendingAmt;
        } else {
//            custId = "";
            tvName.setText("");
            tvAddress.setText("");
            tvMobileNumber.setText("");
            tvAmountDue.setText("");
            edtAmount.setText("");
            edtChequeNumber.setText("");
            edtBankName.setText("");
            edtBranchName.setText("");
            edtDate.setText("");
            edtRemarks.setText("");
            pendingAmt = "";
            serviceFormat = "";
        }
        this.fl_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidRequest = false;
                amount = edtAmount.getText().toString().trim();
                if (amount.length() == 0) {
                    amount = pendingAmt;
                }
                chequeNumber = edtChequeNumber.getText().toString().trim();
                bankName = edtBankName.getText().toString().trim();
                branchName = edtBranchName.getText().toString().trim();
                date = edtDate.getText().toString().trim();
                remarks = edtRemarks.getText().toString().trim();
                transactionType = "Cash";
                if (transactionType.equalsIgnoreCase("Cash")) {
                    trxnType = "1";
                    chequeNumber = "";
                    bankName = "";
                    branchName = "";
                    date = "";
                    isValidRequest = true;
                } else {
                    if (chequeNumber != null && chequeNumber.length() > 0) {
                        if (bankName != null && bankName.length() > 0) {
                            if (branchName != null && branchName.length() > 0) {
                                if (date != null && date.length() > 0) {
                                    trxnType = "2";
                                    isValidRequest = true;
                                } else {
                                    Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Please enter branch name", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Please enter bank name", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Please enter cheque number", Toast.LENGTH_SHORT).show();
                    }
                }
                if (isValidRequest) {
                    AlertDialog paymentAlertDialog = new AlertDialog.Builder(context).setTitle("Payment")
                            .setMessage("Are you sure want to pay the bill?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ((PaymentsActivity)context).sendPayment(custId,amount,trxnType,chequeNumber,bankName,branchName,date);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
//                                resetAllViews();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
                }
            }
        });
    }
    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        serviceFormat = "yy/MM/dd";
        SimpleDateFormat serviceSdf = new SimpleDateFormat(serviceFormat);
        serviceFormat = serviceSdf.format(myCalendar.getTime());
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tvNextPaymentDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getspiPaymentFor = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
