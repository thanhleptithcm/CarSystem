package com.matas.caroperatingsystem.di.component;

import com.matas.caroperatingsystem.base.BaseActivity;
import com.matas.caroperatingsystem.di.PerActivity;
import com.matas.caroperatingsystem.di.module.ActivityModule;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.activity.staff.detail.ListBookActivity;
import com.matas.caroperatingsystem.ui.activity.user.UserActivity;
import com.matas.caroperatingsystem.ui.activity.staff.main.StaffActivity;
import com.matas.caroperatingsystem.ui.activity.manage.ManageActivity;
import com.matas.caroperatingsystem.ui.activity.splash.SplashActivity;
import com.matas.caroperatingsystem.ui.fragment.auth.AuthFragFragment;
import com.matas.caroperatingsystem.ui.fragment.manage_staff_detail.DetailManageStaffFragment;
import com.matas.caroperatingsystem.ui.fragment.login.LoginFragment;
import com.matas.caroperatingsystem.ui.fragment.profile_staff.ProfileFragment;
import com.matas.caroperatingsystem.ui.fragment.setup_price.SetupPriceFragment;
import com.matas.caroperatingsystem.ui.fragment.signUp.SignUpFragment;
import com.matas.caroperatingsystem.ui.fragment.manage_staff.ManageStaffFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);

    void inject(SplashActivity splashActivity);

    void inject(AuthActivity authActivity);

    void inject(StaffActivity staffActivity);

    void inject(ManageActivity manageActivity);

    void inject(ListBookActivity listBookActivity);

    void inject(AuthFragFragment authFragFragment);

    void inject(LoginFragment loginFragment);

    void inject(SignUpFragment signUpFragment);

    void inject(UserActivity userActivity);

    void inject(ProfileFragment profileFragment);

    void inject(ManageStaffFragment manageStaffFragment);

    void inject(DetailManageStaffFragment detailManageStaffFragment);

    void inject(SetupPriceFragment setupPriceFragment);
}