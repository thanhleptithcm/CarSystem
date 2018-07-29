package com.matas.caroperatingsystem.ui.activity.auth;


import com.matas.caroperatingsystem.base.MvpView;

public interface AuthContract {

    interface AuthView extends MvpView {
        void loginSuccess(int typeUser);

        void signUpSuccess();
    }

    interface AuthPresenter {
        void login(String phone, String passWord);

        void signUp(String phone, String passWord, int type, String nin);
    }
}
