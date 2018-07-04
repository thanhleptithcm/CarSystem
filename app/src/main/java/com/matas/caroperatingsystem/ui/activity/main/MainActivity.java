package com.matas.caroperatingsystem.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.ui.activity.login.LoginActivity;
import com.matas.caroperatingsystem.ui.base.BaseMainActivity;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class MainActivity extends BaseMainActivity implements MainContract.MainMvpView, View.OnClickListener {

    private AppTopBar mTopBar;
    @Inject
    MainPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        mTopBar = findViewById(R.id.top_bar);

        initData();
        initListener();
    }

    private void initData() {

    }

    private void initListener() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getContainerId() {
        return R.id.fr_container;
    }

    @Override
    public void logoutSuccess() {
        LoginActivity.startActivity(MainActivity.this);
        finish();
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