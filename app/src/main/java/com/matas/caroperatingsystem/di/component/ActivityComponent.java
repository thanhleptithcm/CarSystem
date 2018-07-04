package com.matas.caroperatingsystem.di.component;

import com.matas.caroperatingsystem.di.PerActivity;
import com.matas.caroperatingsystem.di.module.ActivityModule;
import com.matas.caroperatingsystem.ui.activity.login.LoginActivity;
import com.matas.caroperatingsystem.ui.activity.main.MainActivity;
import com.matas.caroperatingsystem.ui.activity.maps.MapsActivity;
import com.matas.caroperatingsystem.ui.activity.splash.SplashActivity;
import com.matas.caroperatingsystem.ui.fragment.login.LoginFragment;
import com.matas.caroperatingsystem.ui.fragment.signUp.SignUpFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(LoginFragment loginFragment);

    void inject(SignUpFragment signUpFragment);

    void inject(MapsActivity mapsActivity);
}