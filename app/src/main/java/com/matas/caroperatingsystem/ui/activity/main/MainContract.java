package com.matas.caroperatingsystem.ui.activity.main;

import com.matas.caroperatingsystem.ui.base.MvpView;

public interface MainContract {

    interface MainMvpView extends MvpView {
        void logoutSuccess();
    }

    interface MainMvpPresenter {
        void onLogout();
    }
}
