package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Audlink_sri on 12/12/2016.
 */

public class PaymentHistoryDataModel implements Serializable {
    @SerializedName("current_due")
    private String current_due;
    @SerializedName("monthly_bill")
    private String monthly_bill;
    @SerializedName("amount_paid")
    private String amount_paid;
    @SerializedName("transaction_type")
    private String transaction_type;
    @SerializedName("payment_for")
    private String payment_for;
    @SerializedName("dateCreated")
    private String dateCreated;
    @SerializedName("invoice")
    private String invoice;

    public String getcurrent_due() {
        return current_due;
    }

    public void setcurrent_due(String current_due) {
        this.current_due = current_due;
    }

    public String getmonthly_bill() {
        return monthly_bill;
    }

    public void setmonthly_bill(String monthly_bill) {
        this.monthly_bill = monthly_bill;
    }

    public String getamount_paid() {
        return amount_paid;
    }

    public void setamount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String gettransaction_type() {
        return transaction_type;
    }

    public void settransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getpayment_for() {
        return payment_for;
    }

    public void setpayment_for(String transaction_type) {
        this.payment_for = payment_for;
    }

    public String getdateCreated() {
        return dateCreated;
    }

    public void setdateCreated(String transaction_type) {
        this.dateCreated = dateCreated;
    }

    public String getinvoice() {
        return invoice;
    }

    public void setinvoice(String invoice) {
        this.invoice = invoice;
    }
}
