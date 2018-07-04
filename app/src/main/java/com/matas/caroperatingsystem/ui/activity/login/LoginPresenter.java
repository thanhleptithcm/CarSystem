package com.matas.caroperatingsystem.ui.activity.login;

import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;
import com.matas.caroperatingsystem.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public LoginPresenter(CompositeDisposable compositeDisposable,
                          PreferencesHelper prefs) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
    }
}