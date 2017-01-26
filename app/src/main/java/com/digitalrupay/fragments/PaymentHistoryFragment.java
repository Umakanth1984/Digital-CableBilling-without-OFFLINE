package com.digitalrupay.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.digitalrupay.R;
import com.digitalrupay.activities.UpdateComplaintsActivity;
import com.digitalrupay.adapters.ComplaintsListAdapter;
import com.digitalrupay.adapters.PaymentHistoryAdapter;
import com.digitalrupay.adapters.PaymentPagerAdapter;
import com.digitalrupay.datamodels.ComplaintsDataModel;
import com.digitalrupay.datamodels.PaymentHistoryDataModel;
import com.digitalrupay.interfaces.CommunicationListener;
import com.digitalrupay.network.WsUrlConstants;

import java.util.ArrayList;

/**
 * Created by Audlink_sri on 12/12/2016.
 */

public class PaymentHistoryFragment extends Fragment{

    RecyclerView rvComplaints;
    TextView errMsg;
    FrameLayout flAddComplaint;
    Intent complaintsActivity;
    CommunicationListener communicationListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicationListener = (CommunicationListener) getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);

        rvComplaints = (RecyclerView) view.findViewById(R.id.rvComplaints);
        errMsg = (TextView) view.findViewById(R.id.errMsg);
        flAddComplaint = (FrameLayout) view.findViewById(R.id.fl_add_complaint);
//        flAddComplaint.setOnClickListener(this);

        /*ArrayList<ComplaintsDataModel> complaintsDataModelArrayList = SessionData.getSessionDataInstance().getComplaintsDataModelArrayList();
        if (complaintsDataModelArrayList != null) {
            setComplaints(complaintsDataModelArrayList, null, "");
        } else {
        }*/
        complaintsActivity = new Intent(getActivity(), UpdateComplaintsActivity.class);
        return view;
    }

    public void setComplaints(ArrayList<PaymentHistoryDataModel> complaintsDataModelArrayList, String message, String text) {
        if (complaintsDataModelArrayList != null && complaintsDataModelArrayList.size() > 0) {
            PaymentHistoryAdapter complaintsListAdapter = new PaymentHistoryAdapter(getActivity(), complaintsDataModelArrayList);
//            complaintsListAdapter.setListener(this);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rvComplaints.setLayoutManager(llm);
            rvComplaints.setAdapter(complaintsListAdapter);
        } else {
            errMsg.setVisibility(View.VISIBLE);
            errMsg.setText(text);
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.complaint_item:
//                ComplaintsDataModel complaintsDataModel = (ComplaintsDataModel) v.getTag();
//                Log.d("complaint id", complaintsDataModel.getComplaint_id());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(WsUrlConstants.COMPLAINTS_DATA, complaintsDataModel);
//                complaintsActivity.putExtras(bundle);
//            case R.id.fl_add_complaint:
//                startActivityForResult(complaintsActivity, WsUrlConstants.COMPLAINTS_CODE);
//                break;
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == WsUrlConstants.COMPLAINTS_CODE) {
            if (data.getBooleanExtra(WsUrlConstants.RESULT, false)) {
                communicationListener.getComplaints();
            }
        }
    }
}