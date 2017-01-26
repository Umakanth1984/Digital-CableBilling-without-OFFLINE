package com.digitalrupay.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.digitalrupay.R;
import com.digitalrupay.adapters.PagerAdapter;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.fragments.ComplaintsFragment;
import com.digitalrupay.fragments.CustomersFragment;
import com.digitalrupay.interfaces.CommunicationListener;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Santosh on 10/1/2016.
 */

public class CableBillingActivity extends BaseActivity implements CommunicationListener, AsyncRequest.OnAsyncRequestComplete {

//    CustomersFragment customersFragment;
//    ComplaintsFragment complaintsFragment;
//    PagerAdapter adapter;
    AsyncRequest asyncRequest;
    boolean isCustomer;
    String employeeId;
    ImageView ivLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cable_billing);
        if(loginType.equals(loginTypes[2])){
            setTitle("Cable Billing System", false);
        }else if(loginType.equals(loginTypes[4])){
            setTitle("Gated Community Billings", false);
        }
        ivLogo = (ImageView) findViewById(R.id.logo);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Customers"));
        tabLayout.addTab(tabLayout.newTab().setText("Complaints"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
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
                    customersFragment = ((CustomersFragment) adapter.getFragment(tabPos));
                    complaintsFragment = null;
                } else {
                    complaintsFragment = (ComplaintsFragment) adapter.getFragment(tabPos);
                    customersFragment = null;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
*/
        EmployeeDataModel employeeDataModel=null;
        if(loginType.equals(loginTypes[4])) {
            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
        }else if(loginType.equals(loginTypes[2])) {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
        }
        employeeId = employeeDataModel.getEmp_id();
        TextView tvEmpName = (TextView) findViewById(R.id.tvEmpName);
        tvEmpName.setText(employeeDataModel.getEmp_first_name() + " " + employeeDataModel.getEmp_last_name());
        TextView tvEmpMobile = (TextView) findViewById(R.id.tvEmpMobile);
        tvEmpMobile.setText("(" + employeeDataModel.getEmp_mobile_no() + ")");

    }
    @Override
    public void searchCustomer(String customerID) {
        isCustomer = true;
        Log.d("search customer: ", customerID);
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.customerDetailsUrl.replace(WsUrlConstants.CUSTOMER_ID, customerID).replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    @Override
    public void asyncResponse(String response) {
        parseCustomerDetails(response);
    }

    void parseCustomerDetails(final String response) {
        try {
            JSONObject customersObj = new JSONObject(response);
            JSONObject inOBJ=customersObj.getJSONObject("0");
//            String message = customersObj.getString("message");
//            String text = customersObj.getString("text");
            String business_name=inOBJ.getString("business_name");
            String address1=inOBJ.getString("address1");
            String address2="";
            if(!inOBJ.isNull("address2")){
                address2 =inOBJ.getString("address2");
            }
            String email="";
            if(!inOBJ.isNull("email")){
                email=inOBJ.getString("email");
            }
            String city=inOBJ.getString("city");
            String state=inOBJ.getString("state");
            String mobile=inOBJ.getString("mobile");

            final String businessLogo = customersObj.getString("business_logo");
            JSONObject businessObj = new JSONObject(customersObj.getJSONObject("0").toString());
            WsUrlConstants.SERVICES_name = businessObj.getString("business_name");
            WsUrlConstants.SERVICE_MOBILE_NUMBER = businessObj.getString("mobile");
            WsUrlConstants.Adderss=address1+"\n "+city;
            Glide.with(CableBillingActivity.this).load(businessLogo).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .signature(new StringSignature("login_updating")).placeholder(R.mipmap.ic_digital_rupay)
                    .error(R.mipmap.ic_digital_rupay).into(new BitmapImageViewTarget(ivLogo) {
                @Override
                protected void setResource(Bitmap resource) {
                    Log.d("sucess", resource.toString());
                    SessionData.getSessionDataInstance().setLogoBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    Log.d("error", e.getMessage());
                }
            });
            /*CustomerDataModel customerDataModel = null;
            ComplaintsDataModel complaintsDataModel = null;
            ArrayList<ComplaintsDataModel> complaintsDataModelArrayList = new ArrayList<>();
            Iterator<String> keys = customersObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                    if (isCustomer) {
                        customerDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                new TypeToken<CustomerDataModel>() {
                                }.getType());
                    } else {
                        complaintsDataModel = new Gson().fromJson(customersObj.getJSONObject(key).toString(),
                                new TypeToken<ComplaintsDataModel>() {
                                }.getType());
                        complaintsDataModelArrayList.add(complaintsDataModel);
                    }
                }
            }
            customersFragment = (CustomersFragment) adapter.getFragment(0);
            if (customersFragment != null) {
                if (isCustomer)
                    customersFragment.setCustomerData(customerDataModel, message, text);
            }
            complaintsFragment = (ComplaintsFragment) adapter.getFragment(1);
            if (complaintsFragment != null) {
                Log.d("complaint size", complaintsDataModelArrayList.size() + "");
                SessionData.setComplaintsDataModelArrayList(complaintsDataModelArrayList);
                if (!isCustomer)
                    complaintsFragment.setComplaints(complaintsDataModelArrayList, message, text);
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getComplaints() {
        isCustomer = false;
        asyncRequest = new AsyncRequest(this, "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.complaintsDetailsUrl.replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    @Override
    public void registerComplaint(String customerId, String complaintMsg, String category) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == WsUrlConstants.COMPLAINTS_CODE) {
                if (data.getBooleanExtra(WsUrlConstants.RESULT, false)) {
                    //refresh posts in activity feed
                    getComplaints();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void navigateToCollections(View view) {
        Intent collections = new Intent(this, CollectionsActivity.class);
        collections.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        startActivity(collections);
    }

    public void navigateToComplaints(View view) {
        SessionData.setComplaintsDataModelArrayList(null);
        SessionData.setCategoriesList(null);
        Intent complaints = new Intent(this, ComplaintsActivity.class);
        complaints.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        startActivity(complaints);
    }

    public void navigateToChangeMobileNumber(View view) {
        Intent changeMobile = new Intent(this, ChangeMobileNumberActivity.class);
        changeMobile.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        startActivity(changeMobile);
    }
    public void navigateToBluetoothdevicessettings(View view) {
        Intent changeMobile = new Intent(this, Menu_2ST_Activity.class);
        changeMobile.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        startActivity(changeMobile);
    }

}
