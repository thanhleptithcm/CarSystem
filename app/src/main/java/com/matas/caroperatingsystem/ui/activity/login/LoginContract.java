package com.matas.caroperatingsystem.ui.activity.login;


import com.matas.caroperatingsystem.base.MvpView;

public interface LoginContract {

    interface LoginView extends MvpView {
        void loginSucess();

        void loginFailure();
    }

    interface LoginPresenter {
        void login(String userName, String passWord);
    }
}
