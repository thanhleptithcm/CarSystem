package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Staff implements Parcelable {
    public static final String TAG = Staff.class.getSimpleName();
    private String name;
    private String phoneNumber;
    private String address;
    private String gender;

    public Staff(String name, String phoneNumber, String address, String gender) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.address);
        dest.writeString(this.gender);
    }

    public Staff() {
    }

    protected Staff(Parcel in) {
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.address = in.readString();
        this.gender = in.readString();
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel source) {
            return new Staff(source);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };
}
