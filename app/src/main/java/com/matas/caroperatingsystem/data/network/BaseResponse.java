package com.matas.caroperatingsystem.data.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse implements Parcelable {
    @SerializedName("success")
    @Expose
    protected String success;

    @SerializedName("code")
    @Expose
    protected int code;

    @SerializedName("message")
    @Expose
    protected String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.success);
        dest.writeInt(this.code);
        dest.writeString(this.message);
    }

    public BaseResponse() {
    }

    protected BaseResponse(Parcel in) {
        this.success = in.readString();
        this.code = in.readInt();
        this.message = in.readString();
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel source) {
            return new BaseResponse(source);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };
}
