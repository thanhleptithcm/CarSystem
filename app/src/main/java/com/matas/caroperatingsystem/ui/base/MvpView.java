package com.matas.caroperatingsystem.ui.base;

import android.support.annotation.StringRes;

public interface MvpView {

    int getCoordinateLayout();

    void showLoading();

    void hideLoading();

    void showToast(@StringRes int resId);

    void showToast(String message);

    void showErrorDialog(@StringRes int resId);

    void showErrorDialog(String message);

    boolean isNetworkConnected();

    void hideKeyboard();
}