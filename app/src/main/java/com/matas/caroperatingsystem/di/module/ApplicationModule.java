package com.matas.caroperatingsystem.di.module;

import android.app.Application;
import android.content.Context;

import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.network.serialize.manage.ManageApi;
import com.matas.caroperatingsystem.data.network.serialize.staff.StaffApi;
import com.matas.caroperatingsystem.data.network.serialize.user.UserApi;
import com.matas.caroperatingsystem.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {NetworkModule.class,
        PreferenceModule.class})
public class ApplicationModule {
    private static final String TAG = ApplicationModule.class.getSimpleName();

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    @Singleton
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    AuthenticateApi provideAuthenticateApi(Retrofit retrofit) {
        return retrofit.create(AuthenticateApi.class);
    }

    @Provides
    @Singleton
    StaffApi provideStaffApi(Retrofit retrofit) {
        return retrofit.create(StaffApi.class);
    }

    @Provides
    @Singleton
    ManageApi provideManageApi(Retrofit retrofit) {
        return retrofit.create(ManageApi.class);
    }

    @Provides
    @Singleton
    UserApi provideUserApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }
}
