package com.matas.caroperatingsystem.data.network.user.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.Destination;

public class BookingRequest {
    @SerializedName("distance")
    @Expose
    private double distance;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("destination")
    @Expose
    private Destination destination;

    public BookingRequest(double distance, double originLongitude, double originLatitude,
                          Destination destination) {
        this.distance = distance;
        this.longitude = originLongitude;
        this.latitude = originLatitude;
        this.destination = destination;
    }
}

