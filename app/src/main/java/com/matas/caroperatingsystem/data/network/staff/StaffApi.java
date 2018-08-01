package com.matas.caroperatingsystem.data.network.staff;

import com.matas.caroperatingsystem.data.network.Urls;
import com.matas.caroperatingsystem.data.network.staff.request.ConfirmRequest;
import com.matas.caroperatingsystem.data.network.staff.request.ProfileRequest;
import com.matas.caroperatingsystem.data.network.staff.request.StatusRequest;
import com.matas.caroperatingsystem.data.network.staff.request.UpdateLocationRequest;
import com.matas.caroperatingsystem.data.network.staff.response.AllBookingResponse;
import com.matas.caroperatingsystem.data.network.staff.response.ConfirmResponse;
import com.matas.caroperatingsystem.data.network.staff.response.ProfileResponse;
import com.matas.caroperatingsystem.data.network.staff.response.StatusResponse;
import com.matas.caroperatingsystem.data.network.staff.response.UpdateLocationResponse;

import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PUT;
import retrofit2.http.Path;

@Singleton
public interface StaffApi {

    @PUT(Urls.Staff.USER)
    Observable<ProfileResponse> updateProfile(@HeaderMap Map<String, String> headers, @Body ProfileRequest profileRequest);

    @PUT(Urls.Staff.STATUS_BIKER)
    Observable<StatusResponse> updateStatusBiker(@HeaderMap Map<String, String> headers, @Body StatusRequest statusRequest);

    @PUT(Urls.Staff.DRIVERS_LOCATION)
    Observable<UpdateLocationResponse> updateLocation(@HeaderMap Map<String, String> headers, @Body UpdateLocationRequest locationRequest);

    @GET(Urls.Staff.ALL_BOOKING)
    Observable<AllBookingResponse> getAllBooking(@HeaderMap Map<String, String> headers);

    @PUT(Urls.Staff.CONFIRM_BOOKING)
    Observable<ConfirmResponse> confirmBooking(@HeaderMap Map<String, String> headers, @Body ConfirmRequest request, @Path("bookId") String bookId);
}
