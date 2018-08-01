package com.matas.caroperatingsystem.ui.fragment.signUp;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.network.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SignUpPresenter extends BasePresenter<SignUpContract.SignUpMvpView> implements SignUpContract.SignUpMvpPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;

    @Inject
    public SignUpPresenter(CompositeDisposable compositeDisposable,
                           PreferencesHelper prefs,
                           AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
    }
}