package com.matas.caroperatingsystem.ui.fragment.login;

import android.text.TextUtils;

import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;
import com.matas.caroperatingsystem.ui.base.BasePresenter;
import com.matas.caroperatingsystem.utils.CommonUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter extends BasePresenter<LoginContract.LoginMvpView> implements LoginContract.LoginMvpPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;

    @Inject
    public LoginPresenter(CompositeDisposable compositeDisposable,
                          PreferencesHelper prefs,
                          AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
    }
}