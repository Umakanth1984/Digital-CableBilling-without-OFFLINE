package com.digitalrupay.activities.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.activities.BaseActivity;
import com.digitalrupay.activities.ComplaintSuccessActivity;
import com.digitalrupay.adapters.CustomerPagerAdapter;
import com.digitalrupay.datamodels.CategoryDataModel;
import com.digitalrupay.datamodels.ComplaintsDataModel;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.CustomerModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.activities.Customer.fragment.ComplaintsHistoryFragment;
import com.digitalrupay.activities.Customer.fragment.CustomersComplaintsFragment;
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

/**
 * Created by Audlink_sri on 12/11/2016.
 */

public class ComplaintsHistory extends BaseActivity implements  AsyncRequest.OnAsyncRequestComplete,CommunicationListener{
    ViewPager viewPager;
    CustomerPagerAdapter adapter;
    String Customerid;
    AsyncRequest asyncRequest = null;
    ComplaintsHistoryFragment complaintsHistoryFragment;
    CustomersComplaintsFragment customersComplaintFragment;
    int serviceRequest;
    ArrayList<CategoryDataModel> categoryDataModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaintshisotry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complaints");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.custtab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Complaints List"));
        tabLayout.addTab(tabLayout.newTab().setText("Register A Complaint"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.custpager);
        adapter = new CustomerPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPos = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                if (tabPos == 0) {
                complaintsHistoryFragment = (ComplaintsHistoryFragment) adapter.getFragment(tabPos);
                } else {
                    customersComplaintFragment = (CustomersComplaintsFragment) adapter.getFragment(tabPos);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        CustomerModel customerModel = SessionData.getCustomerLoginResult();
        Customerid=customerModel.getcust_id();
        getCategories();
    }

    @Override
    public void searchCustomer(String customerID) {

    }
    @Override
    public void getComplaints() {
        serviceRequest = 1;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.Customer_Complaints_History.replace(WsUrlConstants.Customerid, Customerid));
    }

    void getCategories() {
        serviceRequest = 2;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Categories..");
        asyncRequest.execute(WsUrlConstants.CustomercategoriesUrl);
    }

    @Override
    public void registerComplaint(String customerId, String complaintMsg, String category) {
        serviceRequest = 3;
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Processing Request..");
        asyncRequest.execute(WsUrlConstants.Customer_Complaints.replace(WsUrlConstants.Customerid, customerId)
                .replace(WsUrlConstants.Custcomplaint, complaintMsg.replace(" ", "%20")).replace(WsUrlConstants.Custcomp_cat, category));
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
                complaintsHistoryFragment = (ComplaintsHistoryFragment) adapter.getFragment(0);
                if (complaintsHistoryFragment != null) {
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
                    complaintsHistoryFragment.setComplaints(complaintsDataModelArrayList, message, text);
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
                    customersComplaintFragment = (CustomersComplaintsFragment) adapter.getFragment(1);
                    if (customersComplaintFragment != null) {
                        customersComplaintFragment.setCategories(categoryDataModelArrayList);
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
//                    customersComplaintFragment = (CustomersComplaintsFragment) adapter.getFragment(1);
////                    registerComplaintFragment.edtCustomerID.setText("");
                    customersComplaintFragment.edtComplaintMsg.setText("");
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
                CustomerModel customerDataModel = null;
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
                        customersComplaintFragment = (CustomersComplaintsFragment) adapter.getFragment(1);
                        customersComplaintFragment.setCustomerData(customerDataModel);
                    }
                }
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
