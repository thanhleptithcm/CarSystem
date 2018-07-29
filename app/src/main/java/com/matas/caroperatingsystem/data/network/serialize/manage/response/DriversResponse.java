package com.matas.caroperatingsystem.data.network.serialize.manage.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.BaseResponse;

import java.util.List;

public class DriversResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<User> drivers;

    public List<User> getDrivers() {
        return drivers;
    }
}
