package com.matas.caroperatingsystem.ui.activity.manage;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ManagePresenter extends BasePresenter<ManageContract.ManageMvpView> implements ManageContract.ManageMvpPresenter {

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public ManagePresenter(CompositeDisposable compositeDisposable,
                           PreferencesHelper prefs) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
    }

}