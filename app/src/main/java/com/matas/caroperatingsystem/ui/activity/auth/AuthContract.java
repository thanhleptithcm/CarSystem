package com.matas.caroperatingsystem.ui.activity.auth;


import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.User;

public interface AuthContract {

    interface AuthView extends MvpView {
        void loginSuccess(User user);

        void signUpSuccess();

        void updateProfileSuccess();
    }

    interface AuthPresenter {
        void login(String phone, String passWord);

        void signUp(String phone, String passWord, int type, String nin);

        void updateInfo(String firstName, String lastName, String address, String gender);
    }
}
