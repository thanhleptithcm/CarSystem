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

public class OkDialog extends BaseDialog {

    private String mTitle, mMessage;
    private IOkDialogListener mListener;
    private AppTextView mTitleTextView;

    private AppTextView mMessageTextView;
    private AppButton mOkButton;

    public OkDialog(Context context, String title, String message, IOkDialogListener listener) {
        super(context, R.style.VLDialogFull);
        mTitle = title;
        mMessage = message;
        mListener = listener;
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
        setContentView(R.layout.dialog_ok);
        initData();
        initListener();
    }

    private void initData() {
        mTitleTextView = findViewById(R.id.title_text_view);
        mMessageTextView = findViewById(R.id.message_text_view);
        mOkButton = findViewById(R.id.ok_button);

        if (TextUtils.isEmpty(mTitle)) {
            mTitleTextView.setVisibility(View.GONE);
        } else {
            mTitleTextView.setText(mTitle);
        }

        mMessageTextView.setText(mMessage);
    }

    private void initListener() {
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onIOkDialogAnswerOk(OkDialog.this);
                } else {
                    dismiss();
                }
            }
        });
    }

    public interface IOkDialogListener {
        void onIOkDialogAnswerOk(OkDialog dialog);
    }
}
