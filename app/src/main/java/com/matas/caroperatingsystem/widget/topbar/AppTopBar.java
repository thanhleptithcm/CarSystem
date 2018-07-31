package com.matas.caroperatingsystem.widget.topbar;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.matas.caroperatingsystem.R;

public class AppTopBar extends ConstraintLayout implements ITopBar,
        View.OnClickListener {

    public FrameLayout mLeftOneFrameLayout;
    public ImageView mLeftOneImageView;
    public TextView mLeftOneTextView;

    public TextView mTitleTextView;

    public FrameLayout mRightOneFrameLayout;
    public ImageView mRightOneImageView;
    public TextView mRightOneTextView;

    public ConstraintLayout mConstraintLayout;

    private OnTopBarListener mOnTopBarListener;

    public AppTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_topbar,
                this,
                false
        );
        mConstraintLayout = view.findViewById(R.id.constraint_layout);
        mLeftOneFrameLayout = view.findViewById(R.id.left_one_frame_layout);
        mLeftOneImageView = view.findViewById(R.id.left_one_image_view);
        mLeftOneTextView = view.findViewById(R.id.left_one_text_view);

        mTitleTextView = view.findViewById(R.id.title_text_view);

        mRightOneTextView = view.findViewById(R.id.right_one_text_view);
        mRightOneFrameLayout = view.findViewById(R.id.right_one_frame_layout);
        mRightOneImageView = view.findViewById(R.id.right_one_image_view);

        mLeftOneFrameLayout.setOnClickListener(this);
        mLeftOneTextView.setOnClickListener(this);
        mRightOneTextView.setOnClickListener(this);
        mRightOneFrameLayout.setOnClickListener(this);

        this.addView(view);
    }

    private void setDrawable(FrameLayout frWrapper, ImageView imageView, int src) {
        if (frWrapper == null) {
            imageView.setTag(src);
        } else {
            frWrapper.setTag(src);
        }
        imageView.setImageResource(src);
    }

    @Override
    public void initData(@DrawableRes int srcLeftOneImv,
                         @StringRes int textLeftOneTv,
                         @StringRes int textTitleTv,
                         @StringRes int textRightOneTv,
                         @DrawableRes int srcRightOneImv) {

        setDrawable(mLeftOneFrameLayout, mLeftOneImageView, srcLeftOneImv);
        if (textLeftOneTv != 0) {
            mLeftOneTextView.setText(textLeftOneTv);
            if (mLeftOneTextView.getText().toString().equalsIgnoreCase("Back")) {
                mLeftOneTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_left_arrow, 0, 0, 0);
            }
        }
        if (textTitleTv != 0) {
            mTitleTextView.setText(textTitleTv);
        }
        if (textRightOneTv != 0) {
            mRightOneTextView.setText(textRightOneTv);
        }
        setDrawable(mRightOneFrameLayout, mRightOneImageView, srcRightOneImv);
    }

    @Override
    public void setVisible(int leftOneImv, int leftOneTv, int titleTv, int rightOneTv, int rightOneImv) {
        mLeftOneFrameLayout.setVisibility(leftOneImv);
        mLeftOneTextView.setVisibility(leftOneTv);
        mTitleTextView.setVisibility(titleTv);
        mRightOneTextView.setVisibility(rightOneTv);
        mRightOneFrameLayout.setVisibility(rightOneImv);
    }

    @Override
    public void setDrawableTextLeftOne(int left, int top, int right, int bottom) {
        this.mLeftOneTextView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void setOnTopBarListener(OnTopBarListener callBack) {
        this.mOnTopBarListener = callBack;
    }

    @Override
    public void onClick(View v) {
        if (mOnTopBarListener != null) {
            if (v == mLeftOneFrameLayout) {
                mOnTopBarListener.onImvLeftOneClick();

            } else if (v == mLeftOneTextView) {
                mOnTopBarListener.onTvLeftOneClick();

            } else if (v == mRightOneTextView) {
                mOnTopBarListener.onTvRightOneClick();

            } else if (v == mRightOneFrameLayout) {
                mOnTopBarListener.onImvRightOneClick();
            }
        }
    }

    public interface OnTopBarListener {
        void onImvLeftOneClick();

        void onTvLeftOneClick();

        void onTvRightOneClick();

        void onImvRightOneClick();
    }
}