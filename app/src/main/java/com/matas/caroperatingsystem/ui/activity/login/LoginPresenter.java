package com.matas.caroperatingsystem.ui.activity.login;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.request.LoginRequest;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.request.SignUpRequest;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.response.LoginResponse;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.response.SignUpResponse;
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

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;

    @Inject
    public LoginPresenter(CompositeDisposable compositeDisposable,
                          PreferencesHelper prefs,
                          AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
    }

    @Override
    public void login(String phone, String passWord) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");

        getMvpView().showLoading();
        final LoginRequest loginRequest = new LoginRequest(phone, passWord);
        mCompositeDisposable.add(mAuthenticateApi.login(apiHeaders, loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) {
                        if (isViewAttached()) {
                            User user = loginResponse.getUser();
                            mPrefs.setUserLogin(user);
                            mPrefs.setToken(user.getToken());

                            getMvpView().hideLoading();
                            getMvpView().loginSuccess(user.getType());
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
                                getMvpView().showErrorDialog(R.string.api_default_error);
                            }
                        }
                    }
                }));
    }

    @Override
    public void signUp(String phone, String passWord, int type) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");

        getMvpView().showLoading();
        final SignUpRequest signUpRequest = new SignUpRequest(phone, passWord, type);
        mCompositeDisposable.add(mAuthenticateApi.signUp(apiHeaders, signUpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SignUpResponse>() {
                    @Override
                    public void accept(SignUpResponse loginResponse) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            getMvpView().signUpSuccess();
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
                                getMvpView().showErrorDialog(R.string.api_default_error);
                            }
                        }
                    }
                }));
    }
}