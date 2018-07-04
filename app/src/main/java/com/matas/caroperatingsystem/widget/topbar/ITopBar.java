package com.matas.caroperatingsystem.widget.topbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface ITopBar {
    void initData(@DrawableRes int srcImvLeftOne, String textTvTitle, @DrawableRes int srcImvRightOne);

    void initData(@StringRes int textLeftOne, @StringRes int textTvTitle, @DrawableRes int srcImvRightOne);

    void initData(String textLeftOne, String textTvTitle, @DrawableRes int srcImvRightOne);

    void setVisible(int imvLeftOne, int tvLeftOne, int tvTitle, int imvRightOne);
}
