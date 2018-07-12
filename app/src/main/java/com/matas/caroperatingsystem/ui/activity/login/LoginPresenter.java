package com.matas.caroperatingsystem.ui.activity.login;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public LoginPresenter(CompositeDisposable compositeDisposable,
                          PreferencesHelper prefs) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void login(String userName, String passWord) {
        if(userName.equalsIgnoreCase("thanh@gmail.com") && passWord.equalsIgnoreCase("111111")){
            mPrefs.setUserLogin(new User("1", "thanh@gmail.com"));
            getMvpView().loginSucess();
        } else {
            getMvpView().loginFailure();
        }
    }
}