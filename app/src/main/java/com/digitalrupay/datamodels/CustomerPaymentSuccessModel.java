package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 10/2/2016.
 */

public class CustomerPaymentSuccessModel implements Serializable {

    @SerializedName("payment_id")
    private String payment_id;
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("isAdjustment")
    private String isAdjustment;
    @SerializedName("amount_paid")
    private String amount_paid;
    @SerializedName("transaction_type")
    private String transaction_type;
    @SerializedName("invoice")
    private String invoice;
    @SerializedName("cheque_number")
    private String cheque_number;
    @SerializedName("bank")
    private String bank;
    @SerializedName("branch")
    private String branch;
    @SerializedName("instrument_date")
    private String instrument_date;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("dateCreated")
    private String dateCreated;

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getIsAdjustment() {
        return isAdjustment;
    }

    public void setIsAdjustment(String isAdjustment) {
        this.isAdjustment = isAdjustment;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCheque_number() {
        return cheque_number;
    }

    public void setCheque_number(String cheque_number) {
        this.cheque_number = cheque_number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getInstrument_date() {
        return instrument_date;
    }

    public void setInstrument_date(String instrument_date) {
        this.instrument_date = instrument_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
