package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book implements Parcelable {
    @SerializedName("passenger")
    @Expose
    private Passenger passenger;

    @SerializedName("destination")
    @Expose
    private Destination destination;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("distance")
    @Expose
    private double distance;

    @SerializedName("driverId")
    @Expose
    private String driverId;

    @SerializedName("pricePerKm")
    @Expose
    private double pricePerKm;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.passenger, flags);
        dest.writeParcelable(this.destination, flags);
        dest.writeString(this.status);
        dest.writeString(this.id);
        dest.writeDouble(this.distance);
        dest.writeString(this.driverId);
        dest.writeDouble(this.pricePerKm);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.passenger = in.readParcelable(Passenger.class.getClassLoader());
        this.destination = in.readParcelable(Destination.class.getClassLoader());
        this.status = in.readString();
        this.id = in.readString();
        this.distance = in.readDouble();
        this.driverId = in.readString();
        this.pricePerKm = in.readDouble();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
