package com.matas.caroperatingsystem.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.di.ApplicationContext;
import com.matas.caroperatingsystem.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_LOGIN_USER = "PREF_KEY_LOGIN_USER";

    private final SharedPreferences mPrefs;
    private final Gson mGson;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName,
                                Gson gson) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        mGson = gson;
    }

    @Override
    public User getUserLogin() {
        String userJson = mPrefs.getString(PREF_KEY_LOGIN_USER, null);
        return mGson.fromJson(userJson, User.class);
    }

    @Override
    public void setUserLogin(User user) {
        String userJson = mGson.toJson(user);
        mPrefs.edit().putString(PREF_KEY_LOGIN_USER, userJson).apply();
    }
}
