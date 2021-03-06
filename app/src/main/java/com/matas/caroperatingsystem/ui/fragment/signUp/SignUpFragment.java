package com.matas.caroperatingsystem.ui.fragment.signUp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseFragment;
import com.matas.caroperatingsystem.utils.AppConstants;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppEditText;

import javax.inject.Inject;

public class SignUpFragment extends BaseFragment implements SignUpContract.SignUpMvpView, View.OnClickListener {

    public static final String TAG = SignUpFragment.class.getSimpleName();

    private AppEditText edtPhone;
    private AppEditText edtPassword;
    private AppEditText edtConfirmPassword;
    private AppEditText edtNIN;
    private AppButton btnSignUp;

    private int mType;
    @Inject
    SignUpPresenter mPresenter;

    private OnSignUpListener mOnSignUpListener;

    public static SignUpFragment newInstance(int type) {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        args.putInt(SignUpFragment.TAG, type);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnSignUpListener(OnSignUpListener onSignUpListener) {
        this.mOnSignUpListener = onSignUpListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        edtPhone = view.findViewById(R.id.edt_phone);
        edtPassword = view.findViewById(R.id.edt_pass_word);
        edtConfirmPassword = view.findViewById(R.id.edt_confirm_pass_word);
        edtNIN = view.findViewById(R.id.edt_nin);
        btnSignUp = view.findViewById(R.id.btn_login);

        initListener();
        initData();
    }

    private void initData() {
        if (getArguments() != null) {
            mType = getArguments().getInt(SignUpFragment.TAG);
        }

        if (mType == AppConstants.DRIVER_SIGN_UP) {
            edtNIN.setVisibility(View.VISIBLE);
        }

    }

    private void initListener() {
        btnSignUp.setOnClickListener(this);
    }


    @Override
    public void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (mOnSignUpListener != null) {
            if (v == btnSignUp) {
                getData();
            }
        }
    }

    private void getData() {
        CharSequence phoneStr = edtPhone.getText();
        CharSequence passwordStr = edtPassword.getText();
        CharSequence confirmPasswordStr = edtConfirmPassword.getText();
        CharSequence ninStr = edtNIN.getText();

        if (TextUtils.isEmpty(phoneStr)) {
            showErrorDialog(getString(R.string.login_please_input_phone_number));
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            showErrorDialog(getString(R.string.login_please_input_password));
            return;
        }

        if (TextUtils.isEmpty(confirmPasswordStr)) {
            showErrorDialog(getString(R.string.login_please_input_confirm_password));
            return;
        }

        if (!passwordStr.toString().equalsIgnoreCase(confirmPasswordStr.toString())) {
            showErrorDialog("Password not valid");
            return;
        }

        if (mType == AppConstants.DRIVER_SIGN_UP) {
            if (TextUtils.isEmpty(ninStr)) {
                showErrorDialog(getString(R.string.login_please_input_nin));
                return;
            }

            if (ninStr.length() != 9) {
                showErrorDialog(getString(R.string.login_nin_invalid));
                return;
            }
        }


        if (mOnSignUpListener != null) {
            mOnSignUpListener.onSignUpClick(phoneStr.toString(), passwordStr.toString(), mType, ninStr.toString());
        }
    }

    public interface OnSignUpListener {
        void onSignUpClick(String email, String password, int type, String NIN);
    }
}