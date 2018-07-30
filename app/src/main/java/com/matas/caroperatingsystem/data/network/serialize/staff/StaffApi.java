package com.matas.caroperatingsystem.data.network.serialize.staff;

import com.matas.caroperatingsystem.data.network.Urls;
import com.matas.caroperatingsystem.data.network.serialize.staff.request.ProfileRequest;
import com.matas.caroperatingsystem.data.network.serialize.staff.request.StatusRequest;
import com.matas.caroperatingsystem.data.network.serialize.staff.request.UpdateLocationRequest;
import com.matas.caroperatingsystem.data.network.serialize.staff.response.ProfileResponse;
import com.matas.caroperatingsystem.data.network.serialize.staff.response.StatusResponse;
import com.matas.caroperatingsystem.data.network.serialize.staff.response.UpdateLocationResponse;

import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.PUT;

@Singleton
public interface StaffApi {

    @PUT(Urls.Staff.USER)
    Observable<ProfileResponse> updateProfile(@HeaderMap Map<String, String> headers, @Body ProfileRequest profileRequest);

    @PUT(Urls.Staff.STATUS_BIKER)
    Observable<StatusResponse> updateStatusBiker(@HeaderMap Map<String, String> headers, @Body StatusRequest statusRequest);

    @PUT(Urls.Staff.DRIVERS_LOCATION)
    Observable<UpdateLocationResponse> updateLocation(@HeaderMap Map<String, String> headers, @Body UpdateLocationRequest locationRequest);
}
