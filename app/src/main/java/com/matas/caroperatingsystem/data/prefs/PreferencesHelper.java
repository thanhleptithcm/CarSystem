package com.matas.caroperatingsystem.data.prefs;

import com.matas.caroperatingsystem.data.model.User;

public interface PreferencesHelper {
    User getUserLogin();

    void setUserLogin(User user);

    String getToken();

    void setToken(String token);
}
