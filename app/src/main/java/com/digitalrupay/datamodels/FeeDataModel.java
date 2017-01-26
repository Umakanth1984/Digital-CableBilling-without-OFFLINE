package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 8/27/2016.
 */
public class FeeDataModel implements Serializable {

    @SerializedName("Fee")
    FeeDataModel Fee;
    @SerializedName("Student")
    StudentsDataModel Student;
    @SerializedName("feeId")
    private String feeId;
    @SerializedName("id")
    private String id;
    @SerializedName("student_id")
    private String student_id;
    @SerializedName("challan_number")
    private String challan_number;
    @SerializedName("issue_date")
    private String issue_date;
    @SerializedName("due_date")
    private String due_date;
    @SerializedName("term_month")
    private String term_month;
    @SerializedName("paid")
    private String paid;
    @SerializedName("receipt_number")
    private String receipt_number;
    @SerializedName("receipt_date")
    private String receipt_date;
    @SerializedName("tuition_fee")
    private String tuition_fee;
    @SerializedName("transportation_fee")
    private String transportation_fee;
    @SerializedName("library_fee")
    private String library_fee;
    @SerializedName("books_fee")
    private String books_fee;
    @SerializedName("uniform_fee")
    private String uniform_fee;
    @SerializedName("canteen_fee")
    private String canteen_fee;
    @SerializedName("late_fee")
    private String late_fee;
    @SerializedName("onetime_fee")
    private String onetime_fee;
    @SerializedName("admission_fee")
    private String admission_fee;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;
    @SerializedName("total")
    private int total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getChallan_number() {
        return challan_number;
    }

    public void setChallan_number(String challan_number) {
        this.challan_number = challan_number;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTerm_month() {
        return term_month;
    }

    public void setTerm_month(String term_month) {
        this.term_month = term_month;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getTuition_fee() {
        return tuition_fee;
    }

    public void setTuition_fee(String tuition_fee) {
        this.tuition_fee = tuition_fee;
    }

    public String getTransportation_fee() {
        return transportation_fee;
    }

    public void setTransportation_fee(String transportation_fee) {
        this.transportation_fee = transportation_fee;
    }

    public String getLibrary_fee() {
        return library_fee;
    }

    public void setLibrary_fee(String library_fee) {
        this.library_fee = library_fee;
    }

    public String getBooks_fee() {
        return books_fee;
    }

    public void setBooks_fee(String books_fee) {
        this.books_fee = books_fee;
    }

    public String getUniform_fee() {
        return uniform_fee;
    }

    public void setUniform_fee(String uniform_fee) {
        this.uniform_fee = uniform_fee;
    }

    public String getCanteen_fee() {
        return canteen_fee;
    }

    public void setCanteen_fee(String canteen_fee) {
        this.canteen_fee = canteen_fee;
    }

    public String getLate_fee() {
        return late_fee;
    }

    public void setLate_fee(String late_fee) {
        this.late_fee = late_fee;
    }

    public String getOnetime_fee() {
        return onetime_fee;
    }

    public void setOnetime_fee(String onetime_fee) {
        this.onetime_fee = onetime_fee;
    }

    public String getAdmission_fee() {
        return admission_fee;
    }

    public void setAdmission_fee(String admission_fee) {
        this.admission_fee = admission_fee;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public FeeDataModel getFee() {
        return Fee;
    }

    public void setFee(FeeDataModel fee) {
        Fee = fee;
    }

    public StudentsDataModel getStudent() {
        return Student;
    }

    public void setStudent(StudentsDataModel student) {
        Student = student;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }
}

