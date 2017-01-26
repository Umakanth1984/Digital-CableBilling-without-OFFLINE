package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 12/31/2016.
 */

public class CustomerPendingAmountModel implements Serializable {
    @SerializedName("pending_amount")
    private String pending_amount;
    @SerializedName("current_due")
    private String current_due;
    public String getpending_amount() {
        return pending_amount;
    }
    public void setpending_amount(String pending_amount) {
        this.pending_amount = pending_amount;
    }
    public String getcurrent_due() {
        return current_due;
    }
    public void setcurrent_due(String current_due) {
        this.current_due = current_due;
    }
}
