package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 10/6/2016.
 */

public class CollectionDataModel implements Serializable {

    @SerializedName("total_collections")
    private String total_collections;

    public String getTotal_collections() {
        return total_collections;
    }

    public void setTotal_collections(String total_collections) {
        this.total_collections = total_collections;
    }
}
