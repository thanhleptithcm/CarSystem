package com.matas.caroperatingsystem.data.network.staff.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmRequest {
    @SerializedName("status")
    @Expose
    public String status;

    public ConfirmRequest(String status) {
        this.status = status;
    }
}
