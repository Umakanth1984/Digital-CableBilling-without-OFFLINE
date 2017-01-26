package com.digitalrupay.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.activities.ComplaintsActivity;
import com.digitalrupay.datamodels.CategoryDataModel;
import com.digitalrupay.datamodels.CustomerDataModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.SessionData;
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
 * Created by Santosh on 10/6/2016.
 */

public class RegisterComplaintFragment extends Fragment implements AsyncRequest.OnAsyncRequestComplete {

    Spinner spnCategories;
    String category, custId, employeeId;
    public EditText edtCustomerID, edtComplaintMsg, searchCustomer;
    CommunicationListener communicationListener;
    ArrayList<CategoryDataModel> categoriesList;
    TextView tvName, tvAddress, tvMobileNumber;
    int serviceRequest = 1;
    ImageView ivSearch;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicationListener = (CommunicationListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_complaint, container, false);

        spnCategories = (Spinner) view.findViewById(R.id.spnCategories);
//        edtCustomerID = (EditText) view.findViewById(R.id.edtCustomerId);
        edtComplaintMsg = (EditText) view.findViewById(R.id.edtComplaintMsg);

        tvName = (TextView) view.findViewById(R.id.txtName);
        tvAddress = (TextView) view.findViewById(R.id.txtAddress);
        tvMobileNumber = (TextView) view.findViewById(R.id.txtMobileNumber);

        ((FrameLayout) view.findViewById(R.id.fl_payment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerComplaint(v);
            }
        });

        ivSearch = (ImageView) view.findViewById(R.id.imgSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = searchCustomer.getText().toString().trim();
                Log.d("search string", searchString);
                if (searchCustomer != null && searchCustomer.length() > 0) {
                    communicationListener.searchCustomer(searchString);
                } else {
                    Toast.makeText(getActivity(), "Please Enter Customer ID or Mobile number", Toast.LENGTH_SHORT).show();
                }
                ((ComplaintsActivity) getActivity()).hideKeyBoard(v);
            }
        });

        searchCustomer = (EditText) view.findViewById(R.id.searchCustomer);
        searchCustomer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchString = v.getText().toString().trim();
                    Log.d("search string", searchString);
                    if (searchCustomer != null && searchCustomer.length() > 0) {
                        communicationListener.searchCustomer(searchString);
                    } else {
                        Toast.makeText(getActivity(), "Please Enter Customer ID or Mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        categoriesList = SessionData.getCategoriesList();
        setCategories(categoriesList);

        EmployeeDataModel employeeDataModel;
        if(SessionData.getSessionDataInstance().getApartmentLoginResult()!= null) {
            employeeDataModel = SessionData.getSessionDataInstance().getApartmentLoginResult();
        }else {
            employeeDataModel= SessionData.getSessionDataInstance().getEmployeeLoginResult();
        }
        employeeId = employeeDataModel.getEmp_id();
        return view;
    }

    public void searchCustomer(String customerID) {
        serviceRequest = 1;
        Log.d("search customer: ", customerID);
        AsyncRequest asyncRequest = new AsyncRequest(getActivity(), "GET", new ArrayList<NameValuePair>(), "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.customerDetailsUrl.replace(WsUrlConstants.CUSTOMER_ID, customerID).replace(WsUrlConstants.EMPLOYEE_ID, employeeId));
    }

    public void setCategories(final ArrayList<CategoryDataModel> categoriesList) {

        ArrayList<String> categories = new ArrayList<String>();
        if (categoriesList != null && categoriesList.size() > 0) {

            for (CategoryDataModel categoryDataModel1 : categoriesList) {
                categories.add(categoryDataModel1.getCategory());
            }

        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnCategories.setAdapter(dataAdapter);

        spnCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categoriesList.get(position).getId();
                Log.d("category id", category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void registerComplaint(View view) {
//        String customerId = edtCustomerID.getText().toString().trim();
        String complaintMsg = edtComplaintMsg.getText().toString().trim();
        if (custId != null && custId.length() > 0) {
//            if (complaintMsg != null && complaintMsg.length() > 0) {
            communicationListener.registerComplaint(custId, complaintMsg, category);
//            } else {
//                Toast.makeText(getActivity(), "Please Enter Compliant Message", Toast.LENGTH_SHORT).show();
//            }
        } else {
            Toast.makeText(getActivity(), "Please Enter Customer ID", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void asyncResponse(String response) {
        try {
            if (serviceRequest == 1) {
                JSONObject customersObj = new JSONObject(response);
                Log.e("customersObj",""+customersObj);
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
                        custId = customerDataModel.getCust_id();
                        tvName.setText(customerDataModel.getFirst_name().trim() + " " + customerDataModel.getLast_name().trim() + " - (" + customerDataModel.getCustom_customer_no() + ")");
                        tvAddress.setText(customerDataModel.getAddr2() + ", " + customerDataModel.getCity());
                        tvMobileNumber.setText(customerDataModel.getMobile_no());
                    }
                }
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setCustomerData(CustomerDataModel customerDataModel) {
        custId = customerDataModel.getCust_id();
        String firstName = "", lastName = "", customerNumber = "";
        if (customerDataModel.getFirst_name() != null) {
            firstName = customerDataModel.getFirst_name().trim();
        }
        if (customerDataModel.getLast_name() != null) {
            lastName = customerDataModel.getLast_name().trim();
        }
        if (customerDataModel.getCustom_customer_no() != null) {
            customerNumber = customerDataModel.getCustom_customer_no();
        }
        tvName.setText(firstName + " " + lastName + " - (" + customerNumber + ")");
        tvAddress.setText(customerDataModel.getAddr2() + ", " + customerDataModel.getCity());
        tvMobileNumber.setText(customerDataModel.getMobile_no());
    }
}
