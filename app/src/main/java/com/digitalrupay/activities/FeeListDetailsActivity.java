package com.digitalrupay.activities;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.digitalrupay.R;
import com.digitalrupay.customviews.OAuthWebView;
import com.digitalrupay.datamodels.FeeDataModel;
import com.digitalrupay.datamodels.LoginResultModel;
import com.digitalrupay.datamodels.PaymentDataModel;
import com.digitalrupay.datamodels.SessionData;
import com.digitalrupay.datamodels.UserModel;
import com.digitalrupay.datamodels.WardDataModel;
import com.digitalrupay.network.AsyncRequest;
import com.digitalrupay.network.WsUrlConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import net.smartam.leeloo.client.request.OAuthClientRequest;

import org.apache.http.util.EncodingUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Santosh on 7/5/2016.
 */
public class FeeListDetailsActivity extends BaseActivity implements AsyncRequest.OnAsyncRequestComplete {

    TextView tvStudentName, tvStandard, tvSection, tvBranchName, tvSchoolName, tvFeeIssueDate, tvFeeDueDate, tvTermMonth, tvPaid, tvTransportationFee, tvTuitionFee, tvLibraryFee, tvBooksFee,
            tvUniformFee, tvCanteenFee, tvLateFee, tvOneTimeFee, tvAdmissionFee, tvTotalFee;
    Button btnPay;
    String selectedOption, feeId;
    boolean isPaymentService;
    String oAuthUrl = "";
    ScrollView svFeeDetails;
    WebView wvPayment;
    FrameLayout flPayment;
    int totalFee;
    WardDataModel feeDetails;
    PaymentDataModel paymentDataModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_details);
        setTitle("FeeDetails", true);

        feeId = getIntent().getStringExtra(WsUrlConstants.FEE_ID);
        Log.d("feeId", feeId);
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", null, "Fetching Data..");
        asyncRequest.execute(WsUrlConstants.feeDetailsUrl.replace(WsUrlConstants.FEE_ID, feeId));

        initializeControls();
    }

    void initializeControls() {
        tvStudentName = (TextView) findViewById(R.id.txtStudentName);
        tvStandard = (TextView) findViewById(R.id.txtStandard);
        tvSection = (TextView) findViewById(R.id.txtSection);
        tvBranchName = (TextView) findViewById(R.id.txtBranchName);
        tvSchoolName = (TextView) findViewById(R.id.txtSchoolName);
        tvFeeIssueDate = (TextView) findViewById(R.id.txtFeeIssueDate);
        tvFeeDueDate = (TextView) findViewById(R.id.txtFeeDueDate);
        tvTermMonth = (TextView) findViewById(R.id.txtTermMonth);
        tvPaid = (TextView) findViewById(R.id.txtPaid);
        tvTransportationFee = (TextView) findViewById(R.id.txtTransportationFee);
        tvTuitionFee = (TextView) findViewById(R.id.txtTuitionFee);
        tvLibraryFee = (TextView) findViewById(R.id.txtLibraryFee);
        tvBooksFee = (TextView) findViewById(R.id.txtBooksFee);
        tvUniformFee = (TextView) findViewById(R.id.txtUniformFee);
        tvCanteenFee = (TextView) findViewById(R.id.txtCanteenFee);
        tvLateFee = (TextView) findViewById(R.id.txtLateFee);
        tvOneTimeFee = (TextView) findViewById(R.id.txtOneTimeFee);
        tvAdmissionFee = (TextView) findViewById(R.id.txtAdmissionFee);
        tvTotalFee = (TextView) findViewById(R.id.txtTotalFee);

        btnPay = (Button) findViewById(R.id.btnPayment);

        wvPayment = (WebView) findViewById(R.id.payment_webview);
        svFeeDetails = (ScrollView) findViewById(R.id.scr_feeDtls);
        flPayment = (FrameLayout) findViewById(R.id.flPayment);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void payment() {
        isPaymentService = true;
        AsyncRequest asyncRequest = new AsyncRequest(this, "GET", null, "Processing..");
        asyncRequest.execute(WsUrlConstants.feePaymentUrl.replace(WsUrlConstants.FEE_ID, feeId).replace(WsUrlConstants.PAYMENT_MODE, selectedOption));
    }

    @Override
    public void asyncResponse(String response) {
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(response.toString().getBytes())));
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Type dataListType;
            if (!isPaymentService) {
                dataListType = new TypeToken<HashMap<String, WardDataModel>>() {
                }.getType();
                HashMap<String, WardDataModel> wardResponse = (HashMap<String, WardDataModel>) gson.fromJson(jsonReader, dataListType);
                feeDetails = wardResponse.get("payments");
                Log.d("fee details", feeDetails.toString());
                setData(feeDetails);
            } else {
                dataListType = new TypeToken<PaymentDataModel>() {
                }.getType();
                paymentDataModel = (PaymentDataModel) gson.fromJson(jsonReader, dataListType);
                Log.d("payment data", paymentDataModel.getTotal_amount() + "");
                String paidAmt = "The amount " + paymentDataModel.getTotal_amount() + " has been successfully paid.";
                showPaymentSuccess(paidAmt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setData(WardDataModel feeDetails) {

//        ((TableLayout) findViewById(R.id.feeDetails)).setVisibility(View.VISIBLE);
//        tvStandard.setText("Standard Name: " + feeDetails.getStandard().getName());
//        tvSection.setText("Section Name: " + feeDetails.getSection().getName());
        tvBranchName.setText(/*"Branch Name: " + */feeDetails.getBranch().getName());
        tvSchoolName.setText(/*"School Name: " + */feeDetails.getBranch().getSchool_name() + ",");
        totalFee = feeDetails.getPayment_data().getAmount();
        if (totalFee != 0) {
            tvTotalFee.setText(" Rs." + totalFee + ".00");
        } else {
            tvTotalFee.setText(" Rs." + "0.00");
        }

        FeeDataModel feeDataModel = feeDetails.getOthers().getFeeObject();
        if (feeDataModel != null) {
            tvStudentName.setText("Student Name: " + feeDataModel.getStudent().getName()
                    + "," + feeDetails.getStandard().getName() + "-" +
                    feeDetails.getSection().getName());
            feeDataModel = feeDataModel.getFee();
            tvFeeIssueDate.setText("Issued on: " + feeDataModel.getIssue_date());
            tvFeeDueDate.setText(" - Due Date: " + feeDataModel.getDue_date());
            tvTermMonth.setText("Term : " + feeDataModel.getTerm_month());
            String feePaid = feeDataModel.getPaid();
            if (feePaid.equals("0")) {
                tvPaid.setText("Fee Paid: Not Yet");
                btnPay.setEnabled(true);
                btnPay.setClickable(true);
            } else {
                tvPaid.setText("Fee Paid: Done");
                btnPay.setEnabled(false);
                btnPay.setClickable(false);
            }
            String transportationFee = feeDataModel.getTransportation_fee();
            if (transportationFee != null) {
                tvTransportationFee.setText(transportationFee);
            }

            String tutionFee = feeDataModel.getTuition_fee();
            if (tutionFee != null) {
                tvTuitionFee.setText(tutionFee);
            } else {
                tvTuitionFee.setText("0.00");
            }

            String libraryFee = feeDataModel.getLibrary_fee();
            if (libraryFee != null) {
                tvLibraryFee.setText(libraryFee);
            } else {
                tvLibraryFee.setText("0.00");
            }

            String booksFee = feeDataModel.getBooks_fee();
            if (booksFee != null) {
                tvBooksFee.setText(booksFee);
            } else {
                tvBooksFee.setText("0.00");
            }

            String uniformFee = feeDataModel.getUniform_fee();
            if (uniformFee != null) {
                tvUniformFee.setText(uniformFee);
            } else {
                tvUniformFee.setText("0.00");
            }

            String canteenFee = feeDataModel.getCanteen_fee();
            if (canteenFee != null) {
                tvCanteenFee.setText(canteenFee);
            } else {
                tvCanteenFee.setText("0.00");
            }

            String lateFee = feeDataModel.getLate_fee();
            if (lateFee != null) {
                tvLateFee.setText(lateFee);
            } else {
                tvLateFee.setText("0.00");
            }

            String oneTimeFee = feeDataModel.getOnetime_fee();
            if (oneTimeFee != null) {
                tvOneTimeFee.setText(oneTimeFee);
            } else {
                tvOneTimeFee.setText("0.00");
            }

            String admissionFee = feeDataModel.getAdmission_fee();
            if (admissionFee != null) {
                tvAdmissionFee.setText(admissionFee);
            } else {
                tvAdmissionFee.setText("0.00");
            }
        }
    }

    public void showPaymentOptionsDialog(View view) {
        final Dialog dialog = new Dialog(this, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_payment_options);
        dialog.setCancelable(true);

        final RadioGroup paymentOptionsGroup = (RadioGroup) dialog.findViewById(R.id.radioPaymentOptions);
        ((Button) dialog.findViewById(R.id.btnProceed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedOptionId = paymentOptionsGroup.getCheckedRadioButtonId();

                RadioButton selectedButton = (RadioButton) dialog.findViewById(selectedOptionId);
                String creditCard = getResources().getString(R.string.credit_card);
                String debitCard = getResources().getString(R.string.debit_card);
                if (selectedButton.getText().equals(creditCard)) {
                    selectedOption = "1";
                } else if (selectedButton.getText().equals(debitCard)) {
                    selectedOption = "2";
                } else {
                    selectedOption = "3";
                }
                Log.d("selected Option", selectedOption);
                payment();
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.btnCancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showPaymentSuccess(String message) {
        final Dialog dialog = new Dialog(this, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_payment_success);
        dialog.setCancelable(true);

        ((TextView) dialog.findViewById(R.id.txtPaymentSuccess)).setText(message);
        ((Button) dialog.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                oAuth();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    public void oAuth() {
        svFeeDetails.setVisibility(View.GONE);
        flPayment.setVisibility(View.VISIBLE);

        OAuthWebView webViewClient = new OAuthWebView();
        webViewClient.setContext(this, progressBar);

        wvPayment.clearCache(true);
        wvPayment.clearHistory();
        wvPayment.setWebViewClient(webViewClient);
        wvPayment.getSettings().setDatabaseEnabled(true);
        wvPayment.getSettings().setJavaScriptEnabled(true);
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        try {
            LoginResultModel loginResultModel = SessionData.getLoginResult();
            UserModel userModel = loginResultModel.getDetails().getUserModel();
            OAuthClientRequest request = OAuthClientRequest
                    .authorizationLocation("https://www.emvantage.com/SecurePG/Pay/ProcessPayment.aspx")
//                    .setClientId("").setRedirectURI("https://www.digitalrupay.com/rupay/fees/pay_return/26")
                    .setParameter("ME_ReturnURL", "https://www.digitalrupay.com/rupay/fees/pay_return/26")
                    .setParameter("Version", "1.5")
                    .setParameter("API_KEY", "BC5D2083BEDAF337B6C28B575DCED03C")
                    .setParameter("MerchToken", "8A8708F9B148E822B46F39005BF13430")
                    .setParameter("MerchantID", /*feeDetails.getOthers().getSchool().getSchool().getMerchantid()*/"650716297801432")
//                    .setParameter("ME_RFU4", /*"111"*/feeDetails.getOthers().getSchool().getSchool().getMerchantid())
                    .setParameter("TxnAmount", String.valueOf(paymentDataModel.getTotal_amount()))
                    .setParameter("ME_OrderID", "57d0f9b7afb26")
                    .setParameter("ME_TxnID", /*"57d0f9b7af677"*/paymentDataModel.getTxn_id())
                    .setParameter("CurrencyCode", "356")
                    .setParameter("CountryCode", "356")
                    .setParameter("ItemsPurchased", "Fee for Challenger High School")
                    .setParameter("ItemQuantity", "1")
                    .setParameter("CustomerName", userModel.getUser_name())
                    .setParameter("CustomerEmail", userModel.getUser_email())
                    .setParameter("CustomerMobile", userModel.getMobile())
                    .setParameter("ThumbPrint", paymentDataModel.getCheck_sum())
                    .buildQueryMessage();
            oAuthUrl = Uri.parse(request.getLocationUri() + "&response_type=code").toString();
            Log.d("web url", oAuthUrl);
//            String payUrl = "https://www.digitalrupay.com/rupay/fees/pay_return/26&" +
//                    "Version=1.5&" + "API_KEY=BC5D2083BEDAF337B6C28B575DCED03C&" + "MerchToken=8A8708F9B148E822B46F39005BF13430&" +
//                    "MerchantID=650716297801432&" + "ME_RFU4=" + /*"111"*/feeDetails.getOthers().getSchool().getSchool().getMerchantid() + "&" +
//                    "TxnAmount=" + String.valueOf(paymentDataModel.getTotal_amount()) + "&" + "ME_OrderID=57d0f9b7afb26&" +
//                    /*"transactionId="*/ "ME_TxnID=" + "57d0f9b7af677"/*paymentDataModel.getTxn_id()*/ + "&" + "CurrencyCode=356&" + "CountryCode=356&" +
//                    "ItemsPurchased=Fee%20for%20Challenger%20High%20School&" + "ItemQuantity=1&" + "CustomerName=" + userModel.getUser_name() + "&" +
//                    "CustomerEmail=" + userModel.getUser_email() + "&" + "CustomerMobile=" + userModel.getMobile() + "&" +
//                    "ThumbPrint=" + paymentDataModel.getCheck_sum();
//            Log.d("payment url", payUrl);

            wvPayment.postUrl("https://www.emvantage.com/SecurePG/Pay/ProcessPayment.aspx", EncodingUtils.getBytes(oAuthUrl, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return oAuthUrl;
    }
}
