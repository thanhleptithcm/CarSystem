package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book implements Parcelable {
    @SerializedName("passenger")
    @Expose
    private Passenger passenger;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("_id")
    @Expose
    private String id;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.passenger, flags);
        dest.writeString(this.status);
        dest.writeString(this.id);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.passenger = in.readParcelable(Passenger.class.getClassLoader());
        this.status = in.readString();
        this.id = in.readString();
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
