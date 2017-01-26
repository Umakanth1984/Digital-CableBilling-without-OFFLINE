package com.digitalrupay.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Santosh on 8/17/2016.
 */
public class DetailsModel implements Serializable {

    @SerializedName("User")
    UserModel userModel;
    @SerializedName("Group")
    List<UserModel> Group;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<UserModel> getGroup() {
        return Group;
    }

    public void setGroup(List<UserModel> group) {
        Group = group;
    }
}
