package com.matas.caroperatingsystem.data.network.manage.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffStatusRequest {
    @SerializedName("active")
    @Expose
    public boolean status;

    public StaffStatusRequest(boolean status) {
        this.status = status;
    }
}
