package com.digitalrupay.datamodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import com.digitalrupay.DigitalRupayApplication;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Santosh on 8/19/2016.
 */
public class SessionData {

    private static SessionData sessionData;
    public static String DIGITAL_APP = "DIGITALApp";
    public static String SHPREF_KEY_LOGIN_RESPONSE = "SHPREF_KEY_LOGIN_RESPONSE";
    public static String SHPREF_KEY_EMPLOYEE_LOGIN_RESPONSE = "SHPREF_KEY_EMPLOYEE_LOGIN_RESPONSE";
    public static String SHPREF_KEY_CUSTOMER_LOGIN_RESPONSE = "SHPREF_KEY_CUSTOMER_LOGIN_RESPONSE";
    public static String SHPREF_KEY_APARTMENT_LOGIN_RESPONSE = "SHPREF_KEY_APARTMENT_LOGIN_RESPONSE";
    public static String SHPREF_KEY_BLUETOOTH_ADDRESS = "SHPREF_KEY_BLUETOOTH_ADDRESS";
    public static ArrayList<ComplaintsDataModel> complaintsDataModelArrayList = null;
    public static ArrayList<CategoryDataModel> categoriesList;
    public static ArrayList<PaymentHistoryDataModel> paymentHistoryDataModel;
    public static Bitmap logoBitmap;

    /**
     * Create private constructor
     */
    private SessionData() {
    }

    /**
     * Create a static method to get instance.
     */
    public static SessionData getSessionDataInstance() {
        if (sessionData == null) {
            sessionData = new SessionData();
        }
        return sessionData;
    }

    public static void saveLoginResponse(LoginResultModel loginResultModel) {
        SharedPreferences.Editor e = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).edit();
        if (loginResultModel != null) {
            Gson gson = new Gson();
            String json = gson.toJson(loginResultModel);
            e.putString(SHPREF_KEY_LOGIN_RESPONSE, json);
        } else {
            e.putString(SHPREF_KEY_LOGIN_RESPONSE, null);
        }
        e.commit();
    }
    public static void saveApartmentLoginResponse(EmployeeDataModel employeeDataModel) {
        SharedPreferences.Editor e = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).edit();
        if (employeeDataModel != null) {
            Gson gson = new Gson();
            String json = gson.toJson(employeeDataModel);
            e.putString(SHPREF_KEY_APARTMENT_LOGIN_RESPONSE, json);
        } else {
            e.putString(SHPREF_KEY_APARTMENT_LOGIN_RESPONSE, null);
        }
        e.commit();
    }

    public static void saveEmployeeLoginResponse(EmployeeDataModel employeeDataModel) {
        SharedPreferences.Editor e = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).edit();
        if (employeeDataModel != null) {
            Gson gson = new Gson();
            String json = gson.toJson(employeeDataModel);
            e.putString(SHPREF_KEY_EMPLOYEE_LOGIN_RESPONSE, json);
        } else {
            e.putString(SHPREF_KEY_EMPLOYEE_LOGIN_RESPONSE, null);
        }
        e.commit();
    }
    public static void saveCustomerLoginResponse(CustomerModel employeeDataModel) {
        SharedPreferences.Editor e = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).edit();
        if (employeeDataModel != null) {
            Gson gson = new Gson();
            String json = gson.toJson(employeeDataModel);
            e.putString(SHPREF_KEY_CUSTOMER_LOGIN_RESPONSE, json);
        } else {
            e.putString(SHPREF_KEY_CUSTOMER_LOGIN_RESPONSE, null);
        }
        e.commit();
    }

    public static void saveBluetoothAddress(String address) {
        SharedPreferences.Editor e = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).edit();
        e.putString(SHPREF_KEY_BLUETOOTH_ADDRESS, address);
        e.commit();
    }

    public String getBluetoothAddress() {
        return DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_BLUETOOTH_ADDRESS, null);
    }

    public static LoginResultModel getLoginResult() {
        Gson gson = new Gson();
        String json = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_LOGIN_RESPONSE, null);
        LoginResultModel loginResultModel = gson.fromJson(json, LoginResultModel.class);
        return loginResultModel;
    }

    public static EmployeeDataModel getEmployeeLoginResult() {
        Gson gson = new Gson();
        String json = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_EMPLOYEE_LOGIN_RESPONSE, null);
        EmployeeDataModel employeeDataModel = gson.fromJson(json, EmployeeDataModel.class);
        return employeeDataModel;
    }
    public static EmployeeDataModel getApartmentLoginResult() {
        Gson gson = new Gson();
        String json = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_APARTMENT_LOGIN_RESPONSE, null);
        EmployeeDataModel employeeDataModel = gson.fromJson(json, EmployeeDataModel.class);
        return employeeDataModel;
    }
    public static CustomerModel getCustomerLoginResult() {
        Gson gson = new Gson();
        String json = DigitalRupayApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_CUSTOMER_LOGIN_RESPONSE, null);
        CustomerModel customerModel = gson.fromJson(json, CustomerModel.class);
        return customerModel;
    }
    public static ArrayList<ComplaintsDataModel> getComplaintsDataModelArrayList() {
        return complaintsDataModelArrayList;
    }
    public static void setComplaintsDataModelArrayList(ArrayList<ComplaintsDataModel> complaintsDataModelArrayList) {
        SessionData.complaintsDataModelArrayList = complaintsDataModelArrayList;
    }

    public static ArrayList<PaymentHistoryDataModel> getPaymentHistoryModelArrayList() {
        return paymentHistoryDataModel;
    }

    public static void setPaymentHistoryArrayList(ArrayList<PaymentHistoryDataModel> complaintsDataModelArrayList) {
        SessionData.paymentHistoryDataModel= complaintsDataModelArrayList;
    }

    public static ArrayList<CategoryDataModel> getCategoriesList() {
        return categoriesList;
    }

    public static void setCategoriesList(ArrayList<CategoryDataModel> categoriesList) {
        SessionData.categoriesList = categoriesList;
    }

    public static Bitmap getLogoBitmap() {
        return logoBitmap;
    }

    public static void setLogoBitmap(Bitmap logoBitmap) {
        SessionData.logoBitmap = logoBitmap;
    }
}
