package com.matas.caroperatingsystem.ui.activity.manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.data.model.Staff;
import com.matas.caroperatingsystem.ui.fragment.manage_staff_detail.DetailManageStaffFragment;
import com.matas.caroperatingsystem.ui.fragment.manage_staff.ManageStaffFragment;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class ManageActivity extends TopBarActivity implements ManageContract.ManageMvpView,
        View.OnClickListener, ManageStaffFragment.OnStaffListener {

    private ManageStaffFragment mManageStaffFragment;
    private DetailManageStaffFragment mDetailManageStaffFragment;
    private AppTopBar topBar;

    @Inject
    ManagePresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ManageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.activity_manage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        topBar = findViewById(R.id.top_bar);

        mManageStaffFragment = ManageStaffFragment.newInstance();
        mManageStaffFragment.setOnStaffListener(this);
        pushFragment(mManageStaffFragment, ManageStaffFragment.TAG);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getContainerId() {
        return R.id.fr_container;
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
    public void onStaffClick(Staff staff) {
        mDetailManageStaffFragment = DetailManageStaffFragment.newInstance(staff);
        pushFragment(mDetailManageStaffFragment, DetailManageStaffFragment.TAG, true);
    }
}