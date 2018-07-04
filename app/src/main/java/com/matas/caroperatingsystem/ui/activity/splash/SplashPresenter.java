package com.matas.caroperatingsystem.ui.activity.splash;

import android.text.TextUtils;

import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;
import com.matas.caroperatingsystem.ui.base.BasePresenter;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashMvpView> implements SplashMvpPresenter {

    @Inject
    public SplashPresenter(PreferencesHelper prefs) {
        super(prefs);
    }

    @Override
    public boolean isLogin() {
        return !TextUtils.isEmpty(null);
    }
}
