package com.matas.caroperatingsystem.data.network.manage;

import com.matas.caroperatingsystem.data.network.Urls;
import com.matas.caroperatingsystem.data.network.manage.response.DriversResponse;

import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

@Singleton
public interface ManageApi {

    @GET(Urls.Manage.DRIVERS)
    Observable<DriversResponse> getListDrivers(@HeaderMap Map<String, String> headers);
}
