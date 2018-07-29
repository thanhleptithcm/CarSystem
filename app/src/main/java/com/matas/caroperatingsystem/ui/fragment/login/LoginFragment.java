package com.matas.caroperatingsystem.ui.fragment.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseFragment;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppEditText;

import javax.inject.Inject;

public class LoginFragment extends BaseFragment implements LoginContract.LoginMvpView, View.OnClickListener {

    public static final String TAG = LoginFragment.class.getSimpleName();

    private AppEditText edtPhone;
    private AppEditText edtPassword;
    private AppButton btnLogin;
    private AppButton btnUserSignUp;
    private AppButton btnBikerSignUp;

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

        edtPhone = view.findViewById(R.id.edt_phone);
        edtPassword = view.findViewById(R.id.edt_pass_word);
        btnLogin = view.findViewById(R.id.btn_login);
        btnUserSignUp = view.findViewById(R.id.btn_user_sign_up);
        btnBikerSignUp = view.findViewById(R.id.btn_biker);

        initListener();
        initData();
    }

    private void initData() {
        btnLogin.setOnClickListener(this);
        btnUserSignUp.setOnClickListener(this);
        btnBikerSignUp.setOnClickListener(this);
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
            if (v == btnLogin) {
                getData();
            } else if (v == btnUserSignUp) {
                mOnLoginListener.onUserSignUp();
            } else if (v == btnBikerSignUp) {
                mOnLoginListener.onBikerSignUp();
            }
        }
    }

    private void getData() {
        CharSequence phoneStr = edtPhone.getText();
        CharSequence passwordStr = edtPassword.getText();

        if (TextUtils.isEmpty(phoneStr)) {
            showErrorDialog(getString(R.string.login_please_input_phone_number));
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            showErrorDialog(getString(R.string.login_please_input_password));
            return;
        }

        if (mOnLoginListener != null)
            mOnLoginListener.onLoginClick(phoneStr.toString(), passwordStr.toString());
    }

    public interface OnLoginListener {
        void onLoginClick(String email, String password);

        void onUserSignUp();

        void onBikerSignUp();
    }
}