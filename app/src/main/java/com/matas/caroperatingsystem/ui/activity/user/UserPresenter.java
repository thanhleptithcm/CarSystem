package com.matas.caroperatingsystem.ui.activity.user;

import com.google.android.gms.maps.model.LatLng;
import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.Destination;
import com.matas.caroperatingsystem.data.model.Driver;
import com.matas.caroperatingsystem.data.network.user.UserApi;
import com.matas.caroperatingsystem.data.network.user.request.BookingRequest;
import com.matas.caroperatingsystem.data.network.user.response.BookingResponse;
import com.matas.caroperatingsystem.data.network.user.response.DriverNearResponse;
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

public class UserPresenter extends BasePresenter<UserContract.UserView> implements UserContract.UserPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final UserApi mUserApi;
    private List<Driver> mListDriverNear;

    @Inject
    public UserPresenter(CompositeDisposable compositeDisposable,
                         PreferencesHelper prefs,
                         UserApi userApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mUserApi = userApi;
        mListDriverNear = new ArrayList<>();
    }

    public List<Driver> getListDriverNear() {
        return mListDriverNear;
    }

    @Override
    public String getToken() {
        return mPrefs.getToken();
    }

    @Override
    public void getListDriver(double latitude, double longitude) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());
        getMvpView().showLoading();
        mCompositeDisposable.add(mUserApi.getListDriverNearLocation(apiHeaders, latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DriverNearResponse>() {
                    @Override
                    public void accept(DriverNearResponse driverNearResponse) {
                        if (isViewAttached()) {
                            if (mListDriverNear != null)
                                mListDriverNear.clear();

                            mListDriverNear.addAll(driverNearResponse.getDriverNears());
                            getMvpView().hideLoading();
                            getMvpView().getListDriverNearSuccess();
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
    public void bookingDrivers(double distance, LatLng originLatLng, LatLng latLngDestination) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());
        getMvpView().showLoading();
        BookingRequest request = new BookingRequest(distance,
                originLatLng.longitude,
                originLatLng.latitude,
                new Destination(latLngDestination.longitude, latLngDestination.latitude));
        mCompositeDisposable.add(mUserApi.bookingDriver(apiHeaders, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BookingResponse>() {
                    @Override
                    public void accept(BookingResponse response) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();

                            if (response.getMessage() != null) {
                                getMvpView().showErrorDialog(response.getMessage());
                            } else {
                                getMvpView().bookingDriverSuccess(response);
                            }
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