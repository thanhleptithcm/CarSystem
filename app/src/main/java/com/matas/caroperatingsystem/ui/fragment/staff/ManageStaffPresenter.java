package com.matas.caroperatingsystem.ui.fragment.staff;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.Staff;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ManageStaffPresenter extends BasePresenter<ManageStaffContract.ManageStaffView>
        implements ManageStaffContract.ManageStaffPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;
    private List<Staff> mListStaff;

    @Inject
    public ManageStaffPresenter(CompositeDisposable compositeDisposable,
                                PreferencesHelper prefs,
                                AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
        mListStaff = new ArrayList<>();
    }

    public List<Staff> getListStaff() {
        return mListStaff;
    }

    @Override
    public void fetchListStaff() {
        getMvpView().showLoading();
        mListStaff.add(new Staff("Ta Nhi", "0909090909", "Binh Thanh", "Female"));
        mListStaff.add(new Staff("Le Thanh", "0909090909", "Binh Thanh", "Male"));
        mListStaff.add(new Staff("Le Thanh", "0909090909", "Binh Thanh", "Male"));
        mListStaff.add(new Staff("Le Thanh", "0909090909", "Binh Thanh", "Male"));
        getMvpView().hideLoading();
        getMvpView().getListStaffSuccess();
    }
}