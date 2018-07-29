package com.matas.caroperatingsystem.ui.activity.user;

import com.matas.caroperatingsystem.base.MvpView;

public interface UserContract {

    interface UserView extends MvpView {
        void getListDriverNearSuccess();
    }

    interface UserPresenter {
        void getListDriver(double latitude, double longitude);
    }
}
