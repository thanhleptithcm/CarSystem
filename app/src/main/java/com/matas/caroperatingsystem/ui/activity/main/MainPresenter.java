package com.matas.caroperatingsystem.ui.activity.main;

import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;
import com.matas.caroperatingsystem.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter extends BasePresenter<MainContract.MainMvpView> implements MainContract.MainMvpPresenter {

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public MainPresenter(CompositeDisposable compositeDisposable,
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