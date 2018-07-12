package com.matas.caroperatingsystem.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseDialog;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppTextView;

public class ConfirmDialog extends BaseDialog implements View.OnClickListener {

    private String mTitle;
    private String mMessage;
    private String mPositiveText;
    private String mNegativeText;

    private AppButton mPositiveButton;
    private AppButton mNegativeButton;
    private OnConfirmDialogListener mOnConfirmDialogListener;

    public ConfirmDialog(Context context,
                         String title,
                         String message,
                         String positive,
                         String negative,
                         OnConfirmDialogListener onConfirmDialogListener) {
        super(context, R.style.VLDialogFull);
        this.mTitle = title;
        this.mMessage = message;
        this.mPositiveText = positive;
        this.mNegativeText = negative;
        this.mOnConfirmDialogListener = onConfirmDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.transparent));
            }
        }
        setContentView(R.layout.dialog_confirmation);

        initData();
        initListener();

    }

    private void initData() {
        AppTextView mTitleTextView = findViewById(R.id.title_text_view);
        AppTextView mMessageTextView = findViewById(R.id.message_text_view);
        mPositiveButton = findViewById(R.id.positive_button);
        mNegativeButton = findViewById(R.id.negative_button);

        if (TextUtils.isEmpty(mTitle)) {
            mTitleTextView.setVisibility(View.GONE);
        } else {
            mTitleTextView.setText(mTitle);
        }

        mMessageTextView.setText(mMessage);
        mPositiveButton.setText(mPositiveText);
        mNegativeButton.setText(mNegativeText);
    }

    private void initListener() {
        mPositiveButton.setOnClickListener(this);
        mNegativeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mOnConfirmDialogListener != null) {
            if (v == mPositiveButton) {
                mOnConfirmDialogListener.onConfirmDialogPositiveClick(ConfirmDialog.this);

            } else if (v == mNegativeButton) {
                mOnConfirmDialogListener.onConfirmDialogNegativeClick(ConfirmDialog.this);
            }
        }
    }

    public interface OnConfirmDialogListener {
        void onConfirmDialogPositiveClick(ConfirmDialog dialog);

        void onConfirmDialogNegativeClick(ConfirmDialog dialog);
    }
}
