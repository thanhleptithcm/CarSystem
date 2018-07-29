package com.matas.caroperatingsystem.ui.activity.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.ui.activity.manage.ManageActivity;
import com.matas.caroperatingsystem.ui.activity.staff.StaffActivity;
import com.matas.caroperatingsystem.ui.activity.user.UserActivity;
import com.matas.caroperatingsystem.ui.fragment.login.LoginFragment;
import com.matas.caroperatingsystem.ui.fragment.profile_staff.ProfileFragment;
import com.matas.caroperatingsystem.ui.fragment.signUp.SignUpFragment;
import com.matas.caroperatingsystem.utils.AppConstants;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class AuthActivity extends TopBarActivity implements AuthContract.AuthView,
        LoginFragment.OnLoginListener,
        ProfileFragment.OnProfileListener,
        SignUpFragment.OnSignUpListener {

    private LoginFragment mLoginFragment;
    private SignUpFragment mSignUpFragment;
    private ProfileFragment mProfileFragment;

    @Inject
    AuthPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
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
        mPresenter.onViewAttach(this);

        showScreenLogin();
    }

    private void showScreenLogin() {
        mLoginFragment = LoginFragment.newInstance();
        mLoginFragment.setOnLoginListener(this);
        pushFragment(mLoginFragment, LoginFragment.TAG);
    }

    private void showScreenUpdateInfo() {
        mProfileFragment = ProfileFragment.newInstance();
        mProfileFragment.setOnProfileListener(this);
        pushFragment(mProfileFragment, ProfileFragment.TAG, false);
    }

    @Override
    protected int getContainerId() {
        return R.id.fr_login_container;
    }

    @Override
    public void onLoginClick(String email, String password) {
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
    public void onSignUpClick(String email, String password, int type, String NIN) {
        mPresenter.signUp(email, password, type, NIN);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }

    @Override
    public AppTopBar getTopBar() {
        return null;
    }

    @Override
    public void loginSuccess(User user) {
        switch (user.getType()) {
            case 0:
                UserActivity.startActivity(AuthActivity.this);
                break;
            case 1:
                if (user.getFirstName() == null) {
                    showScreenUpdateInfo();
                } else {
                    StaffActivity.startActivity(AuthActivity.this);
                }
                break;
            case 2:
                ManageActivity.startActivity(AuthActivity.this);
                break;
        }
    }

    @Override
    public void signUpSuccess() {
        showToast("Sign up Success");
        showScreenLogin();
    }

    @Override
    public void updateProfileSuccess() {
        showToast("Update Profile Success");
        StaffActivity.startActivity(AuthActivity.this);
    }

    @Override
    public void onUpdateProfile(String firstName, String lastName, String address, String gender) {
        mPresenter.updateInfo(firstName, lastName, address, gender);
    }
}
