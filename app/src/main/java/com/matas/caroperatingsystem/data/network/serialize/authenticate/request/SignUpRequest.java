package com.matas.caroperatingsystem.data.network.serialize.authenticate.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("type")
    @Expose
    private int type;

    public SignUpRequest(String phone, String password, int type) {
        this.phone = phone;
        this.password = password;
        this.type = type;
    }
}

