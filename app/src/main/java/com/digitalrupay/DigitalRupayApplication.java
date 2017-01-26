package com.digitalrupay;

import android.app.Application;
import android.content.Context;

import com.crittercism.app.Crittercism;

/**
 * Created by Santosh on 8/19/2016.
 */


//
public class DigitalRupayApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();
        setAppContext(getApplicationContext());

        //To capture crash report
        try {
            Crittercism.initialize(getApplicationContext(), "a1bec79e4faf413c8ae77c46e4e1086d00555300");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return appContext;
    }

    private static void setAppContext(Context appContext) {
        DigitalRupayApplication.appContext = appContext;
    }
}
