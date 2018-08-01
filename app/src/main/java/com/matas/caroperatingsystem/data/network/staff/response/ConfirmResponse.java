package com.matas.caroperatingsystem.data.network.staff.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.ConfirmBooking;
import com.matas.caroperatingsystem.data.network.BaseResponse;

public class ConfirmResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ConfirmBooking confirmBooking;

    public ConfirmBooking getConfirmBooking() {
        return confirmBooking;
    }
}
