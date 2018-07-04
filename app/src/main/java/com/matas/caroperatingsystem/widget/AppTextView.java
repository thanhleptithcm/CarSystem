package com.matas.caroperatingsystem.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.matas.caroperatingsystem.R;

public class AppTextView extends AppCompatTextView {

    private static final String TAG = AppTextView.class.getSimpleName();

//    private boolean mActiveEffect;

    public AppTextView(Context context) {
        super(context);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.AppTextView);

        //active - inactive effect
//        mActiveEffect = a.getBoolean(R.styleable.FlyNowTextView_tvActiveEffect, false);

        //font
        String customFont = ctx.getString(R.string.font_calibre_regular);
        if (a.hasValue(R.styleable.AppTextView_tvFont)) {
            customFont = a.getString(R.styleable.AppTextView_tvFont);
        }
        setCustomFont(ctx, customFont);
        a.recycle();
    }

//    @Override
//    public void setSelected(boolean selected) {
//        if (mActiveEffect) {
//            setAlpha(selected ? AppConstants.ALPHA_ACTIVE : AppConstants.ALPHA_INACTIVE);
//        }
//        super.setSelected(selected);
//    }

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

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
