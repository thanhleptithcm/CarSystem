package com.matas.caroperatingsystem.data.network.serialize.staff.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.Book;
import com.matas.caroperatingsystem.data.network.BaseResponse;

import java.util.List;

public class AllBookingResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }
}
