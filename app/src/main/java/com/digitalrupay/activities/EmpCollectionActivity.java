package com.digitalrupay.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.digitalrupay.R;
import com.digitalrupay.adapters.EmpCollectionListAdapter;
import com.digitalrupay.datamodels.EmpCollectionDataModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class EmpCollectionActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete {

    TextView tvTotalCollection;
    String todayTotalCollectionAmt;
    RecyclerView recyclerViewFee;
    private LinearLayoutManager llm;
    EmpCollectionListAdapter employeeListAdapter;
    ArrayList<EmpCollectionDataModel> employeeDataModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_collection);

        setTitle("Collections Details", true);
        tvTotalCollection = (TextView) findViewById(R.id.tvTotal);

        recyclerViewFee = (RecyclerView) findViewById(R.id.recyclerView_employees);
        llm = new LinearLayoutManager(this);
        recyclerViewFee.setLayoutManager(llm);
//        employeeListAdapter = new EmpCollectionListAdapter(this, employeeDataModelArrayList);
//        employeeListAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
//            @Override
//            public void onListItemExpanded(int position) {
//                EmpCollectionDataModel employeeDataModel = employeeDataModelArrayList.get(position);
//            }
//
//            @Override
//            public void onListItemCollapsed(int position) {
//                EmpCollectionDataModel employeeDataModel = employeeDataModelArrayList.get(position);
//            }
//        });


        EmployeeDataModel employeeDataModel;
//        if(SessionData.getSessionDataInstance().getApartmentLoginResult()!= null) {
//            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
//        }else {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
//        }
        String employeeId = employeeDataModel.getEmp_id();
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.emp_tot_collections.replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    @Override
    public void asyncResponse(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            EmpCollectionDataModel complaintsDataModel = null;
            ArrayList<EmpCollectionDataModel> complaintsDataModelArrayList = new ArrayList<>();
            Iterator<String> keys = customersObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                    complaintsDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                            new TypeToken<EmpCollectionDataModel>() {}.getType());
                    complaintsDataModelArrayList.add(complaintsDataModel);
                }
            }
            String message = customersObj.getString("message");
            String text = customersObj.getString("total_collections");
            if (complaintsDataModelArrayList.size() > 0) {
                employeeListAdapter = new EmpCollectionListAdapter(this, complaintsDataModelArrayList);
                recyclerViewFee.setAdapter(employeeListAdapter);
                tvTotalCollection.setText("Total Collections: Rs." + text + "/-");
            } else {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
