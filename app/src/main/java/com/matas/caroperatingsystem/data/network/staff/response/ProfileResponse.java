package com.matas.caroperatingsystem.data.network.staff.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.BaseResponse;

public class ProfileResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }
}

