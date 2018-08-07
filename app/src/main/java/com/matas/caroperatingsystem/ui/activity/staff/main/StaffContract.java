package com.matas.caroperatingsystem.ui.activity.staff.main;

import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.ConfirmBooking;
import com.matas.caroperatingsystem.data.model.User;

public interface StaffContract {

    interface StaffView extends MvpView {
        void updateStatusSuccess(boolean status);

        void updateLocationSuccess();

        void confirmBookingSuccess(ConfirmBooking confirmBooking);
    }

    interface StaffPresenter {
        String getToken();

        User getUser();

        void updateStatus(boolean status);

        void updateLocation(double latitude, double longitude);

        void confirmBooking(String bookId);
    }
}
