package com.digitalrupay.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.digitalrupay.R;
import com.digitalrupay.datamodels.DetailsModel;
import com.digitalrupay.datamodels.EmployeeDataModel;
import com.digitalrupay.datamodels.LoginResultModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.datamodels.UserModel;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

public class LoginActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete {

    TextInputLayout userName, password, email;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(WsUrlConstants.LOGIN_TYPE, loginType);
        userName = (TextInputLayout) findViewById(R.id.text_input_layout_user_name);
        password = (TextInputLayout) findViewById(R.id.text_input_layout_password);
        email = (TextInputLayout) findViewById(R.id.text_input_layout_email);
        if (loginType.equals(loginTypes[2])) {
            userName.setVisibility(View.GONE);
        }else if( loginType.equals(loginTypes[3]) ){
            userName.setVisibility(View.GONE);
        }else if( loginType.equals(loginTypes[4]) ){
            userName.setVisibility(View.GONE);
        }
        else {
            email.setVisibility(View.GONE);
        }

    }

    public void checkLoginCredentials(View view) {
        String username = userName.getEditText().getText().toString().trim();
        String pwd = password.getEditText().getText().toString().trim();
        if (loginType.equals(loginTypes[2])) {
            username = email.getEditText().getText().toString().trim();
        }else if (loginType.equals(loginTypes[3])) {
            username = email.getEditText().getText().toString().trim();
        }else if (loginType.equals(loginTypes[4])) {
            username = email.getEditText().getText().toString().trim();
        }
        if (username != null && username.length() > 0) {
            if (pwd != null && pwd.length() > 0) {
                if (checkNetworkConnection()) {
                    AsyncRequest getPosts = new AsyncRequest(this, "GET", null, "Validating User..");
                    if (loginType.equals(loginTypes[2])) {
                        getPosts.execute(WsUrlConstants.employeeLoginUrl.replace(WsUrlConstants.EMAIL, username).replace(WsUrlConstants.PASSWORD, pwd));
                    }else if (loginType.equals(loginTypes[4])) {
                    getPosts.execute(WsUrlConstants.employeeLoginUrl.replace(WsUrlConstants.EMAIL, username).replace(WsUrlConstants.PASSWORD, pwd));
                } else {
                        getPosts.execute(WsUrlConstants.loginUrl.replace(WsUrlConstants.USERNAME, username).replace(WsUrlConstants.PASSWORD, pwd));
                    }
                }
            } else {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        }
    }

    public void redirectToFeeListActivity(String parentId) {
        Intent feeList = new Intent(this, FeeListActivity.class);
        feeList.putExtra(WsUrlConstants.PARENT_ID, parentId);
        feeList.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        feeList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(feeList);
        finish();
    }

    public void redirectToCableBillingActivity(String parentId) {
        Intent feeList = new Intent(this, CableBillingActivity.class);
        feeList.putExtra(WsUrlConstants.PARENT_ID, parentId);
        feeList.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        feeList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(feeList);
        finish();
    }

    private ArrayList<NameValuePair> getParams() {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("start", "0"));
        params.add(new BasicNameValuePair("limit", "10"));
        params.add(new BasicNameValuePair("fields", "id,title"));
        return params;
    }
    @Override
    public void asyncResponse(String response) {
        if (response != null) {
                try {
                    JSONObject responseObj = new JSONObject(response);
                    String message = "";
                    if (loginType.equals(loginTypes[2])) {
                        message = responseObj.getString("message");
                        if (message.equalsIgnoreCase("success")) {
                            parseEmployeeResponse(responseObj);
                            return;
                        }
                    } else if (loginType.equals(loginTypes[3])) {
                        message = responseObj.getString("message");
                        if (message.equalsIgnoreCase("success")) {
                            parseEmployeeResponse(responseObj);
                            return;
                        }
                    } else if (loginType.equals(loginTypes[4])) {
                        message = responseObj.getString("message");
                        if (message.equalsIgnoreCase("success")) {
                            parseEmployeeResponse(responseObj);
                            return;
                        }
                    } else {
                        JSONObject loginObject = responseObj.getJSONObject("LoginResponse");
                        message = loginObject.getString("message");
                    }

                    Log.d("message", message);
                    if (message.equalsIgnoreCase("success")) {
                        JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(response.toString().getBytes())));
                        Type dataListType = new TypeToken<HashMap<String, LoginResultModel>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        HashMap<String, LoginResultModel> loginResponse = (HashMap<String, LoginResultModel>) gson.fromJson(jsonReader, dataListType);
                        LoginResultModel loginResultModel = loginResponse.get("LoginResponse");
                        SessionData.getSessionDataInstance().saveLoginResponse(loginResultModel);
                        DetailsModel detailsModel = ((DetailsModel) loginResultModel.getDetails());
                        UserModel userModel = detailsModel.getUserModel();
                        redirectToFeeListActivity(userModel.getId());
                    } else {
                        Toast.makeText(this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    public void parseEmployeeResponse(JSONObject empObj) {
        try {
            EmployeeDataModel employeeDataModel;
            Iterator<String> keys = empObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Log.e("key id", key);
                if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                    employeeDataModel = new Gson().fromJson(empObj.getJSONObject(key).toString(),
                            new TypeToken<EmployeeDataModel>() {
                            }.getType());
                    if (loginType.equals(loginTypes[4])) {
                        SessionData.getSessionDataInstance().saveApartmentLoginResponse(employeeDataModel);
                    }else if((loginType.equals(loginTypes[2]))) {
                        SessionData.getSessionDataInstance().saveEmployeeLoginResponse(employeeDataModel);
                    }
                    redirectToCableBillingActivity(employeeDataModel.getEmp_id());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
