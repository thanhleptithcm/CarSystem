package com.matas.caroperatingsystem.data.network.serialize.authenticate.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("isPAClientAdmin")
    @Expose
    private Boolean isPAClientAdmin;

    @SerializedName("roles")
    @Expose
    private List<String> roles;

    @SerializedName("verified")
    @Expose
    private Boolean verified;

    @SerializedName("metric")
    @Expose
    private Boolean metric;

    @SerializedName("hasPayment")
    @Expose
    private Boolean hasPayment;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getPAClientAdmin() {
        return isPAClientAdmin;
    }

    public void setPAClientAdmin(Boolean PAClientAdmin) {
        isPAClientAdmin = PAClientAdmin;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getMetric() {
        return metric;
    }

    public void setMetric(Boolean metric) {
        this.metric = metric;
    }

    public Boolean getHasPayment() {
        return hasPayment;
    }

    public void setHasPayment(Boolean hasPayment) {
        this.hasPayment = hasPayment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.username);
        dest.writeValue(this.isPAClientAdmin);
        dest.writeStringList(this.roles);
        dest.writeValue(this.verified);
        dest.writeValue(this.metric);
        dest.writeValue(this.hasPayment);
    }

    public User() {
    }

    protected User(Parcel in) {
        this._id = in.readString();
        this.username = in.readString();
        this.isPAClientAdmin = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.roles = in.createStringArrayList();
        this.verified = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.metric = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.hasPayment = (Boolean) in.readValue(Boolean.class.getClassLoader());
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
