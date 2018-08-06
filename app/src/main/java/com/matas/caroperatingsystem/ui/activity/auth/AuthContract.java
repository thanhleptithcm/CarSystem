package com.matas.caroperatingsystem.ui.activity.auth;


import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.User;

public interface AuthContract {

    interface AuthView extends MvpView {
        void updateProfileSuccess();
    }

    interface AuthPresenter {
        User getUser();

        boolean isLogin();

        void updateInfo(String firstName, String lastName, String address, String gender);
    }
}
