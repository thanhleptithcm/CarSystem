package com.matas.caroperatingsystem.ui.activity.staff.main;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.staff.StaffApi;
import com.matas.caroperatingsystem.data.network.staff.request.ConfirmRequest;
import com.matas.caroperatingsystem.data.network.staff.request.StatusRequest;
import com.matas.caroperatingsystem.data.network.staff.request.UpdateLocationRequest;
import com.matas.caroperatingsystem.data.network.staff.response.ConfirmResponse;
import com.matas.caroperatingsystem.data.network.staff.response.StatusResponse;
import com.matas.caroperatingsystem.data.network.staff.response.UpdateLocationResponse;
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
    public String getToken() {
        return mPrefs.getToken();
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
                            getMvpView().updateStatusSuccess(status);
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
    public void updateLocation(double latitude, double longitude) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        final UpdateLocationRequest locationRequest = new UpdateLocationRequest(latitude, longitude);

        mCompositeDisposable.add(mStaffApi.updateLocation(apiHeaders, locationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateLocationResponse>() {
                    @Override
                    public void accept(UpdateLocationResponse loginResponse) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            getMvpView().updateLocationSuccess();
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
    public void confirmBooking(String bookId) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        ConfirmRequest request = new ConfirmRequest("ACCEPT");
        mCompositeDisposable.add(mStaffApi.confirmBooking(apiHeaders, request, bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ConfirmResponse>() {
                    @Override
                    public void accept(ConfirmResponse response) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            getMvpView().confirmBookingSuccess(response.getConfirmBooking());
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