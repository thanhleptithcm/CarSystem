package com.matas.caroperatingsystem.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PosLocation implements Parcelable {
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates;

    @SerializedName("type")
    @Expose
    private String type;

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.coordinates);
        dest.writeString(this.type);
    }

    public PosLocation() {
    }

    protected PosLocation(Parcel in) {
        this.coordinates = new ArrayList<Double>();
        in.readList(this.coordinates, Double.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Creator<PosLocation> CREATOR = new Creator<PosLocation>() {
        @Override
        public PosLocation createFromParcel(Parcel source) {
            return new PosLocation(source);
        }

        @Override
        public PosLocation[] newArray(int size) {
            return new PosLocation[size];
        }
    };
}
