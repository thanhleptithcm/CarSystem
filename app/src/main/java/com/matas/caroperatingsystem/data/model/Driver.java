package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver implements Parcelable {

    @SerializedName("location")
    @Expose
    private PosLocation location;

    @SerializedName("active")
    @Expose
    private boolean active;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("driverId")
    @Expose
    private String driverId;

    @SerializedName("socketId")
    @Expose
    private String socketId;

    public PosLocation getLocation() {
        return location;
    }

    public void setLocation(PosLocation location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
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
        dest.writeParcelable(this.location, flags);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.driverId);
        dest.writeString(this.socketId);
    }

    public Driver() {
    }

    protected Driver(Parcel in) {
        this.location = in.readParcelable(PosLocation.class.getClassLoader());
        this.active = in.readByte() != 0;
        this.id = in.readString();
        this.driverId = in.readString();
        this.socketId = in.readString();
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel source) {
            return new Driver(source);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };
}
