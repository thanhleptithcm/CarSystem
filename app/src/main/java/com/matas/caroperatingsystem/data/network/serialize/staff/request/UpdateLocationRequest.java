package com.matas.caroperatingsystem.data.network.serialize.staff.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLocationRequest {
    @SerializedName("latitude")
    @Expose
    public double latitude;

    @SerializedName("longitude")
    @Expose
    public double longitude;

    @SerializedName("socketId")
    @Expose
    public String socketId;

    public UpdateLocationRequest(double latitude, double longitude, String socketId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.socketId = socketId;
    }
}
