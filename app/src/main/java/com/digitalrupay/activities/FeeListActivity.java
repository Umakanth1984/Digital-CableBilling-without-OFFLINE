package com.digitalrupay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.digitalrupay.R;
import com.digitalrupay.adapters.CustomersListAdapter;
import com.digitalrupay.adapters.FeeListAdapter;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.WardDataModel;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Santosh on 7/5/2016.
 */
public class FeeListActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete, View.OnClickListener {

    private static final int VERTICAL_ITEM_SPACE = 48;
    RecyclerView recyclerViewFee;
    private LinearLayoutManager llm;
    FeeListAdapter feeListAdapter;
    ArrayList<WardDataModel> wardDataModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelist);
        if (loginType.equals(loginTypes[2])) {
            setTitle("CustomersList", false);
        } else {
            setTitle("FeeList", false);
        }

        recyclerViewFee = (RecyclerView) findViewById(R.id.recyclerView_fee);
        llm = new LinearLayoutManager(this);
        recyclerViewFee.setLayoutManager(llm);//add ItemDecoration
//        recyclerViewFee.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
//        recyclerViewFee.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

        feeListAdapter = new FeeListAdapter(this, wardDataModelArrayList);
        feeListAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                WardDataModel expandedRecipe = wardDataModelArrayList.get(position);
                System.out.println(expandedRecipe);
            }

            @Override
            public void onListItemCollapsed(int position) {
                WardDataModel collapsedRecipe = wardDataModelArrayList.get(position);
                System.out.println(collapsedRecipe);
            }
        });

        String parentId = getIntent().getStringExtra(WsUrlConstants.PARENT_ID);

        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", getParams(), "Fetching Data..");
        if (loginType.equals(loginTypes[2])) {
            asyncRequest.execute(WsUrlConstants.customerListUrl.replace(WsUrlConstants.EMPLOYEE_ID, parentId));
        } else {
            asyncRequest.execute(WsUrlConstants.feeListUrl.replace(WsUrlConstants.PARENT_ID, parentId));
        }

    }

    // here you specify and return a list of parameter/value pairs supported by
    // the API
    private ArrayList<NameValuePair> getParams() {
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("start", "0"));
//        params.add(new BasicNameValuePair("limit", "10"));
//        params.add(new BasicNameValuePair("fields", "id,title"));
        return params;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ward_item:
                if (loginType.equals(loginTypes[2])) {
                } else {
                    String feeId = (String) v.getTag();
                    Intent feeDetails = new Intent(this, FeeListDetailsActivity.class);
                    feeDetails.putExtra(WsUrlConstants.FEE_ID, feeId);
                    startActivity(feeDetails);
                }

                break;
        }
    }

    @Override
    public void asyncResponse(String response) {
        try {
            if (loginType.equals(loginTypes[2])) {
                parseCustomersList(response);
            } else {
                parseFeeList(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void parseFeeList(String response) {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(response.toString().getBytes())));
        Type dataListType = new TypeToken<HashMap<String, ArrayList<WardDataModel>>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        HashMap<String, ArrayList<WardDataModel>> wardResponse = (HashMap<String, ArrayList<WardDataModel>>) gson.fromJson(jsonReader, dataListType);
        wardDataModelArrayList = wardResponse.get("warddata");
        if (wardDataModelArrayList.size() > 0) {
            Log.d("ward list size", wardDataModelArrayList.size() + " " + wardDataModelArrayList.get(0).getStudentsDataModel().getId());
            feeListAdapter = new FeeListAdapter(this, wardDataModelArrayList);
            feeListAdapter.setListener(this);
            recyclerViewFee.setAdapter(feeListAdapter);
        } else {
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
    }

    void parseCustomersList(String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            ArrayList<CustomerDataModel> listOfCustomers = new ArrayList<CustomerDataModel>();
            CustomerDataModel customerDataModel;
            Iterator<String> keys = customersObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                    customerDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                            new TypeToken<CustomerDataModel>() {
                            }.getType());
                    listOfCustomers.add(customerDataModel);
                }
            }
            int listSize = listOfCustomers.size();
            if (listSize > 0) {
                CustomersListAdapter customersListAdapter = new CustomersListAdapter(this, listOfCustomers);
                recyclerViewFee.setOnClickListener(this);
                recyclerViewFee.setAdapter(customersListAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_logout) {
            SessionData.saveLoginResponse(null);
            redirectToLoginActivity();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void redirectToLoginActivity() {
        redirectToLoginActivity(null);
//        Intent login = new Intent(this, HomeActivity.class);
//        startActivity(login);
//        finish();
    }
}
