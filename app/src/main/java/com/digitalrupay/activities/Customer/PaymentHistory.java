package com.digitalrupay.activities.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.activities.BaseActivity;
import com.digitalrupay.activities.ComplaintSuccessActivity;
import com.digitalrupay.adapters.PaymentPagerAdapter;
import com.digitalrupay.datamodels.CategoryDataModel;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.CustomerModel;
import com.digitalrupay.datamodels.PaymentHistoryDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.activities.Customer.fragment.PaymentHistoryFragment;
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
public class PaymentHistory extends BaseActivity implements  AsyncRequest.OnAsyncRequestComplete,CommunicationListener {
    ViewPager viewPager;
    PaymentPagerAdapter adapter;
    String Customerid;
    AsyncRequest asyncRequest = null;
    PaymentHistoryFragment payHistoryFragment;
    int serviceRequest;
    ArrayList<CategoryDataModel> categoryDataModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymenthistory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.paymenttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.paytab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Payment History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.custpager);
        adapter = new PaymentPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPos = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                payHistoryFragment = (PaymentHistoryFragment) adapter.getFragment(tabPos);
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
        asyncRequest.execute(WsUrlConstants.Customer_Payment_History.replace(WsUrlConstants.Customerid, Customerid));
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
        asyncRequest.execute(WsUrlConstants.addComplaintUrl.replace(WsUrlConstants.EMPLOYEE_ID, Customerid).replace(WsUrlConstants.CUSTOMER_ID, customerId.replace(" ", "%20"))
                .replace(WsUrlConstants.COMPLAINT_MSG, complaintMsg.replace(" ", "%20")).replace(WsUrlConstants.COMPLAINT_CATEGORY, category));
    }

    @Override
    public void asyncResponse(String response) {
        if (serviceRequest == 1) {
            try {
                JSONObject customersObj = new JSONObject(response);
                PaymentHistoryDataModel complaintsDataModel = null;
                ArrayList<PaymentHistoryDataModel> complaintsDataModelArrayList = new ArrayList<>();
                Iterator<String> keys = customersObj.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                        complaintsDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                new TypeToken<PaymentHistoryDataModel>() {
                                }.getType());
                        complaintsDataModelArrayList.add(complaintsDataModel);
                    }
                }
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                payHistoryFragment = (PaymentHistoryFragment) adapter.getFragment(0);
                if (payHistoryFragment != null) {
                    ArrayList<CategoryDataModel> categoryDataModelArrayList = SessionData.getCategoriesList();
                    if (categoryDataModelArrayList.size() > 0 && complaintsDataModelArrayList.size() > 0) {
                        ArrayList<PaymentHistoryDataModel> dummyList = new ArrayList<>();
                        for (PaymentHistoryDataModel complaintsDataModel1 : complaintsDataModelArrayList) {
                            for (CategoryDataModel categoryDataModel : categoryDataModelArrayList) {
//                                if (categoryDataModel.getId().contains(complaintsDataModel1.getComp_cat())) {
//                                    complaintsDataModel1.setComp_cat(categoryDataModel.getCategory());
//                                    break;
//                                }
                            }
                            dummyList.add(complaintsDataModel1);
                        }
                        complaintsDataModelArrayList = dummyList;
                    }
                    SessionData.setPaymentHistoryArrayList(complaintsDataModelArrayList);
                    payHistoryFragment.setComplaints(complaintsDataModelArrayList, message, text);
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
//                    registerComplaintFragment = (RegisterComplaintFragment) adapter.getFragment(1);
//                    if (registerComplaintFragment != null) {
//                        registerComplaintFragment.setCategories(categoryDataModelArrayList);
//                    }
                } else {
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                }
                getComplaints();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (serviceRequest == 3) {
            try {
                JSONObject customersObj = new JSONObject(response);
                String message = customersObj.getString("message");
                String text = customersObj.getString("text");
                if (message.equalsIgnoreCase("success")) {
//                    registerComplaintFragment = (RegisterComplaintFragment) adapter.getFragment(1);
////                    registerComplaintFragment.edtCustomerID.setText("");
//                    registerComplaintFragment.edtComplaintMsg.setText("");
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
//                        registerComplaintFragment = (RegisterComplaintFragment) adapter.getFragment(1);
//                        registerComplaintFragment.setCustomerData(customerDataModel);
                    }
                }
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
