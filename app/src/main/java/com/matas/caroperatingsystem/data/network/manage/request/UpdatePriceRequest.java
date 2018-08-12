package com.matas.caroperatingsystem.data.network.manage.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.Price;

import java.util.ArrayList;
import java.util.List;

public class UpdatePriceRequest {

    @SerializedName("byTime")
    @Expose
    public List<Price> byTime = new ArrayList<>();
}
