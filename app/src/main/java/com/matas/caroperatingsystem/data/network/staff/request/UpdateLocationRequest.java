package com.matas.caroperatingsystem.data.network.staff.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLocationRequest {
    @SerializedName("latitude")
    @Expose
    public double latitude;

    @SerializedName("longitude")
    @Expose
    public double longitude;

    public UpdateLocationRequest(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
