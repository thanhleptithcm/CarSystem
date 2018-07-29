package com.matas.caroperatingsystem.data.network.serialize.user;

import com.matas.caroperatingsystem.data.network.Urls;
import com.matas.caroperatingsystem.data.network.serialize.user.response.DriverNearResponse;

import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PUT;
import retrofit2.http.Query;

@Singleton
public interface UserApi {

    @GET(Urls.User.DRIVERS_LOCATION)
    Observable<DriverNearResponse> getListDriverNearLocation(
            @HeaderMap Map<String, String> headers,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude);

}
