package com.digitalrupay.datamodels;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santosh on 10/9/2016.
 */

public class TodayCollectionEmployeeDataModel implements Serializable, ParentListItem {

    @SerializedName("emp_name")
    private String emp_name;
    @SerializedName("paid_amt")
    private String paid_amt;
    @SerializedName("group")
    private ArrayList<GroupDataModel> groupDataModelArrayList;

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getPaid_amt() {
        return paid_amt;
    }

    public void setPaid_amt(String paid_amt) {
        this.paid_amt = paid_amt;
    }

    public ArrayList<GroupDataModel> getGroupDataModelArrayList() {
        return groupDataModelArrayList;
    }

    public void setGroupDataModelArrayList(ArrayList<GroupDataModel> groupDataModelArrayList) {
        this.groupDataModelArrayList = groupDataModelArrayList;
    }

    @Override
    public List<?> getChildItemList() {
        return getGroupDataModelArrayList();
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}

