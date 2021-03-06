package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User implements Parcelable {
    public static final String TAG = User.class.getSimpleName();

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("NIN")
    @Expose
    private String NIN;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("active")
    @Expose
    private boolean active;

    @SerializedName("booking")
    @Expose
    private List<Book> booking;

    public User(int type, String id, String firstName, String lastName, String phone, String address, String gender, String NIN, String token, boolean active) {
        this.type = type;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.NIN = NIN;
        this.token = token;
        this.active = active;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getNIN() {
        return NIN;
    }

    public void setNIN(String NIN) {
        this.NIN = NIN;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return active;
    }

    public List<Book> getBooking() {
        return booking;
    }

    public void setBooking(List<Book> booking) {
        this.booking = booking;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.phone);
        dest.writeString(this.address);
        dest.writeString(this.gender);
        dest.writeString(this.NIN);
        dest.writeString(this.token);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.booking);
    }

    protected User(Parcel in) {
        this.type = in.readInt();
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
        this.gender = in.readString();
        this.NIN = in.readString();
        this.token = in.readString();
        this.active = in.readByte() != 0;
        this.booking = in.createTypedArrayList(Book.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
