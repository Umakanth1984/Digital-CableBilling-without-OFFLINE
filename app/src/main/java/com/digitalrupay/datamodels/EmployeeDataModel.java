package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 9/30/2016.
 */

public class EmployeeDataModel implements Serializable {
    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("emp_first_name")
    private String emp_first_name;
    @SerializedName("emp_last_name")
    private String emp_last_name;
    @SerializedName("emp_add1")
    private String emp_add1;
    @SerializedName("emp_add2")
    private String emp_add2;
    @SerializedName("emp_add3")
    private String emp_add3;
    @SerializedName("emp_city")
    private String emp_city;
    @SerializedName("emp_pin_code")
    private String emp_pin_code;
    @SerializedName("emp_email")
    private String emp_email;
    @SerializedName("emp_phone_no")
    private String emp_phone_no;
    @SerializedName("emp_mobile_no")
    private String emp_mobile_no;
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("user_role")
    private String user_role;
    @SerializedName("emp_password")
    private String emp_password;
    @SerializedName("date_created")
    private String date_created;
    @SerializedName("status")
    private String status;
    @SerializedName("inactive_date")
    private String inactive_date;

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_first_name() {
        return emp_first_name;
    }

    public void setEmp_first_name(String emp_first_name) {
        this.emp_first_name = emp_first_name;
    }

    public String getEmp_last_name() {
        return emp_last_name;
    }

    public void setEmp_last_name(String emp_last_name) {
        this.emp_last_name = emp_last_name;
    }

    public String getEmp_add1() {
        return emp_add1;
    }

    public void setEmp_add1(String emp_add1) {
        this.emp_add1 = emp_add1;
    }

    public String getEmp_add2() {
        return emp_add2;
    }

    public void setEmp_add2(String emp_add2) {
        this.emp_add2 = emp_add2;
    }

    public String getEmp_add3() {
        return emp_add3;
    }

    public void setEmp_add3(String emp_add3) {
        this.emp_add3 = emp_add3;
    }

    public String getEmp_city() {
        return emp_city;
    }

    public void setEmp_city(String emp_city) {
        this.emp_city = emp_city;
    }

    public String getEmp_pin_code() {
        return emp_pin_code;
    }

    public void setEmp_pin_code(String emp_pin_code) {
        this.emp_pin_code = emp_pin_code;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_phone_no() {
        return emp_phone_no;
    }

    public void setEmp_phone_no(String emp_phone_no) {
        this.emp_phone_no = emp_phone_no;
    }

    public String getEmp_mobile_no() {
        return emp_mobile_no;
    }

    public void setEmp_mobile_no(String emp_mobile_no) {
        this.emp_mobile_no = emp_mobile_no;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getEmp_password() {
        return emp_password;
    }

    public void setEmp_password(String emp_password) {
        this.emp_password = emp_password;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInactive_date() {
        return inactive_date;
    }

    public void setInactive_date(String inactive_date) {
        this.inactive_date = inactive_date;
    }
}
