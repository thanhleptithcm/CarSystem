package com.matas.caroperatingsystem.ui.activity.user;

import com.google.android.gms.maps.model.LatLng;
import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.network.user.response.BookingResponse;

public interface UserContract {

    interface UserView extends MvpView {
        void getListDriverNearSuccess();

        void bookingDriverSuccess(BookingResponse response);
    }

    interface UserPresenter {
        void getListDriver(double latitude, double longitude);

        void bookingDrivers(LatLng latLng, String socketId);
    }
}
