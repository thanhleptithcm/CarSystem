package com.matas.caroperatingsystem.ui.fragment.signUp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.ui.base.BaseFragment;
import com.matas.caroperatingsystem.utils.CommonUtils;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppEditText;
import com.matas.caroperatingsystem.widget.AppTextView;

import javax.inject.Inject;

public class SignUpFragment extends BaseFragment implements SignUpContract.SignUpMvpView, View.OnClickListener {

    public static final String TAG = SignUpFragment.class.getSimpleName();

    private AppEditText edtEmail;
    private AppEditText edtPassword;
    private AppEditText edtConfirmPassword;
    private AppButton btnSignUp;


    @Inject
    SignUpPresenter mPresenter;

    private OnSignUpListener mOnSignUpListener;

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
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

        edtEmail = view.findViewById(R.id.edt_user_name);
        edtPassword = view.findViewById(R.id.edt_pass_word);
        edtConfirmPassword = view.findViewById(R.id.edt_confirm_pass_word);
        btnSignUp = view.findViewById(R.id.btn_login);

        initListener();
        initData();
    }

    private void initData() {
        btnSignUp.setOnClickListener(this);
    }

    private void initListener() {
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
        CharSequence emailStr = edtEmail.getText();
        CharSequence passwordStr = edtPassword.getText();
        CharSequence confirmPasswordStr = edtConfirmPassword.getText();

        if (TextUtils.isEmpty(emailStr)) {
            showErrorDialog(getString(R.string.login_please_input_email));
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            showErrorDialog(getString(R.string.login_please_input_email));
            return;
        }

        if (TextUtils.isEmpty(confirmPasswordStr)) {
            showErrorDialog(getString(R.string.login_please_input_confirm_password));
            return;
        }

        if (!CommonUtils.isEmailValid(emailStr)) {
            showErrorDialog(getString(R.string.login_email_invalid));
            return;
        }

        if (mOnSignUpListener != null)
            mOnSignUpListener.onSignUpClick(emailStr.toString(), passwordStr.toString(), confirmPasswordStr.toString());
    }

    public interface OnSignUpListener {
        void onSignUpClick(String email, String password, String confirmPass);
    }
}