package com.digitalrupay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.adapters.EmpCollectionListAdapter;
import com.digitalrupay.datamodels.CollectionDataModel;
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

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Santosh on 10/5/2016.
 */

public class CollectionsActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete {

    TextView tvTodaysCollection,tv_Collection_collection_amt,tv_OutStation_amt;
    String totalCollectionAmt = "0";
    int serviceRequest;
    String employeeId = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        setTitle("Collections", true);
        tvTodaysCollection = (TextView) findViewById(R.id.tv_todays_collection_amt);
        tv_Collection_collection_amt=(TextView)findViewById(R.id.tv_Collection_collection_amt);
        tv_OutStation_amt=(TextView)findViewById(R.id.tv_OutStation_amt);
        if(loginType.equals(loginTypes[4])) {
             employeeId = SessionData.getApartmentLoginResult().getEmp_id();
        }else if(loginType.equals(loginTypes[2])) {
             employeeId = SessionData.getEmployeeLoginResult().getEmp_id();
        }
        serviceRequest=1;
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.todaysTotalCollectionUrl.replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    public void navigateToPayments(View view) {
        Intent payments = new Intent(this, PaymentsActivity.class);
        payments.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        startActivity(payments);
    }

    public void navigateToTodaysCollections(View view) {
        Intent todayCollection = new Intent(this, TodayCollectionsActivity.class);
        todayCollection.putExtra(WsUrlConstants.AMOUNT, totalCollectionAmt);
        startActivity(todayCollection);
    }

    @Override
    public void asyncResponse(String response) {
        try {
            if(serviceRequest==1) {
                JSONObject customersObj = new JSONObject(response);
                CollectionDataModel collectionDataModel = null;
                ArrayList<CollectionDataModel> collectionDataModelArrayList = new ArrayList<>();
                Iterator<String> keys = customersObj.keys();
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                if (message.equalsIgnoreCase("success")) {
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            collectionDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                    new TypeToken<CollectionDataModel>() {
                                    }.getType());
                            collectionDataModelArrayList.add(collectionDataModel);
                        }
                    }
                }
                if (collectionDataModelArrayList.size() > 0) {
                    String totalCollections = collectionDataModelArrayList.get(0).getTotal_collections();
                    if (totalCollections == null)
                        totalCollections = "0";
                    tvTodaysCollection.setText("Rs." + totalCollections + "/-");
                    totalCollectionAmt = totalCollections;
                } else {
                    tvTodaysCollection.setText("Rs.0/-");
                }
                loadCollection();
            }else if(serviceRequest==2){
                try {
                    JSONObject customersObj = new JSONObject(response);
                    Iterator<String> keys = customersObj.keys();
                    String text = customersObj.getString("total_collections");
                    tv_Collection_collection_amt.setText("Rs." + text + "/-");
                    loadOutStation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(serviceRequest==3){
                try {
                    Log.i("response",response);
                    JSONObject customersObj = new JSONObject(response);
                    String text = customersObj.getString("total_outstaning");
                    tv_OutStation_amt.setText("Rs." + text + "/-");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOutStation() {
        serviceRequest=3;
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.emp_tot_outstanding.replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    private void loadCollection() {
        serviceRequest=2;
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.emp_tot_collections.replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    public void navigateToSummary(View view){
        Intent summary=new Intent(CollectionsActivity.this,SummaryActivity.class);
        startActivity(summary);
    }
    public void navigateToOutStation(View view){
        Intent summary=new Intent(CollectionsActivity.this,OutStationActivity.class);
        startActivity(summary);
    }
    public void navigateToCollection(View view){
        Intent summary=new Intent(CollectionsActivity.this,EmpCollectionActivity.class);
        startActivity(summary);
    }
}
