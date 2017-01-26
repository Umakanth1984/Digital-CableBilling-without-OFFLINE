package com.digitalrupay.datamodels;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Audlink_sri on 12/26/2016.
 */

public class EmpCollectionDataModel implements Serializable {

    @SerializedName("total")
    private String total;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("total_collections")
    private String total_collections;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String gettotal_collections() {
        return total_collections;
    }

    public void settotal_collections(String total_collections) {
        this.total_collections = total_collections;
    }
}
