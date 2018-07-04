package com.matas.caroperatingsystem.ui.fragment.login;

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

public class LoginFragment extends BaseFragment implements LoginContract.LoginMvpView, View.OnClickListener {

    public static final String TAG = LoginFragment.class.getSimpleName();

    private AppEditText edtEmail;
    private AppEditText edtPassword;
    private AppTextView tvSignUp;
    private AppTextView tvForgotPass;
    private AppButton btnLogin;
    private AppButton btnLoginFaceBook;
    private AppButton btnLoginGoogle;


    @Inject
    LoginPresenter mPresenter;

    private OnLoginListener mOnLoginListener;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        this.mOnLoginListener = onLoginListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        edtEmail = view.findViewById(R.id.edt_user_name);
        edtPassword = view.findViewById(R.id.edt_pass_word);
        tvSignUp = view.findViewById(R.id.tv_sign_up);
        tvForgotPass = view.findViewById(R.id.tv_forgot_password);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLoginFaceBook = view.findViewById(R.id.btn_facebook);
        btnLoginGoogle = view.findViewById(R.id.btn_google);

        initListener();
        initData();
    }

    private void initData() {
        tvSignUp.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLoginFaceBook.setOnClickListener(this);
        btnLoginGoogle.setOnClickListener(this);
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
        if (mOnLoginListener != null) {
            if (v == tvSignUp) {
                mOnLoginListener.onSignUpClick();
            } else if (v == tvForgotPass) {
                mOnLoginListener.onForgotPasswordClick();
            } else if (v == btnLogin) {
                getData();
            } else if (v == btnLoginFaceBook) {
                mOnLoginListener.onLoginWithFaceBook();
            } else if (v == btnLoginGoogle) {
                mOnLoginListener.onLoginWithGoogle();
            }
        }
    }

    private void getData() {
        CharSequence emailStr = edtEmail.getText();
        CharSequence passwordStr = edtPassword.getText();

        if (TextUtils.isEmpty(emailStr)) {
            showErrorDialog(getString(R.string.login_please_input_email));
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            showErrorDialog(getString(R.string.login_please_input_email));
            return;
        }

        if (!CommonUtils.isEmailValid(emailStr)) {
            showErrorDialog(getString(R.string.login_email_invalid));
            return;
        }

        if (mOnLoginListener != null)
            mOnLoginListener.onLoginClick(emailStr.toString(), passwordStr.toString());
    }

    public interface OnLoginListener {
        void onSignUpClick();

        void onForgotPasswordClick();

        void onLoginClick(String email, String password);

        void onLoginWithFaceBook();

        void onLoginWithGoogle();
    }
}