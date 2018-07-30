package com.matas.caroperatingsystem.data.network.serialize.user.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingRequest {
    @SerializedName("socketId")
    @Expose
    private String socketId;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    public BookingRequest(String socketId, double longitude, double latitude) {
        this.socketId = socketId;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
