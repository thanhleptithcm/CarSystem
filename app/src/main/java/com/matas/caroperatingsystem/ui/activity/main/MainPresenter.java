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

//    @Override
//    public void requestFetchMissionList() {
//        mCompositeDisposable.add(mMissionApi.fetchAll()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Response<FetchMissionListResponse>>() {
//                    @Override
//                    public void accept(Response<FetchMissionListResponse> response) {
//                        if (response != null && response.isSuccessful()) {
//                            getMvpView().fetchedMissionList(missionList);
//                        } else {
//                            handleApiError(response);
//                        }
//                    }
//
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable){
//                        if (!isViewAttached()) {
//                            return;
//                        }
////                        getMvpView().hideLoading();
//                        getMvpView().showErrorDialog(throwable.getMessage());
//                    }
//                }));
//    }
}