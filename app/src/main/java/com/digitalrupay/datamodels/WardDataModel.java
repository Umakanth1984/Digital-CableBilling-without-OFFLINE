package com.digitalrupay.datamodels;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santosh on 8/27/2016.
 */
public class WardDataModel implements Serializable, ParentListItem {
    @SerializedName("Student")
    StudentsDataModel studentsDataModel;
    @SerializedName("Section")
    StudentsDataModel Section;
    @SerializedName("Standard")
    StudentsDataModel Standard;
    @SerializedName("Branch")
    StudentsDataModel Branch;
    @SerializedName("User")
    StudentsDataModel User;
    @SerializedName("Fee")
    ArrayList<FeeDataModel> Fee;

    @SerializedName("payment_data")
    StudentsDataModel payment_data;
    @SerializedName("school")
    SchoolDataModel school;
    @SerializedName("others")
    WardDataModel others;
    @SerializedName("student")
    StudentsDataModel student;
    @SerializedName("fee")
    FeeDataModel fee;

    public StudentsDataModel getStudentsDataModel() {
        return studentsDataModel;
    }

    public void setStudentsDataModel(StudentsDataModel studentsDataModel) {
        this.studentsDataModel = studentsDataModel;
    }

    public StudentsDataModel getSection() {
        return Section;
    }

    public void setSection(StudentsDataModel section) {
        Section = section;
    }

    public StudentsDataModel getStandard() {
        return Standard;
    }

    public void setStandard(StudentsDataModel standard) {
        Standard = standard;
    }

    public StudentsDataModel getBranch() {
        return Branch;
    }

    public void setBranch(StudentsDataModel branch) {
        Branch = branch;
    }

    public StudentsDataModel getUser() {
        return User;
    }

    public void setUser(StudentsDataModel user) {
        User = user;
    }

    public ArrayList<FeeDataModel> getFee() {
        return Fee;
    }

    public void setFee(ArrayList<FeeDataModel> fee) {
        Fee = fee;
    }

    public StudentsDataModel getPayment_data() {
        return payment_data;
    }

    public void setPayment_data(StudentsDataModel payment_data) {
        this.payment_data = payment_data;
    }

    public SchoolDataModel getSchool() {
        return school;
    }

    public void setSchool(SchoolDataModel school) {
        this.school = school;
    }

    public WardDataModel getOthers() {
        return others;
    }

    public void setOthers(WardDataModel others) {
        this.others = others;
    }

    public StudentsDataModel getStudent() {
        return student;
    }

    public void setStudent(StudentsDataModel student) {
        this.student = student;
    }

    public void setFee(FeeDataModel fee) {
        this.fee = fee;
    }

    public FeeDataModel getFeeObject() {
        return fee;
    }

    @Override
    public List<?> getChildItemList() {
        return getFee();
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
