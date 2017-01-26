package com.digitalrupay.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.network.WsUrlConstants;
import com.digitalrupay.utils.ConnectionDetector;

/**
 * Created by Santosh on 7/5/2016.
 */
public class BaseActivity extends AppCompatActivity {
    ConnectionDetector connectionDetector;
    public String loginType,custloginType;
    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionDetector = new ConnectionDetector(this);
        loginType = getIntent().getStringExtra(WsUrlConstants.LOGIN_TYPE);
        custloginType = getIntent().getStringExtra(WsUrlConstants.CUSTLOGIN_TYPE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void setTitle(String title, boolean isBackEnable) {
        getSupportActionBar().setTitle(title);
        if (isBackEnable) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /*ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                    | ActionBar.DISPLAY_SHOW_CUSTOM);
            ImageView imageView = new ImageView(actionBar.getThemedContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            Bitmap logo = SessionData.getSessionDataInstance().getLogoBitmap();
            if (logo != null) {
                imageView.setImageBitmap(logo);
            }
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                    100,
                    100, Gravity.RIGHT
                    | Gravity.CENTER_VERTICAL);
            layoutParams.rightMargin = 40;
            imageView.setLayoutParams(layoutParams);
            actionBar.setCustomView(imageView);*/
        }
    }

    public void finishAllActivities() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.logout, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        MenuItem userItem = menu.findItem(R.id.action_logo);

        //get your bitmap
        Bitmap logo = SessionData.getSessionDataInstance().getLogoBitmap();
        if (logo != null) {
            Bitmap bitmap = Bitmap.createScaledBitmap(logo, 100, 100, false);
            userItem.setIcon(resizeImage(logo, 200, 200));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Drawable resizeImage(Bitmap bitmap, int w, int h) {
        // load the origial Bitmap
//        Bitmap BitmapOrg = BitmapFactory.decodeResource(getResources(), resId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = w;
        int newHeight = h;
        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

    protected boolean checkNetworkConnection() {
        if (connectionDetector.isConnectingToInternet()) {
            return true;
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void redirectToLoginActivity(View view) {
        AlertDialog logoutDialog = new AlertDialog.Builder(this).setTitle("Logout")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
    }

    public void redirectToHomeActivity(View view) {
        AlertDialog homeDialog = new AlertDialog.Builder(this).setTitle("Home")
                .setMessage("Are you sure want to navigate to home screen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        home();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
    }

    private void logout() {
        SessionData.saveLoginResponse(null);
        SessionData.saveEmployeeLoginResponse(null);
        SessionData.setComplaintsDataModelArrayList(null);
        SessionData.setCategoriesList(null);
        SessionData.saveCustomerLoginResponse(null);
        SessionData.saveApartmentLoginResponse(null);
        Intent login = new Intent(this, HomeActivity.class);
        login.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
        finish();
    }

    private void home() {
        Intent home = new Intent(this, CableBillingActivity.class);
        home.putExtra(WsUrlConstants.LOGIN_TYPE, loginType);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(home);
        finish();
    }

    public void hideKeyBoard(View view) {

        Log.v("hide keyboard", "hide keyboard");
        if (this != null && view != null) {
            imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            Log.v("hide keyboard", "hide keyboard");
            if (view instanceof AutoCompleteTextView)
                view.setFocusable(true);
        }

    }
}
