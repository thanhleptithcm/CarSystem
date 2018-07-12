package com.matas.caroperatingsystem.ui.activity.splash;

import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.User;

public interface SplashContract {

    interface SplashView extends MvpView {

    }

    interface SplashPresenter {
        boolean isLogin();

        User getUser();
    }
}
