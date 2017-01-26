package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 10/9/2016.
 */

public class GroupDataModel implements Serializable {

    @SerializedName("grp_id")
    private String grp_id;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("paid_amt")
    private String paid_amt;

    public String getGrp_id() {
        return grp_id;
    }

    public void setGrp_id(String grp_id) {
        this.grp_id = grp_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getPaid_amt() {
        return paid_amt;
    }

    public void setPaid_amt(String paid_amt) {
        this.paid_amt = paid_amt;
    }
}
