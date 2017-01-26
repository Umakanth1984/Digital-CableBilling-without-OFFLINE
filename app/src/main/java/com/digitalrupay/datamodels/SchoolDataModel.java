package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 8/28/2016.
 */
public class SchoolDataModel implements Serializable {

    @SerializedName("School")
    private SchoolDataModel School;

    @SerializedName("CC")
    private String CC;
    @SerializedName("DC")
    private String DC;
    @SerializedName("NB")
    private String NB;
    @SerializedName("COMM_INCLUDED")
    private boolean COMM_INCLUDED;

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;
    @SerializedName("merchantid")
    private String merchantid;
    @SerializedName("cc_percentage")
    private String cc_percentage;
    @SerializedName("dc_percentage")
    private String dc_percentage;
    @SerializedName("am_percentage")
    private String am_percentage;
    @SerializedName("nb_amount")
    private String nb_amount;
    @SerializedName("commission_included")
    private boolean commission_included;
    @SerializedName("status")
    private String status;
    @SerializedName("transaction_id")
    private String transaction_id;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;

    public SchoolDataModel getSchool() {
        return School;
    }

    public void setSchool(SchoolDataModel school) {
        School = school;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getDC() {
        return DC;
    }

    public void setDC(String DC) {
        this.DC = DC;
    }

    public String getNB() {
        return NB;
    }

    public void setNB(String NB) {
        this.NB = NB;
    }

    public boolean isCOMM_INCLUDED() {
        return COMM_INCLUDED;
    }

    public void setCOMM_INCLUDED(boolean COMM_INCLUDED) {
        this.COMM_INCLUDED = COMM_INCLUDED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getCc_percentage() {
        return cc_percentage;
    }

    public void setCc_percentage(String cc_percentage) {
        this.cc_percentage = cc_percentage;
    }

    public String getDc_percentage() {
        return dc_percentage;
    }

    public void setDc_percentage(String dc_percentage) {
        this.dc_percentage = dc_percentage;
    }

    public String getAm_percentage() {
        return am_percentage;
    }

    public void setAm_percentage(String am_percentage) {
        this.am_percentage = am_percentage;
    }

    public String getNb_amount() {
        return nb_amount;
    }

    public void setNb_amount(String nb_amount) {
        this.nb_amount = nb_amount;
    }

    public boolean isCommission_included() {
        return commission_included;
    }

    public void setCommission_included(boolean commission_included) {
        this.commission_included = commission_included;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
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
}
