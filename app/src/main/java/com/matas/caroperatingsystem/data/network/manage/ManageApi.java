package com.matas.caroperatingsystem.data.network.manage;

import com.matas.caroperatingsystem.data.network.BaseResponse;
import com.matas.caroperatingsystem.data.network.Urls;
import com.matas.caroperatingsystem.data.network.manage.request.StaffStatusRequest;
import com.matas.caroperatingsystem.data.network.manage.response.DriversResponse;
import com.matas.caroperatingsystem.data.network.staff.request.ConfirmRequest;
import com.matas.caroperatingsystem.data.network.staff.response.ConfirmResponse;

import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PUT;
import retrofit2.http.Path;

@Singleton
public interface ManageApi {

    @GET(Urls.Manage.DRIVERS)
    Observable<DriversResponse> getListDrivers(@HeaderMap Map<String, String> headers);

    @PUT(Urls.Manage.UPDATE_STATUS_DRIVER)
    Observable<BaseResponse> updateStatusStaff(@HeaderMap Map<String, String> headers, @Body StaffStatusRequest request, @Path("driverId") String driverId);
}
