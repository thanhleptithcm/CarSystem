package com.matas.caroperatingsystem.ui.activity.login;


import com.matas.caroperatingsystem.base.MvpView;

public interface LoginContract {

    interface LoginView extends MvpView {
        void loginSuccess(int typeUser);

        void signUpSuccess();
    }

    interface LoginPresenter {
        void login(String phone, String passWord);

        void signUp(String phone, String passWord, int type);
    }
}
