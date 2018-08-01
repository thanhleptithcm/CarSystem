package com.matas.caroperatingsystem.di.component;

import android.app.Application;
import android.content.Context;

import com.matas.caroperatingsystem.MainApplication;
import com.matas.caroperatingsystem.data.network.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.network.manage.ManageApi;
import com.matas.caroperatingsystem.data.network.staff.StaffApi;
import com.matas.caroperatingsystem.data.network.user.UserApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;
import com.matas.caroperatingsystem.di.ApplicationContext;
import com.matas.caroperatingsystem.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(MainApplication mainApplication);

    @ApplicationContext
    Context context();

    Application application();

    PreferencesHelper preferencesHelper();

    AuthenticateApi authenticateApi();

    StaffApi staffApi();

    ManageApi manageApi();

    UserApi userApi();
}
