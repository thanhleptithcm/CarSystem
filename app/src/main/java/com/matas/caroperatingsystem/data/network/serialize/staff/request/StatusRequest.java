package com.matas.caroperatingsystem.data.network.serialize.staff.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusRequest {
    @SerializedName("active")
    @Expose
    public boolean active;

    public StatusRequest(boolean active) {
        this.active = active;
    }
}
