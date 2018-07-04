package com.matas.caroperatingsystem;

import android.app.Application;
import android.content.Context;

import com.matas.caroperatingsystem.di.component.ApplicationComponent;
import com.matas.caroperatingsystem.di.component.DaggerApplicationComponent;
import com.matas.caroperatingsystem.di.module.ApplicationModule;

public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getSimpleName();

    private static ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getContext() {
        return mApplicationComponent.context();
    }

}
