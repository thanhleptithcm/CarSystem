package com.matas.caroperatingsystem.data.network.staff.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.Driver;
import com.matas.caroperatingsystem.data.network.BaseResponse;

public class UpdateLocationResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private Driver driver;

    public Driver getDriver() {
        return driver;
    }
}
