package com.matas.caroperatingsystem.ui.activity.auth;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.network.staff.StaffApi;
import com.matas.caroperatingsystem.data.network.staff.request.ProfileRequest;
import com.matas.caroperatingsystem.data.network.staff.response.ProfileResponse;
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

public class AuthPresenter extends BasePresenter<AuthContract.AuthView> implements AuthContract.AuthPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final StaffApi mStaffApi;

    @Inject
    public AuthPresenter(CompositeDisposable compositeDisposable,
                         PreferencesHelper prefs,
                         AuthenticateApi authenticateApi,
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
    public boolean isLogin() {
        return mPrefs.getToken() != null;
    }

    @Override
    public void updateInfo(String firstName, String lastName, String address, String gender) {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        final ProfileRequest profileRequest = new ProfileRequest(firstName, lastName, address, gender);
        mCompositeDisposable.add(mStaffApi.updateProfile(apiHeaders, profileRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProfileResponse>() {
                    @Override
                    public void accept(ProfileResponse loginResponse) {
                        if (isViewAttached()) {
                            User user = loginResponse.getUser();
                            mPrefs.setUserLogin(user);
                            mPrefs.setToken(user.getToken());

                            getMvpView().hideLoading();
                            getMvpView().updateProfileSuccess();
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