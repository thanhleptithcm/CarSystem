package com.matas.caroperatingsystem.ui.activity.manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.ui.fragment.home.HomeFragment;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class ManageActivity extends TopBarActivity implements ManageContract.ManageMvpView,
        View.OnClickListener {

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
        return null;
    }
}