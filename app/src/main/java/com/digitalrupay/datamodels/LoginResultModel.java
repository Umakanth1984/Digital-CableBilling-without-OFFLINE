package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 8/17/2016.
 */
public class LoginResultModel implements Serializable {
    @SerializedName("message")
    String message;
    @SerializedName("text")
    String text;
    @SerializedName("details")
    DetailsModel details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DetailsModel getDetails() {
        return details;
    }

    public void setDetails(DetailsModel details) {
        this.details = details;
    }
}
