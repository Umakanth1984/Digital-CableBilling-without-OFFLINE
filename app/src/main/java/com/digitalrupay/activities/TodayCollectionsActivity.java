package com.digitalrupay.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.digitalrupay.R;
import com.digitalrupay.adapters.EmployeeListAdapter;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.datamodels.TodayCollectionEmployeeDataModel;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.apache.http.NameValuePair;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Santosh on 10/8/2016.
 */

public class TodayCollectionsActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete {

    TextView tvTotalCollection;
    String todayTotalCollectionAmt;
    RecyclerView recyclerViewFee;
    private LinearLayoutManager llm;
    EmployeeListAdapter employeeListAdapter;
    ArrayList<TodayCollectionEmployeeDataModel> employeeDataModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_collection);

        setTitle("Today's Collection", true);

        todayTotalCollectionAmt = getIntent().getStringExtra(WsUrlConstants.AMOUNT);
        tvTotalCollection = (TextView) findViewById(R.id.tvTotal);

        recyclerViewFee = (RecyclerView) findViewById(R.id.recyclerView_employees);
        llm = new LinearLayoutManager(this);
        recyclerViewFee.setLayoutManager(llm);//add ItemDecoration

        employeeListAdapter = new EmployeeListAdapter(this, employeeDataModelArrayList);
        employeeListAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                TodayCollectionEmployeeDataModel employeeDataModel = employeeDataModelArrayList.get(position);
                System.out.println(employeeDataModel.getEmp_name());
            }

            @Override
            public void onListItemCollapsed(int position) {
                TodayCollectionEmployeeDataModel employeeDataModel = employeeDataModelArrayList.get(position);
                System.out.println(employeeDataModel.getEmp_name());
            }
        });


        EmployeeDataModel employeeDataModel;
        if(SessionData.getSessionDataInstance().getApartmentLoginResult()!= null) {
            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
        }else {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
        }
        String employeeId = employeeDataModel.getEmp_id();
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.todaysCollectionUrl.replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    @Override
    public void asyncResponse(String response) {
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(response.toString().getBytes())));
            Type dataListType = new TypeToken<HashMap<String, ArrayList<TodayCollectionEmployeeDataModel>>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            HashMap<String, ArrayList<TodayCollectionEmployeeDataModel>> employeeDataModelList = (HashMap<String, ArrayList<TodayCollectionEmployeeDataModel>>) gson.fromJson(jsonReader, dataListType);
            employeeDataModelArrayList = employeeDataModelList.get("response");
            Log.d("employee list size", employeeDataModelList.size() + "");
            if (employeeDataModelArrayList.size() > 0) {
                Log.d("ward list size", employeeDataModelArrayList.size() + " " + employeeDataModelArrayList.get(0).getEmp_name());
                employeeListAdapter = new EmployeeListAdapter(this, employeeDataModelArrayList);
                recyclerViewFee.setAdapter(employeeListAdapter);

                tvTotalCollection.setText("Total : Rs." + todayTotalCollectionAmt + "/-");
            } else {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    void addGroups(ArrayList<CollectionDataModel> collectionDataModels) {
        for (CollectionDataModel collectionDataModel
                : collectionDataModels) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View itemView = inflater.inflate(R.layout.employee_item, null);
            TextView tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
            TextView tvGroupCollectionAmount = (TextView) itemView.findViewById(R.id.tvGroupCollection);
            tvGroupName.setText(collectionDataModel.getTotal_collections());
            String collectionAmt = collectionDataModel.getTotal_collections();
            if (collectionAmt != null) {
                tvGroupCollectionAmount.setText("Rs." + collectionAmt + "/-");
            } else {
                tvGroupCollectionAmount.setText("Rs.0/-");
            }
//            itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, 8, 8, 0);
//            itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//            itemView.setLayoutParams(lp);
//            itemView.setTag(collectionDataModel);
            // Creating a new LinearLayout
//            linearLayout = new LinearLayout(this);

            // Defining the LinearLayout layout parameters with Fill_Parent
//            linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            linearLayout.setLayoutParams(linearLayoutParams);
            llGroups.addView(itemView);
        }
        tvTotalCollection.setText("Total : Rs." + todayTotalCollectionAmt + "/-");
    }
*/
}
