package com.matas.caroperatingsystem.ui.activity.user;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserPresenter extends BasePresenter<UserContract.UserView> implements UserContract.UserPresenter {

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public UserPresenter(CompositeDisposable compositeDisposable,
                         PreferencesHelper prefs) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
    }
}