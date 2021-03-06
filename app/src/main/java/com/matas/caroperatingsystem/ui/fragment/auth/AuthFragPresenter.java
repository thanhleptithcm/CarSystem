package com.matas.caroperatingsystem.ui.fragment.auth;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.network.authenticate.request.LoginRequest;
import com.matas.caroperatingsystem.data.network.authenticate.request.SignUpRequest;
import com.matas.caroperatingsystem.data.network.authenticate.response.LoginResponse;
import com.matas.caroperatingsystem.data.network.authenticate.response.SignUpResponse;
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

public class AuthFragPresenter extends BasePresenter<AuthFragContract.AuthFragView> implements AuthFragContract.AuthFragPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;
    private int mCurrentIndexFragment = 0;
    @Inject
    public AuthFragPresenter(CompositeDisposable compositeDisposable,
                             PreferencesHelper prefs,
                             AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
    }

    public int getCurrentIndexFragment() {
        return this.mCurrentIndexFragment;
    }

    public void setCurrentIndexFragment(int index) {
        this.mCurrentIndexFragment = index;
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
                            getMvpView().loginSuccess(user);
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
    public void signUp(String phone, String passWord, int type, String nin) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");

        getMvpView().showLoading();
        final SignUpRequest signUpRequest = new SignUpRequest(phone, passWord, type, nin);
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
                                getMvpView().showErrorDialog(throwable.getMessage());
                            }
                        }
                    }
                }));
    }


}