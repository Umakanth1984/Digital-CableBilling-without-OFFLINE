package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class CustomerDataModel implements Serializable {

    @SerializedName("cust_id")
    private String cust_id;
    @SerializedName("business_name")
    private String business_name;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("addr1")
    private String addr1;
    @SerializedName("addr2")
    private String addr2;
    @SerializedName("monthly_bill")
    private String monthly_bill;
    @SerializedName("addr3")
    private String addr3;
    @SerializedName("country")
    private String country;
    @SerializedName("state")
    private String state;
    @SerializedName("city")
    private String city;
    @SerializedName("pin_code")
    private String pin_code;
    @SerializedName("phone_no")
    private String phone_no;
    @SerializedName("mobile_no")
    private String mobile_no;
    @SerializedName("email_id")
    private String email_id;
    @SerializedName("group_id")
    private String group_id;
    @SerializedName("custom_customer_no")
    private String custom_customer_no;
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
    @SerializedName("mac_id")
    private String mac_id;
    @SerializedName("stb_no")
    private String stb_no;
    @SerializedName("card_no")
    private String card_no;


    public String getMac_id() {
        return mac_id;
    }

    public void setMac_id(String mac_id) {
        this.mac_id = mac_id;
    }

    public String getStb_no() {
        return stb_no;
    }

    public void setStb_no(String stb_no) {
        this.cust_id = stb_no;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }


    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getCustom_customer_no() {
        return custom_customer_no;
    }

    public void setCustom_customer_no(String custom_customer_no) {
        this.custom_customer_no = custom_customer_no;
    }

    public String getConnection_date() {
        return connection_date;
    }

    public void setConnection_date(String connection_date) {
        this.connection_date = connection_date;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAnniversary_date() {
        return anniversary_date;
    }

    public void setAnniversary_date(String anniversary_date) {
        this.anniversary_date = anniversary_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPending_amount() {
        return pending_amount;
    }

    public void setPending_amount(String pending_amount) {
        this.pending_amount = pending_amount;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getInactive_date() {
        return inactive_date;
    }

    public void setInactive_date(String inactive_date) {
        this.inactive_date = inactive_date;
    }
    public String getMonthlybill() {
        return monthly_bill;
    }

    public void setMonthly_bill(String monthly_bill1) {
        this.monthly_bill = monthly_bill1;
    }
}
