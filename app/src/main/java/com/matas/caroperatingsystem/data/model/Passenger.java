package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passenger implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("socketId")
    @Expose
    private String socketId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.phone);
        dest.writeString(this.socketId);
    }

    public Passenger() {
    }

    protected Passenger(Parcel in) {
        this.id = in.readString();
        this.phone = in.readString();
        this.socketId = in.readString();
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        @Override
        public Passenger createFromParcel(Parcel source) {
            return new Passenger(source);
        }

        @Override
        public Passenger[] newArray(int size) {
            return new Passenger[size];
        }
    };
}
