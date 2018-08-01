package com.matas.caroperatingsystem.data.network.user.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.Driver;
import com.matas.caroperatingsystem.data.network.BaseResponse;

import java.util.List;

public class DriverNearResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<Driver> driverNears;

    public List<Driver> getDriverNears() {
        return driverNears;
    }
}
