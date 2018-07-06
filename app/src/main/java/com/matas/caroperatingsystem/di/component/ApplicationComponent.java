package com.matas.caroperatingsystem.di.component;

import android.app.Application;
import android.content.Context;

import com.matas.caroperatingsystem.MainApplication;
import com.matas.caroperatingsystem.data.caches.CachesHelper;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;
import com.matas.caroperatingsystem.di.ApplicationContext;
import com.matas.caroperatingsystem.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(MainApplication flyNowApplication);

    @ApplicationContext
    Context context();

    Application application();

    PreferencesHelper preferencesHelper();

    CachesHelper cachesHelper();

    AuthenticateApi authenticateApi();
}