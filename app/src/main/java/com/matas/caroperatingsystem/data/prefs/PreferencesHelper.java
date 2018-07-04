package com.matas.caroperatingsystem.data.prefs;

import com.matas.caroperatingsystem.data.network.serialize.authenticate.response.User;

public interface PreferencesHelper {
    boolean isRemember();

    void setIsRemember(boolean isRemember);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    User getUserLogin();

    void setUserLogin(User user);
}
