package com.digitalrupay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.digitalrupay.R;
import com.digitalrupay.network.WsUrlConstants;

import java.util.Calendar;

import static com.digitalrupay.network.WsUrlConstants.loginTypes;

/**
 * Created by Santosh on 9/4/2016.
 */
public class HomeActivity extends BaseActivity {

    String userId, loginType;
    TextView txtTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtTitle = (TextView) findViewById(R.id.tv_title);
        txtTitle.setText(getTimeFromMobile());
    }

    String getTimeFromMobile() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String message = "";
        Log.d("time of day", timeOfDay + "");

        if (timeOfDay >= 4 && timeOfDay < 12) {
            message = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            message = "Good Afternoon";
        } else if (timeOfDay >= 16 || timeOfDay < 4) {
            message = "Good Evening";
        }/* else if (timeOfDay >= 21 && timeOfDay < 24) {
            Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
        }*/
        return message;
    }

    public void loginView(View v) {
        switch (v.getId()) {
            case R.id.fl_school:
                loginType = loginTypes[0];
                break;
            case R.id.fl_paper_billing:
                loginType = loginTypes[1];
                break;
            case R.id.fl_cable_billing:
                loginType = loginTypes[2];
                break;
            case R.id.fl_apartment_seva:
                loginType = loginTypes[4];
                break;
        }
        Intent login = new Intent(this, LoginTypeActivity.class);
        login.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        startActivity(login);
    }

}
