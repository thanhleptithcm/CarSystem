package com.matas.caroperatingsystem.data.network.user;

import com.matas.caroperatingsystem.data.network.BaseResponse;
import com.matas.caroperatingsystem.data.network.Urls;
import com.matas.caroperatingsystem.data.network.staff.request.ConfirmRequest;
import com.matas.caroperatingsystem.data.network.user.request.BookingRequest;
import com.matas.caroperatingsystem.data.network.user.response.BookingResponse;
import com.matas.caroperatingsystem.data.network.user.response.DriverNearResponse;

import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Singleton
public interface UserApi {

    @GET(Urls.User.DRIVERS_LOCATION)
    Observable<DriverNearResponse> getListDriverNearLocation(
            @HeaderMap Map<String, String> headers,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude);

    @POST(Urls.User.BOOKING)
    Observable<BookingResponse> bookingDriver(@HeaderMap Map<String, String> headers, @Body BookingRequest bookingRequest);

    @DELETE(Urls.User.CANCEL_BOOKING)
    Observable<BaseResponse> cancelBooking(@HeaderMap Map<String, String> headers, @Path("idBooking") String idBooking);
}
