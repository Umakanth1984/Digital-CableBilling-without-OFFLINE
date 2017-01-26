package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

public class MaintainceModel {

    @SerializedName("ecat_name")
    private String ecat_name;
    public String getEcat_name() {
        return ecat_name; }
    public void setEcat_name(String ecat_name1) {
        this.ecat_name = ecat_name1;
    }
}
