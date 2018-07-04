package com.matas.caroperatingsystem.widget.topbar;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.matas.caroperatingsystem.R;

public class AppTopBar extends ConstraintLayout implements ITopBar {
    private FrameLayout frLeftOne;
    private ImageView imvLeftOne;
    private TextView tvLeftOne;
    private TextView tvTitle;
    private FrameLayout frRightOne;
    private ImageView imvRightOne;

    public AppTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_topbar,
                this,
                false
        );

        frLeftOne = view.findViewById(R.id.fr_left_one);
        imvLeftOne = view.findViewById(R.id.imv_left_one);
        tvLeftOne = view.findViewById(R.id.tv_left_one);
        tvTitle = view.findViewById(R.id.tv_title);
        frRightOne = view.findViewById(R.id.fr_right_one);
        imvRightOne = view.findViewById(R.id.imv_right_one);

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
    public void initData(int srcImvLeftOne, String textTvTitle, int srcImvRightOne) {
        setDrawable(frLeftOne, imvLeftOne, srcImvLeftOne);
        tvTitle.setText(textTvTitle);
        setDrawable(frRightOne, imvRightOne, srcImvRightOne);
    }

    @Override
    public void initData(int textLeftOne, int textTvTitle, int srcImvRightOne) {
        tvLeftOne.setText(textLeftOne);
        tvTitle.setText(textTvTitle);
        setDrawable(frRightOne, imvRightOne, srcImvRightOne);
    }

    @Override
    public void initData(String textLeftOne, String textTvTitle, int srcImvRightOne) {
        tvLeftOne.setText(textLeftOne);
        tvTitle.setText(textTvTitle);
        setDrawable(frRightOne, imvRightOne, srcImvRightOne);
    }

    @Override
    public void setVisible(int imvLeftOne, int tvLeftOne, int tvTitle, int imvRightOne) {
        this.frLeftOne.setVisibility(imvLeftOne);
        this.tvLeftOne.setVisibility(tvLeftOne);
        this.tvTitle.setVisibility(tvTitle);
        this.frRightOne.setVisibility(imvRightOne);
    }
}