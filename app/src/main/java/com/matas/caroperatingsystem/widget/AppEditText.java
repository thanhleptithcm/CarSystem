package com.matas.caroperatingsystem.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.utils.KeyboardUtils;

public class AppEditText extends AppCompatEditText {

    private static final String TAG = AppEditText.class.getSimpleName();

    private OnKeyboardListener mKeyboardListener;
    private OnFocusChangeListener mFocusChangedListener;

    public AppEditText(Context context) {
        super(context);
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnKeyboardListener(OnKeyboardListener keyboardListener) {
        this.mKeyboardListener = keyboardListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.mFocusChangedListener = onFocusChangeListener;
    }

    private void init(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.AppEditText);
        //font
        String customFont = ctx.getString(R.string.font_calibre_regular);
        if (a.hasValue(R.styleable.AppEditText_edtFont)) {
            customFont = a.getString(R.styleable.AppEditText_edtFont);
        }
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent event) {
        if (isFocusable()) {
            if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    || event.getAction() == KeyEvent.KEYCODE_ENTER)
                    && event.getAction() == KeyEvent.ACTION_UP) {
                clearFocus();
                return true;
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            KeyboardUtils.showSoftInput(this);
        } else {
            KeyboardUtils.hideSoftInput(this);
            if (mKeyboardListener != null) {
                mKeyboardListener.onHideKeyboard(this);
            }
        }
        if (mFocusChangedListener != null) {
            mFocusChangedListener.onFocus(this, focused);
        }
    }

    @Override
    public void onEditorAction(int actionId) {
        super.onEditorAction(actionId);
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE) {
            clearFocus();
        }
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface typeface;
        try {
            typeface = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Unable to load typeface: " + e.getMessage());
            return false;
        }

        setTypeface(typeface);
        return true;
    }

    public interface OnKeyboardListener {
        void onHideKeyboard(AppEditText editText);
    }

    public interface OnFocusChangeListener {
        void onFocus(AppEditText editText, boolean hasFocus);
    }
}
