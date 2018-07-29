package com.matas.caroperatingsystem.ui.activity.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseActivity;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.activity.manage.ManageActivity;
import com.matas.caroperatingsystem.ui.activity.staff.StaffActivity;
import com.matas.caroperatingsystem.ui.activity.user.UserActivity;
import com.matas.caroperatingsystem.utils.AppConstants;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashContract.SplashView {

    @Override
    public int getCoordinateLayout() {
        return R.layout.activity_splash;
    }

    @Inject
    SplashPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPresenter.isLogin()) {
                    switch (mPresenter.getUser().getType()) {
                        case 0:
                            UserActivity.startActivity(SplashActivity.this);
                            break;
                        case 1:
                            StaffActivity.startActivity(SplashActivity.this);
                            break;
                        case 2:
                            ManageActivity.startActivity(SplashActivity.this);
                            break;
                    }
                } else {
                    AuthActivity.startActivity(SplashActivity.this);
                }
                finish();
            }
        }, AppConstants.SPLASH_DELAY_TIME);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }
}
