package com.matas.caroperatingsystem.ui.activity.splash;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashContract.SplashView> implements SplashContract.SplashPresenter {

    @Inject
    public SplashPresenter(PreferencesHelper prefs) {
        super(prefs);
    }

    @Override
    public boolean isLogin() {
        return mPrefs.getUserLogin() != null;
    }

    @Override
    public User getUser() {
        return mPrefs.getUserLogin();
    }
}
