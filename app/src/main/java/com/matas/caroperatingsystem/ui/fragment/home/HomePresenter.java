package com.matas.caroperatingsystem.ui.fragment.home;

import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;
import com.matas.caroperatingsystem.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class HomePresenter extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;

    @Inject
    public HomePresenter(CompositeDisposable compositeDisposable,
                         PreferencesHelper prefs,
                         AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
    }
}