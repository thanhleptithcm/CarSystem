package com.matas.caroperatingsystem.ui.fragment.setup_price;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.network.manage.ManageApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;


public class SetupPricePresenter extends BasePresenter<SetupPriceContract.SetupPriceView>
        implements SetupPriceContract.SetupPricePresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final ManageApi mManageApi;

    @Inject
    public SetupPricePresenter(CompositeDisposable compositeDisposable,
                               PreferencesHelper prefs,
                               ManageApi manageApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mManageApi = manageApi;
    }

}