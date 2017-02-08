package com.digitalrupay.activities.Customer.fragment;

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
import com.digitalrupay.datamodels.CustomerModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.interfaces.CommunicationListener;
import com.digitalrupay.network.AsyncRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Audlink_sri on 12/14/2016.
 */

public class CustomersComplaintsFragment extends Fragment implements AsyncRequest.OnAsyncRequestComplete {

    Spinner spnCategories;
    String category, custId;
    public EditText edtComplaintMsg;
    CommunicationListener communicationListener;
    ArrayList<CategoryDataModel> categoriesList;
    TextView tvName, tvAddress, tvMobileNumber;
    int serviceRequest = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicationListener = (CommunicationListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customercomplanit, container, false);

        spnCategories = (Spinner) view.findViewById(R.id.spncustCategories);
        edtComplaintMsg = (EditText) view.findViewById(R.id.edtcustComplaintMsg);
        tvName = (TextView) view.findViewById(R.id.txtName);
        tvAddress = (TextView) view.findViewById(R.id.txtAddress);
        tvMobileNumber = (TextView) view.findViewById(R.id.txtMobileNumber);

        ((FrameLayout) view.findViewById(R.id.fl_payment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerComplaint(v);
            }
        });
        categoriesList = SessionData.getCategoriesList();
        setCategories(categoriesList);

        CustomerModel employeeDataModel = SessionData.getCustomerLoginResult();
        custId = employeeDataModel.getcust_id();
        return view;
    }
    public void setCategories(final ArrayList<CategoryDataModel> categoriesList) {

        ArrayList<String> categories = new ArrayList<String>();
        if (categoriesList != null && categoriesList.size() > 0) {

            for (CategoryDataModel categoryDataModel1 : categoriesList) {
                categories.add(categoryDataModel1.getCategory());
            }

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        String complaintMsg = edtComplaintMsg.getText().toString().trim();
        if (custId != null && custId.length() > 0) {
            communicationListener.registerComplaint(custId, complaintMsg, category);
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

    public void setCustomerData(CustomerModel customerDataModel) {
        custId = customerDataModel.getcust_id();
        String firstName = "", lastName = "", customerNumber = "";
        if (customerDataModel.getfirst_name() != null) {
            firstName = customerDataModel.getfirst_name().trim();
        }
        if (customerDataModel.getlast_name() != null) {
            lastName = customerDataModel.getlast_name().trim();
        }
        if (customerDataModel.getcustom_customer_no() != null) {
            customerNumber = customerDataModel.getcustom_customer_no();
        }
        tvName.setText(firstName + " " + lastName + " - (" + customerNumber + ")");
        tvAddress.setText(customerDataModel.getaddr1() + ", " + customerDataModel.getaddr2());
        tvMobileNumber.setText(customerDataModel.getmobile_no());
    }
}