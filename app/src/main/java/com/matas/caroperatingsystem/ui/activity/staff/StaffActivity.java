package com.matas.caroperatingsystem.ui.activity.staff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.fragment.profile_staff.ProfileFragment;
import com.matas.caroperatingsystem.ui.fragment.staff.StaffFragment;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class StaffActivity extends TopBarActivity implements StaffContract.StaffView,
        ProfileFragment.OnProfileListener,
        StaffFragment.OnStaffListener {

    private StaffFragment mStaffFragment;
    private ProfileFragment mProfileFragment;
    private AppTopBar topBar;

    @Inject
    StaffPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StaffActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.activity_staff;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        topBar = findViewById(R.id.top_bar);

        if (mPresenter.getUser().getNIN() == null) {
            showScreenUpdateInfo();
        } else {
            mPresenter.updateStatus(true);
        }
    }

    private void showScreenStaff() {
        mStaffFragment = StaffFragment.newInstance();
        mStaffFragment.setOnStaffListener(this);
        pushFragment(mStaffFragment, StaffFragment.TAG, false);
    }

    private void showScreenUpdateInfo() {
        mProfileFragment = ProfileFragment.newInstance();
        mProfileFragment.setOnProfileListener(this);
        pushFragment(mProfileFragment, ProfileFragment.TAG, false);
    }

    @Override
    protected int getContainerId() {
        return R.id.fr_container;
    }

    @Override
    public void onUpdateProfile(String firstName, String lastName, String address, String gender) {
        mPresenter.updateInfo(firstName, lastName, address, gender);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }

    @Override
    public AppTopBar getTopBar() {
        return topBar;
    }

    @Override
    public void updateProfileSuccess() {
        showToast("Update Profile Success");
        showScreenStaff();
    }

    @Override
    public void updateStatusSucess(boolean status) {
        if (status) {
            showScreenStaff();
        } else {
            mPresenter.setLogOut();
            AuthActivity.startActivity(this);
        }
    }

    @Override
    public void onLogout() {
        mPresenter.updateStatus(false);
    }
}