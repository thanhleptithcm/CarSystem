package com.matas.caroperatingsystem.ui.fragment.manage_staff;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.BaseResponse;
import com.matas.caroperatingsystem.data.network.manage.ManageApi;
import com.matas.caroperatingsystem.data.network.manage.request.StaffStatusRequest;
import com.matas.caroperatingsystem.data.network.manage.response.DriversResponse;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ManageStaffPresenter extends BasePresenter<ManageStaffContract.ManageStaffView>
        implements ManageStaffContract.ManageStaffPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final ManageApi mManageApi;
    private List<User> mListStaff;

    @Inject
    public ManageStaffPresenter(CompositeDisposable compositeDisposable,
                                PreferencesHelper prefs,
                                ManageApi manageApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mManageApi = manageApi;
        mListStaff = new ArrayList<>();
    }

    public List<User> getListStaff() {
        return mListStaff;
    }

    @Override
    public void fetchListStaff() {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        mCompositeDisposable.add(mManageApi.getListDrivers(apiHeaders)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DriversResponse>() {
                    @Override
                    public void accept(DriversResponse driversResponse) {
                        if (isViewAttached()) {
                            if(mListStaff != null){
                                mListStaff.clear();
                            }
                            mListStaff.addAll(driversResponse.getDrivers());
                            getMvpView().hideLoading();
                            getMvpView().getListStaffSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            if (throwable instanceof HttpException) {
                                HttpException exception = (HttpException) throwable;
                                if (exception.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                                    getMvpView().showErrorDialog(R.string.login_failed);
                                }
                            } else if (throwable instanceof UnknownHostException) {
                                getMvpView().showErrorDialog(R.string.connection_error);
                            } else {
                                getMvpView().showErrorDialog(throwable.getMessage());
                            }
                        }
                    }
                }));
    }

    @Override
    public void updateStatusStaff(boolean status, String driverId) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        StaffStatusRequest request = new StaffStatusRequest(status);
        mCompositeDisposable.add(mManageApi.updateStatusStaff(apiHeaders, request, driverId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) {
                        if (isViewAttached()) {

                            getMvpView().hideLoading();
                            getMvpView().updateStatusStaffSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            if (throwable instanceof HttpException) {
                                HttpException exception = (HttpException) throwable;
                                if (exception.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                                    getMvpView().showErrorDialog(R.string.login_failed);
                                }
                            } else if (throwable instanceof UnknownHostException) {
                                getMvpView().showErrorDialog(R.string.connection_error);
                            } else {
                                getMvpView().showErrorDialog(throwable.getMessage());
                            }
                        }
                    }
                }));
    }
}