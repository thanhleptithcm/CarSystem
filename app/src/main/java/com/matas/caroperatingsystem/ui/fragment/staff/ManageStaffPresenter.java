package com.matas.caroperatingsystem.ui.fragment.staff;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ManageStaffPresenter extends BasePresenter<ManageStaffContract.ManageStaffView> implements ManageStaffContract.ManageStaffPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;

    @Inject
    public ManageStaffPresenter(CompositeDisposable compositeDisposable,
                                PreferencesHelper prefs,
                                AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
    }
}