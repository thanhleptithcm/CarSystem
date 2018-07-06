package com.matas.caroperatingsystem.ui.activity.maps;

import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;
import com.matas.caroperatingsystem.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MapsPresenter extends BasePresenter<MapsContract.MapsView> implements MapsContract.MapsPresenter {

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public MapsPresenter(CompositeDisposable compositeDisposable,
                         PreferencesHelper prefs) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
    }
}