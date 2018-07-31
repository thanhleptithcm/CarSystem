package com.matas.caroperatingsystem.ui.fragment.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseFragment;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.activity.manage.ManageActivity;
import com.matas.caroperatingsystem.ui.activity.staff.main.StaffActivity;
import com.matas.caroperatingsystem.ui.activity.user.UserActivity;
import com.matas.caroperatingsystem.ui.fragment.login.LoginFragment;
import com.matas.caroperatingsystem.ui.fragment.signUp.SignUpFragment;
import com.matas.caroperatingsystem.utils.AppConstants;
import com.matas.caroperatingsystem.widget.AppTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AuthFragFragment extends BaseFragment implements AuthFragContract.AuthFragView,
        View.OnClickListener {

    public static final String TAG = AuthFragFragment.class.getSimpleName();
    private LoginFragment mLoginFragment;
    private SignUpFragment mUserSignUpFragment;
    private SignUpFragment mDriverSignUpFragment;

    private AuthPagerAdapter mAdapter;
    private List<BaseFragment> mListFragment;
    private ViewPager vpContent;

    private AppTextView tvLogin;
    private AppTextView tvUserSignUp;
    private AppTextView tvDriverSignUp;

    @Inject
    AuthFragPresenter mPresenter;

    private OnAuthFragListener mOnAuthFragListener;

    public static AuthFragFragment newInstance() {
        Bundle args = new Bundle();
        AuthFragFragment fragment = new AuthFragFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnAuthFragListener(OnAuthFragListener onAuthFragListener) {
        this.mOnAuthFragListener = onAuthFragListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_auth;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        vpContent = view.findViewById(R.id.vp_content);
        tvLogin = view.findViewById(R.id.tv_login);
        tvUserSignUp = view.findViewById(R.id.tv_user_sign_up);
        tvDriverSignUp = view.findViewById(R.id.tv_driver_sign_up);

        initListener();
        initData();
    }

    private void initData() {
        mLoginFragment = LoginFragment.newInstance();
        mLoginFragment.setOnLoginListener(new LoginFragment.OnLoginListener() {
            @Override
            public void onLoginClick(String email, String password) {
                mPresenter.login(email, password);
            }
        });

        mUserSignUpFragment = SignUpFragment.newInstance(AppConstants.USER_SIGN_UP);
        mUserSignUpFragment.setOnSignUpListener(new SignUpFragment.OnSignUpListener() {
            @Override
            public void onSignUpClick(String email, String password, int type, String NIN) {
                mPresenter.signUp(email, password, type, NIN);
            }
        });

        mDriverSignUpFragment = SignUpFragment.newInstance(AppConstants.DRIVER_SIGN_UP);
        mDriverSignUpFragment.setOnSignUpListener(new SignUpFragment.OnSignUpListener() {
            @Override
            public void onSignUpClick(String email, String password, int type, String NIN) {
                mPresenter.signUp(email, password, type, NIN);
            }
        });

        mListFragment = new ArrayList<>();
        mListFragment.add(mLoginFragment);
        mListFragment.add(mUserSignUpFragment);
        mListFragment.add(mDriverSignUpFragment);

        mAdapter = new AuthPagerAdapter(getFragmentManager(), mListFragment);
        vpContent.setAdapter(mAdapter);
    }

    private void initListener() {
        tvLogin.setOnClickListener(this);
        tvUserSignUp.setOnClickListener(this);
        tvDriverSignUp.setOnClickListener(this);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mPresenter.getCurrentIndexFragment() == position) {
                    setSelectedHeader(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.setCurrentIndexFragment(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSelectedHeader(int position) {
        switch (position) {
            case 0:
                tvLogin.setSelected(true);
                tvUserSignUp.setSelected(false);
                tvDriverSignUp.setSelected(false);
                break;
            case 1:
                tvLogin.setSelected(false);
                tvUserSignUp.setSelected(true);
                tvDriverSignUp.setSelected(false);
                break;
            case 2:
                tvLogin.setSelected(false);
                tvUserSignUp.setSelected(false);
                tvDriverSignUp.setSelected(true);
                break;
        }
    }


    @Override
    public void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == tvLogin) {
            vpContent.setCurrentItem(0);
        } else if (v == tvUserSignUp) {
            vpContent.setCurrentItem(1);
        } else if (v == tvDriverSignUp) {
            vpContent.setCurrentItem(2);
        }
    }

    @Override
    public void loginSuccess(User user) {
        showToast("Login Success");
        if (mOnAuthFragListener != null) {
            mOnAuthFragListener.onAuthLoginSuccess(user.getType(), user.getFirstName());
        }
    }

    @Override
    public void signUpSuccess() {
        showToast("Sign up Success");
        vpContent.setCurrentItem(0);
    }


    public interface OnAuthFragListener {
        void onAuthLoginSuccess(int type, String firstName);
    }
}