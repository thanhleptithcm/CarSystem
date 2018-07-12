package com.matas.caroperatingsystem.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.matas.caroperatingsystem.base.BaseDialog;
import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.ui.dialog.OkDialog;

public class DialogUtils {
    public static void showOkDialog(Context context, String title, String message,
                                    OkDialog.IOkDialogListener listener,
                                    final BaseDialog.OnBackPressListener backPressListener) {
        final OkDialog dialogOk = new OkDialog(context, title, message, listener);
        dialogOk.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (backPressListener == null) {
                        dialog.dismiss();
                    } else {
                        backPressListener.onBackPress(dialogOk);
                    }
                }
                return true;
            }
        });
        dialogOk.show();
    }

    public static void showConfirmDialog(Context context,
                                         String title,
                                         String message,
                                         String positive,
                                         String negative,
                                         ConfirmDialog.OnConfirmDialogListener listener,
                                         final BaseDialog.OnBackPressListener backPressListener) {
        final ConfirmDialog confirmationDialog = new ConfirmDialog(context, title,
                message, positive, negative, listener);
        confirmationDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (backPressListener == null) {
                        dialog.dismiss();
                    } else {
                        backPressListener.onBackPress(confirmationDialog);
                    }
                }
                return true;
            }
        });
        confirmationDialog.show();
    }
}
