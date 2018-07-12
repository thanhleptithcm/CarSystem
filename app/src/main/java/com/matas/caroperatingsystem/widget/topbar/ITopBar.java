package com.matas.caroperatingsystem.widget.topbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface ITopBar {
    void initData(@DrawableRes int srcLeftOneImv, @StringRes int textLeftOneTv, @StringRes int textTitleTv, @StringRes int textRightOneTv, @DrawableRes int srcRightOneImv);

    void setVisible(int leftOneImv, int leftOneTv, int titleTv, int rightOneTv, int rightOneImv);

    void setDrawableTextLeftOne(int left, int top, int right, int bottom);
}
