package com.matas.caroperatingsystem.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public interface OnBackPressListener {
        void onBackPress(BaseDialog dialog);
    }
}
