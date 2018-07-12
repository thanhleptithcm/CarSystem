package com.matas.caroperatingsystem.ui.fragment.staff;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class StaffPresenter extends BasePresenter<StaffContract.StaffView> implements StaffContract.StaffPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;

    @Inject
    public StaffPresenter(CompositeDisposable compositeDisposable,
                          PreferencesHelper prefs,
                          AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
    }
}