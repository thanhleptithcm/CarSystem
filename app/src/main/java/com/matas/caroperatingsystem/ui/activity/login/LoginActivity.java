package com.matas.caroperatingsystem.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.ui.base.BaseMainActivity;
import com.matas.caroperatingsystem.ui.fragment.login.LoginFragment;
import com.matas.caroperatingsystem.ui.fragment.signUp.SignUpFragment;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class LoginActivity extends BaseMainActivity implements LoginContract.LoginView,
        LoginFragment.OnLoginListener,
        SignUpFragment.OnSignUpListener {

    private LoginFragment mLoginFragment;
    private SignUpFragment mSignUpFragment;

    private AppTopBar mTopBar;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        mTopBar = findViewById(R.id.top_bar);

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
    public void onSignUpClick() {
        mSignUpFragment = SignUpFragment.newInstance();
        mSignUpFragment.setOnSignUpListener(this);
        pushFragment(mSignUpFragment, SignUpFragment.TAG, true);
    }

    @Override
    public void onForgotPasswordClick() {

    }

    @Override
    public void onLoginClick(String email, String password) {

    }

    @Override
    public void onLoginWithFaceBook() {

    }

    @Override
    public void onLoginWithGoogle() {

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
    public AppTopBar getTopBar() {
        return mTopBar;
    }
}
