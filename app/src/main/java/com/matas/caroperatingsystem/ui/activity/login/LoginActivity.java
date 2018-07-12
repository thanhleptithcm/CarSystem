package com.matas.caroperatingsystem.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.ui.activity.staff.StaffActivity;
import com.matas.caroperatingsystem.ui.fragment.login.LoginFragment;
import com.matas.caroperatingsystem.ui.fragment.signUp.SignUpFragment;
import com.matas.caroperatingsystem.utils.AppConstants;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class LoginActivity extends TopBarActivity implements LoginContract.LoginView,
        LoginFragment.OnLoginListener,
        SignUpFragment.OnSignUpListener {

    private LoginFragment mLoginFragment;
    private SignUpFragment mSignUpFragment;

    @Inject
    LoginPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        showScreenLogin();
    }

    private void showScreenLogin() {
        mLoginFragment = LoginFragment.newInstance();
        mLoginFragment.setOnLoginListener(this);
        pushFragment(mLoginFragment, LoginFragment.TAG);
    }

    @Override
    protected int getContainerId() {
        return R.id.fr_login_container;
    }

    @Override
    public void onLoginClick(String email, String password) {
        showLoading();
        mPresenter.login(email, password);
    }

    @Override
    public void onUserSignUp() {
        mSignUpFragment = SignUpFragment.newInstance(AppConstants.USER_SIGN_UP);
        mSignUpFragment.setOnSignUpListener(this);
        pushFragment(mSignUpFragment, SignUpFragment.TAG, true);
    }

    @Override
    public void onBikerSignUp() {
        mSignUpFragment = SignUpFragment.newInstance(AppConstants.BIKER_SIGN_UP);
        mSignUpFragment.setOnSignUpListener(this);
        pushFragment(mSignUpFragment, SignUpFragment.TAG, true);
    }

    @Override
    public void onSignUpClick(String email, String password, String confirmPass) {

    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }

    @Override
    public void loginSucess() {
        hideLoading();
        StaffActivity.startActivity(LoginActivity.this);
    }

    @Override
    public void loginFailure() {
        hideLoading();
        showErrorDialog("Login Failure");
    }

    @Override
    public AppTopBar getTopBar() {
        return null;
    }
}
