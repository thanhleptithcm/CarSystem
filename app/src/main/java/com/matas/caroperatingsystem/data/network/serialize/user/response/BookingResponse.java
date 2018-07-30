package com.matas.caroperatingsystem.data.network.serialize.user.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.Book;
import com.matas.caroperatingsystem.data.network.BaseResponse;

public class BookingResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private Book book;

    public Book getBook() {
        return book;
    }
}
