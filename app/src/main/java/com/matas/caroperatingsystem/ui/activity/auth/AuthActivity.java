package com.matas.caroperatingsystem.ui.activity.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.ui.activity.manage.ManageActivity;
import com.matas.caroperatingsystem.ui.activity.staff.main.StaffActivity;
import com.matas.caroperatingsystem.ui.activity.user.UserActivity;
import com.matas.caroperatingsystem.ui.fragment.auth.AuthFragFragment;
import com.matas.caroperatingsystem.ui.fragment.login.LoginFragment;
import com.matas.caroperatingsystem.ui.fragment.profile_staff.ProfileFragment;
import com.matas.caroperatingsystem.ui.fragment.signUp.SignUpFragment;
import com.matas.caroperatingsystem.utils.AppConstants;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class AuthActivity extends TopBarActivity implements AuthContract.AuthView,
        ProfileFragment.OnProfileListener,
        AuthFragFragment.OnAuthFragListener {

    private AuthFragFragment mAuthFragFragment;
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

        mAuthFragFragment = AuthFragFragment.newInstance();
        mAuthFragFragment.setOnAuthFragListener(this);
        pushFragment(mAuthFragFragment, AuthFragFragment.TAG, false);
    }

    private void showScreenUpdateInfo() {
        mProfileFragment = ProfileFragment.newInstance();
        mProfileFragment.setOnProfileListener(this);
        pushFragment(mProfileFragment, ProfileFragment.TAG);
    }

    @Override
    protected int getContainerId() {
        return R.id.fr_login_container;
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
    public void updateProfileSuccess() {
        showToast("Update Profile Success");
        StaffActivity.startActivity(AuthActivity.this);
    }

    @Override
    public void onUpdateProfile(String firstName, String lastName, String address, String gender) {
        mPresenter.updateInfo(firstName, lastName, address, gender);
    }

    @Override
    public void onAuthLoginSuccess(int type, String firstName) {
        switch (type) {
            case 0:
                UserActivity.startActivity(AuthActivity.this);
                break;
            case 1:
                if (firstName == null) {
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
}
