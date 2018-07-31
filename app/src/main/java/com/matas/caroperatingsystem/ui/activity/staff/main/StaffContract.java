package com.matas.caroperatingsystem.ui.activity.staff.main;

import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.User;

public interface StaffContract {

    interface StaffView extends MvpView {
        void updateStatusSuccess(boolean status);

        void updateLocationSuccess();
    }

    interface StaffPresenter {
        User getUser();

        void updateStatus(boolean status);

        void updateLocation(double latitude, double longitude, String socketId);
    }
}
