package com.digitalrupay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.adapters.PagerAdapter;
import com.digitalrupay.datamodels.CategoryDataModel;
import com.digitalrupay.datamodels.ComplaintsDataModel;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.fragments.ComplaintsFragment;
import com.digitalrupay.fragments.RegisterComplaintFragment;
import com.digitalrupay.interfaces.CommunicationListener;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Santosh on 10/2/2016.
 */

public class ComplaintsActivity extends BaseActivity implements CommunicationListener, AsyncRequest.OnAsyncRequestComplete {

    Spinner spnCategories;
    EditText edtCustomerID, edtComplaintMsg;
    AsyncRequest asyncRequest = null;
    String category, employeeId;
    boolean isCategory;
    PagerAdapter adapter;
    ComplaintsFragment complaintsFragment;
    RegisterComplaintFragment registerComplaintFragment;
    int serviceRequest;
    ViewPager viewPager;
    ArrayList<CategoryDataModel> categoryDataModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complaints");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Complaints List"));
        tabLayout.addTab(tabLayout.newTab().setText("Register A Complaint"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPos = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                if (tabPos == 0) {
                    complaintsFragment = (ComplaintsFragment) adapter.getFragment(tabPos);
                } else {
                    registerComplaintFragment = (RegisterComplaintFragment) adapter.getFragment(tabPos);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        EmployeeDataModel employeeDataModel = null;
        if(loginType.equals(loginTypes[4])) {
            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
        }else if(loginType.equals(loginTypes[2])) {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
        }
        employeeId = employeeDataModel.getEmp_id();
        getCategories();
        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ComplaintsDataModel complaintsDataModel = ((ComplaintsDataModel) bundle.getSerializable(WsUrlConstants.COMPLAINTS_DATA));
        }

        spnCategories = (Spinner) findViewById(R.id.spnCategories);
        edtCustomerID = (EditText) findViewById(R.id.edtCustomerId);
        edtComplaintMsg = (EditText) findViewById(R.id.edtComplaintMsg);

        spnCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categoryDataModelArrayList.get(position).getId();
                Log.d("category id", category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        isCategory = true;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Categories..");
        asyncRequest.execute(WsUrlConstants.categoriesUrl);*/
    }

    @Override
    public void searchCustomer(String customerID) {
        serviceRequest = 4;
        Log.d("search customer: ", customerID);
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.customerDetailsUrl.replace(WsUrlConstants.CUSTOMER_ID, customerID).replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    @Override
    public void getComplaints() {
        serviceRequest = 1;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.complaintsDetailsUrl.replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    void getCategories() {
        serviceRequest = 2;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Categories..");
        asyncRequest.execute(WsUrlConstants.categoriesUrl);
    }

    @Override
    public void registerComplaint(String customerId, String complaintMsg, String category) {
        serviceRequest = 3;
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Processing Request..");
        asyncRequest.execute(WsUrlConstants.addComplaintUrl.replace(WsUrlConstants.EMPLOYEE_ID, employeeId).replace(WsUrlConstants.CUSTOMER_ID, customerId.replace(" ", "%20"))
                .replace(WsUrlConstants.COMPLAINT_MSG, complaintMsg.replace(" ", "%20")).replace(WsUrlConstants.COMPLAINT_CATEGORY, category));
    }

    @Override
    public void asyncResponse(String response) {
        if (serviceRequest == 1) {
            try {
                JSONObject customersObj = new JSONObject(response);
                ComplaintsDataModel complaintsDataModel = null;
                ArrayList<ComplaintsDataModel> complaintsDataModelArrayList = new ArrayList<>();
                Iterator<String> keys = customersObj.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                        complaintsDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                new TypeToken<ComplaintsDataModel>() {
                                }.getType());
                        complaintsDataModelArrayList.add(complaintsDataModel);
                    }
                }
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                complaintsFragment = (ComplaintsFragment) adapter.getFragment(0);
                if (complaintsFragment != null) {
                    Log.d("complaint size", complaintsDataModelArrayList.size() + "");
                    ArrayList<CategoryDataModel> categoryDataModelArrayList = SessionData.getCategoriesList();
                    if (categoryDataModelArrayList.size() > 0 && complaintsDataModelArrayList.size() > 0) {

                        ArrayList<ComplaintsDataModel> dummyList = new ArrayList<>();
                        for (ComplaintsDataModel complaintsDataModel1 : complaintsDataModelArrayList) {
                            for (CategoryDataModel categoryDataModel : categoryDataModelArrayList) {
                                if (categoryDataModel.getId().contains(complaintsDataModel1.getComp_cat())) {
                                    complaintsDataModel1.setComp_cat(categoryDataModel.getCategory());
                                    break;
                                }
                            }
                            dummyList.add(complaintsDataModel1);
                        }
                        complaintsDataModelArrayList = dummyList;
                    }
                    SessionData.setComplaintsDataModelArrayList(complaintsDataModelArrayList);
                    complaintsFragment.setComplaints(complaintsDataModelArrayList, message, text);
                }
//                getCategories();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (serviceRequest == 2) {
            try {
                JSONObject customersObj = new JSONObject(response);
                CategoryDataModel categoryDataModel = null;
                Iterator<String> keys = customersObj.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                        categoryDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                new TypeToken<CategoryDataModel>() {
                                }.getType());
                        categoryDataModelArrayList.add(categoryDataModel);
                    }
                }
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                if (message.equalsIgnoreCase("success")) {

                    // Spinner Drop down elements
                    ArrayList<String> categories = new ArrayList<String>();
                    for (CategoryDataModel categoryDataModel1 : categoryDataModelArrayList) {
                        categories.add(categoryDataModel1.getCategory());
                    }
                    SessionData.setCategoriesList(categoryDataModelArrayList);
                    registerComplaintFragment = (RegisterComplaintFragment) adapter.getFragment(1);
                    if (registerComplaintFragment != null) {
                        registerComplaintFragment.setCategories(categoryDataModelArrayList);
                    }
                } else {
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                }
                getComplaints();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (serviceRequest == 3) {
            try {
                JSONObject customersObj = new JSONObject(response);
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                if (message.equalsIgnoreCase("success")) {
                    registerComplaintFragment = (RegisterComplaintFragment) adapter.getFragment(1);
//                    registerComplaintFragment.edtCustomerID.setText("");
                    registerComplaintFragment.edtComplaintMsg.setText("");
//                    getComplaints();

                    Intent complaintSuccessActivity = new Intent(this, ComplaintSuccessActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable(WsUrlConstants.PAYMENT_DATA, customerPaymentSuccessModel);
//                    bundle.putSerializable(WsUrlConstants.CUSTOMER_DATA, customerDataModel);
//                    complaintSuccessActivity.putExtras(bundle);
                    startActivityForResult(complaintSuccessActivity, 1000);
                }
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (serviceRequest == 4) {
            try {
                JSONObject customersObj = new JSONObject(response);
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                Iterator<String> keys = customersObj.keys();
                CustomerDataModel customerDataModel = null;
                if (message.equalsIgnoreCase("success")) {
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            customerDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                    new TypeToken<CustomerDataModel>() {
                                    }.getType());
                        }
                    }

                    if (customerDataModel != null) {
                        registerComplaintFragment = (RegisterComplaintFragment) adapter.getFragment(1);
                        registerComplaintFragment.setCustomerData(customerDataModel);
                    }
                }
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getComplaints();
        viewPager.setCurrentItem(0);
    }
}
