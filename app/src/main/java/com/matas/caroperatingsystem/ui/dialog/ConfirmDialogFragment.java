package com.matas.caroperatingsystem.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.ui.base.BaseDialogFragment;

public class ConfirmDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    public static final String TAG = ConfirmDialogFragment.class.getSimpleName();

    private static final String TITLE_KEY = "TITLE_KEY";
    private static final String MESSAGE_KEY = "MESSAGE_KEY";
    private static final String POSITIVE_KEY = "POSITIVE_KEY";
    private static final String NEGATIVE_KEY = "NEGATIVE_KEY";

    TextView tvTitle;
    TextView tvMessage;
    Button btnNegative;
    Button btnPositive;

    private IPositiveListener mPositiveListener;
    private INegativeListener mNegativeListener;

    public static ConfirmDialogFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putString(MESSAGE_KEY, message);
        ConfirmDialogFragment fragment = new ConfirmDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ConfirmDialogFragment newInstance(String title, String message, String positive, String negative) {
        Bundle args = new Bundle();
        args.putString(TITLE_KEY, title);
        args.putString(MESSAGE_KEY, message);
        args.putString(POSITIVE_KEY, positive);
        args.putString(NEGATIVE_KEY, negative);
        ConfirmDialogFragment fragment = new ConfirmDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setPositiveListener(IPositiveListener positiveListener) {
        this.mPositiveListener = positiveListener;
    }

    public void setNegativeListener(INegativeListener negativeListener) {
        this.mNegativeListener = negativeListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.dialog_fragment_confirmation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = view.findViewById(R.id.tv_title);
        tvMessage = view.findViewById(R.id.tv_message);
        btnNegative = view.findViewById(R.id.btn_negative);
        btnPositive = view.findViewById(R.id.btn_positive);
    }

    @Override
    protected void initData() {


        if (getArguments() != null) {
            tvTitle.setText(getArguments().getString(TITLE_KEY));
            tvMessage.setText(getArguments().getString(MESSAGE_KEY));
            btnPositive.setText(getArguments().getString(POSITIVE_KEY, getString(R.string.action_ok)));
            btnNegative.setText(getArguments().getString(NEGATIVE_KEY, getString(R.string.action_cancel).toUpperCase()));

            tvTitle.setVisibility(TextUtils.isEmpty(tvTitle.getText()) ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {
        btnPositive.setOnClickListener(this);
        btnNegative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismissDialog(TAG);
        if (v == btnNegative) {
            if (mNegativeListener != null) {
                mNegativeListener.onNegative(ConfirmDialogFragment.this);
            }
        } else if (v == btnPositive) {
            if (mPositiveListener != null) {
                mPositiveListener.onPositive(ConfirmDialogFragment.this);
            }
        }
    }

    public interface INegativeListener {
        void onNegative(DialogFragment dialog);
    }

    public interface IPositiveListener {
        void onPositive(DialogFragment dialog);
    }
}
