package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmBooking implements Parcelable{
    @SerializedName("booking")
    @Expose
    private Book booking;

    @SerializedName("driverLocation")
    @Expose
    private Driver driverLocation;

    public Book getBooking() {
        return booking;
    }

    public void setBooking(Book booking) {
        this.booking = booking;
    }

    public Driver getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(Driver driverLocation) {
        this.driverLocation = driverLocation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.booking, flags);
        dest.writeParcelable(this.driverLocation, flags);
    }

    public ConfirmBooking() {
    }

    protected ConfirmBooking(Parcel in) {
        this.booking = in.readParcelable(Book.class.getClassLoader());
        this.driverLocation = in.readParcelable(Driver.class.getClassLoader());
    }

    public static final Creator<ConfirmBooking> CREATOR = new Creator<ConfirmBooking>() {
        @Override
        public ConfirmBooking createFromParcel(Parcel source) {
            return new ConfirmBooking(source);
        }

        @Override
        public ConfirmBooking[] newArray(int size) {
            return new ConfirmBooking[size];
        }
    };
}
