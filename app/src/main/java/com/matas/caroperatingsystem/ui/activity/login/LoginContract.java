package com.matas.caroperatingsystem.ui.activity.login;

import com.matas.caroperatingsystem.ui.base.MvpView;

public interface LoginContract {

    interface LoginView extends MvpView {
        void loginSucess();

        void loginFailure();
    }

    interface LoginPresenter {
        void login(String userName, String passWord);
    }
}
