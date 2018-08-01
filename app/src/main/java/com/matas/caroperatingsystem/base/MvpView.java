package com.matas.caroperatingsystem.base;

import android.content.Context;
import android.support.annotation.StringRes;

import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.ui.dialog.OkDialog;

public interface MvpView {

    int getCoordinateLayout();

    void showLoading();

    void hideLoading();

    void showConfirmDialog(Context context,
                           String title,
                           String message,
                           ConfirmDialog.OnConfirmDialogListener onConfirmDialogListener,
                           BaseDialog.OnBackPressListener backPressListener);

    void showConfirmDialog(Context context,
                           String title,
                           String message,
                           String positive,
                           String negative,
                           ConfirmDialog.OnConfirmDialogListener onConfirmDialogListener,
                           BaseDialog.OnBackPressListener backPressListener);

    void showToast(@StringRes int resId);

    void showToast(String message);

    void showErrorDialog(@StringRes int resId);

    void showErrorDialog(String message);

    void showOKDialog(Context context, String title, String message, OkDialog.IOkDialogListener listener,
                         final BaseDialog.OnBackPressListener backPressListener);

    void showOKDialog(Context context, String message, OkDialog.IOkDialogListener listener,
                         final BaseDialog.OnBackPressListener backPressListener);

    boolean isNetworkConnected();

    void hideKeyboard();
}