package com.matas.caroperatingsystem.ui.activity.staff;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class StaffPresenter extends BasePresenter<StaffContract.StaffView> implements StaffContract.StaffPresenter {

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public StaffPresenter(CompositeDisposable compositeDisposable,
                          PreferencesHelper prefs) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onLogout() {
        setUserAsLoggedOut();
        getMvpView().logoutSuccess();
    }
}