package com.digitalrupay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.datamodels.ComplaintsDataModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Santosh on 10/7/2016.
 */

public class UpdateComplaintsActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete {

    public TextView tvCustomerName, tvCategory;
    EditText edtRemarks;
    Spinner spnStatus;
    String selectedStatus, employeeId;
    ComplaintsDataModel complaintsDataModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_complaint);
        setTitle("Update Complaint", true);
        complaintsDataModel = (ComplaintsDataModel) getIntent().getExtras().getSerializable(WsUrlConstants.COMPLAINTS_DATA);
        tvCustomerName = (TextView) findViewById(R.id.txtCustomerName);
        tvCategory = (TextView) findViewById(R.id.txtCategory);
        edtRemarks = (EditText) findViewById(R.id.edtRemarks);

        String firstName = "", lastName = "", customerNumber = "";
        if (complaintsDataModel.getFirst_name() != null) {
            firstName = complaintsDataModel.getFirst_name().trim();
        }
        if (complaintsDataModel.getLast_name() != null) {
            lastName = complaintsDataModel.getLast_name().trim();
        }
        if (complaintsDataModel.getCustom_customer_no() != null) {
            customerNumber = complaintsDataModel.getCustom_customer_no();
        }

        tvCustomerName.setText(firstName + " " + lastName + " - (" +  customerNumber +")");
        tvCategory.setText("Category: " + complaintsDataModel.getComp_cat());

        spnStatus = (Spinner) findViewById(R.id.spnStatus);
        spnStatus.setSelection(Integer.parseInt(complaintsDataModel.getStatus()));
        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = (String) parent.getItemAtPosition(position);
                Log.d("selected status", selectedStatus);
                selectedStatus = position + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        EmployeeDataModel employeeDataModel;
        if(SessionData.getSessionDataInstance().getApartmentLoginResult()!= null) {
            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
        }else {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
        }
        employeeId = employeeDataModel.getEmp_id();
    }

    public void updateComplaint(View view) {
        String remarks = edtRemarks.getText().toString().trim().replace(" ","%20");
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Processing Request..");
        asyncRequest.execute(WsUrlConstants.updateComplaintUrl.replace(WsUrlConstants.COMPLAINT_ID, complaintsDataModel.getComplaint_id())
                .replace(WsUrlConstants.EMPLOYEE_ID, employeeId).replace(WsUrlConstants.REMARKS, remarks).replace(WsUrlConstants.COMPLAINT_STATUS, selectedStatus));
    }

    @Override
    public void asyncResponse(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            String message = customersObj.getString("message");
            String text = customersObj.getString("text");
            if (message.equalsIgnoreCase("success")) {
                Intent updateComplaint = new Intent();
                updateComplaint.putExtra(WsUrlConstants.RESULT, true);
                setResult(WsUrlConstants.COMPLAINTS_CODE, updateComplaint);
                finish();
            }
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
