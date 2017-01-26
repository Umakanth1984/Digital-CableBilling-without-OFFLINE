package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Audlink_sri on 12/7/2016.
 */

public class CustomerModel implements Serializable {
    @SerializedName("cust_id")
    private String cust_id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("addr1")
    private String addr1;
    @SerializedName("addr2")
    private String addr2;
    @SerializedName("country")
    private String country;
    @SerializedName("state")
    private String state;
    @SerializedName("ownership")
    private String ownership;
    @SerializedName("pin_code")
    private String pin_code;
    @SerializedName("phone_no")
    private String phone_no;
    @SerializedName("email_id")
    private String email_id;
    @SerializedName("mobile_no")
    private String mobile_no;
    @SerializedName("install_charge")
    private String install_charge;
    @SerializedName("group_id")
    private String group_id;
    @SerializedName("custom_customer_no")
    private String custom_customer_no;
    @SerializedName("mac_id")
    private String mac_id;
    @SerializedName("stb_no")
    private String stb_no;
    @SerializedName("card_no")
    private String card_no;
    @SerializedName("connection_date")
    private String connection_date;
    @SerializedName("dob")
    private String dob;
    @SerializedName("anniversary_date")
    private String anniversary_date;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("amount")
    private String amount;
    @SerializedName("pending_amount")
    private String pending_amount;
    @SerializedName("current_due")
    private String current_due;
    @SerializedName("monthly_bill")
    private String monthly_bill;
    @SerializedName("tax_rate")
    private String tax_rate;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("end_date")
    private String end_date;
    @SerializedName("status")
    private String status;
    @SerializedName("dateCreated")
    private String dateCreated;
    @SerializedName("inactive_date")
    private String inactive_date;


    public String getcust_id() {
        return cust_id;
    }

    public void setcust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getfirst_name() {
        return first_name;
    }

    public void setfirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getlast_name() {
        return last_name;
    }

    public void setlast_name(String emp_last_name) {
        this.last_name = last_name;
    }

    public String getaddr1() {
        return addr1;
    }

    public void setaddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getaddr2() {
        return addr2;
    }

    public void setaddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public String getstate() {
        return state;
    }

    public void setstate(String state) {
        this.state = state;
    }

    public String getownership() {
        return ownership;
    }

    public void setownership(String ownership) {
        this.ownership = ownership;
    }

    public String getpin_code() {
        return pin_code;
    }

    public void setpin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getmobile_no() {
        return mobile_no;
    }

    public void setmobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getemail_id() {
        return email_id;
    }

    public void setemail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getinstall_charge() {
        return install_charge;
    }

    public void setinstall_charge(String install_charge) {
        this.install_charge = install_charge;
    }

    public String getgroup_id() {
        return group_id;
    }

    public void setgroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getcustom_customer_no() {
        return custom_customer_no;
    }

    public void setcustom_customer_no(String custom_customer_no) {
        this.custom_customer_no = custom_customer_no;
    }

    public String getmac_id() {
        return mac_id;
    }

    public void setmac_id(String date_created) {
        this.mac_id = mac_id;
    }

    public String getstb_no() {
        return stb_no;
    }

    public void setstb_no(String stb_no) {
        this.stb_no = stb_no;
    }

    public String getcard_no() {
        return card_no;
    }

    public void setcard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getconnection_date() {
        return connection_date;
    }

    public void setconnection_date(String connection_date) {
        this.connection_date = connection_date;
    }

    public String getdob() {
        return dob;
    }

    public void setdob(String dob) {
        this.dob = dob;
    }

    public String getanniversary_date() {
        return anniversary_date;
    }

    public void setanniversary_date(String anniversary_date) {
        this.anniversary_date = anniversary_date;
    }

    public String getremarks() {
        return remarks;
    }

    public void setremarks(String remarks) {
        this.remarks = remarks;
    }

    public String getamount() {
        return amount;
    }

    public void setamount(String amount) {
        this.amount = amount;
    }

    public String getpending_amount() {
        return pending_amount;
    }

    public void setpending_amount(String pending_amount) {
        this.pending_amount = pending_amount;
    }

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

    public String gettax_rate() {
        return tax_rate;
    }

    public void settax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getstart_date() {
        return start_date;
    }

    public void setstart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getend_date() {
        return end_date;
    }

    public void setend_date(String end_date) {
        this.end_date = end_date;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getdateCreated() {
        return dateCreated;
    }

    public void setdateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getinactive_date() {
        return inactive_date;
    }

    public void setinactive_date(String inactive_date) {
        this.inactive_date = inactive_date;
    }
}
