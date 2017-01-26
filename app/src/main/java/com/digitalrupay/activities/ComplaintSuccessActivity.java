package com.digitalrupay.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.digitalrupay.R;

/**
 * Created by Santosh on 11/2/2016.
 */

public class ComplaintSuccessActivity extends BaseActivity {

    TextView tvComplaintID, tvComplaintMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_success_layout);

        setTitle("Complaint Success", false);

        tvComplaintID = (TextView) findViewById(R.id.txtComplaintID);
        tvComplaintMessage = (TextView) findViewById(R.id.txtComplaintMessage);


    }

    public void redirectToComplaints(View view) {
//        Intent customers = new Intent(this, Menu_2ST_Activity.class);
//        customers.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(customers);
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
