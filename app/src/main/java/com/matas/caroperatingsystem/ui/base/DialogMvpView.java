package com.matas.caroperatingsystem.ui.base;

import android.support.v4.app.FragmentManager;

public interface DialogMvpView extends MvpView {
    void show(FragmentManager fragmentManager, String tag);

    void dismissDialog(String tag);
}