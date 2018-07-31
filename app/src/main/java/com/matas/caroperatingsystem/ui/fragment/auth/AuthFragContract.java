package com.matas.caroperatingsystem.ui.fragment.auth;

import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.User;

public interface AuthFragContract {

    interface AuthFragView extends MvpView {
        void loginSuccess(User user);

        void signUpSuccess();
    }

    interface AuthFragPresenter {
        void login(String phone, String passWord);

        void signUp(String phone, String passWord, int type, String nin);

    }
}
