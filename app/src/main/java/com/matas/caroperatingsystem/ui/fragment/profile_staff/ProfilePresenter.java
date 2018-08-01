package com.matas.caroperatingsystem.ui.fragment.profile_staff;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.network.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ProfilePresenter extends BasePresenter<ProfileContract.ProfileView> implements ProfileContract.ProfilePresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;

    @Inject
    public ProfilePresenter(CompositeDisposable compositeDisposable,
                            PreferencesHelper prefs,
                            AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
    }
}