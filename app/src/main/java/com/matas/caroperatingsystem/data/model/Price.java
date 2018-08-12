package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price implements Parcelable {
    @SerializedName("hour")
    @Expose
    private int hour;

    @SerializedName("price")
    @Expose
    private double price;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hour);
        dest.writeDouble(this.price);
    }

    public Price() {
    }

    public Price(int hour, double price) {
        this.hour = hour;
        this.price = price;
    }

    protected Price(Parcel in) {
        this.hour = in.readInt();
        this.price = in.readDouble();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}
