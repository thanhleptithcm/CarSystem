package com.matas.caroperatingsystem.ui.activity.staff;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.request.LoginRequest;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.response.LoginResponse;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.response.SignUpResponse;
import com.matas.caroperatingsystem.data.network.serialize.staff.StaffApi;
import com.matas.caroperatingsystem.data.network.serialize.staff.request.ProfileRequest;
import com.matas.caroperatingsystem.data.network.serialize.staff.request.StatusRequest;
import com.matas.caroperatingsystem.data.network.serialize.staff.request.UpdateLocationRequest;
import com.matas.caroperatingsystem.data.network.serialize.staff.response.ProfileResponse;
import com.matas.caroperatingsystem.data.network.serialize.staff.response.StatusResponse;
import com.matas.caroperatingsystem.data.network.serialize.staff.response.UpdateLocationResponse;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class StaffPresenter extends BasePresenter<StaffContract.StaffView> implements StaffContract.StaffPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final StaffApi mStaffApi;

    @Inject
    public StaffPresenter(CompositeDisposable compositeDisposable,
                          PreferencesHelper prefs,
                          StaffApi staffApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mStaffApi = staffApi;
    }

    @Override
    public User getUser() {
        return mPrefs.getUserLogin();
    }


    @Override
    public void updateStatus(final boolean status) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        final StatusRequest statusRequest = new StatusRequest(status);

        mCompositeDisposable.add(mStaffApi.updateStatusBiker(apiHeaders, statusRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StatusResponse>() {
                    @Override
                    public void accept(StatusResponse loginResponse) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            getMvpView().updateStatusSucess(status);
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
    public void updateLocation(double latitude, double longitude, String socketId) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        final UpdateLocationRequest locationRequest = new UpdateLocationRequest(latitude, longitude, socketId);

        mCompositeDisposable.add(mStaffApi.updateLocation(apiHeaders, locationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateLocationResponse>() {
                    @Override
                    public void accept(UpdateLocationResponse loginResponse) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            getMvpView().updateLocationSucess();
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