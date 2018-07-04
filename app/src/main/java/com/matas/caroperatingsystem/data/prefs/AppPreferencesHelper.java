package com.matas.caroperatingsystem.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.response.User;
import com.matas.caroperatingsystem.di.ApplicationContext;
import com.matas.caroperatingsystem.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_IS_REMEMBER = "PREF_KEY_IS_REMEMBER";
    private static final String PREF_KEY_USERNAME = "PREF_KEY_USERNAME";
    private static final String PREF_KEY_PASSWORD = "PREF_KEY_PASSWORD";
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
    public boolean isRemember() {
        return mPrefs.getBoolean(PREF_KEY_IS_REMEMBER, false);
    }

    @Override
    public void setIsRemember(boolean isRemember) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_REMEMBER, isRemember).apply();
    }

    @Override
    public String getUsername() {
        return mPrefs.getString(PREF_KEY_USERNAME, null);
    }

    @Override
    public void setUsername(String username) {
        mPrefs.edit().putString(PREF_KEY_USERNAME, username).apply();
    }

    @Override
    public String getPassword() {
        return mPrefs.getString(PREF_KEY_PASSWORD, null);
    }

    @Override
    public void setPassword(String password) {
        mPrefs.edit().putString(PREF_KEY_PASSWORD, password).apply();
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
