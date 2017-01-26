package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 10/2/2016.
 */

public class ComplaintsDataModel implements Serializable {

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
    @SerializedName("monthly_bill")
    private String monthly_bill;
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

    @SerializedName("complaint_id")
    private String complaint_id;
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("comp_ticketno")
    private String comp_ticketno;
    @SerializedName("comp_cat")
    private String comp_cat;
    @SerializedName("complaint")
    private String complaint;
    @SerializedName("comp_status")
    private String comp_status;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("created_date")
    private String created_date;
    @SerializedName("last_edited_by")
    private String last_edited_by;
    @SerializedName("edited_on")
    private String edited_on;
    @SerializedName("comp_remarks")
    private String comp_remarks;

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

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getComp_ticketno() {
        return comp_ticketno;
    }

    public void setComp_ticketno(String comp_ticketno) {
        this.comp_ticketno = comp_ticketno;
    }

    public String getComp_cat() {
        return comp_cat;
    }

    public void setComp_cat(String comp_cat) {
        this.comp_cat = comp_cat;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getComp_status() {
        return comp_status;
    }

    public void setComp_status(String comp_status) {
        this.comp_status = comp_status;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getLast_edited_by() {
        return last_edited_by;
    }

    public void setLast_edited_by(String last_edited_by) {
        this.last_edited_by = last_edited_by;
    }

    public String getEdited_on() {
        return edited_on;
    }

    public void setEdited_on(String edited_on) {
        this.edited_on = edited_on;
    }

    public String getComp_remarks() {
        return comp_remarks;
    }

    public void setComp_remarks(String comp_remarks) {
        this.comp_remarks = comp_remarks;
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

    public String getMonthlybill() {
        return monthly_bill;
    }

    public void setMonthly_bill(String monthly_bill1) {
        this.monthly_bill = monthly_bill1;
    }
}
