package com.matas.caroperatingsystem.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.di.component.ActivityComponent;

public abstract class BaseFragment extends Fragment implements MvpView {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private BaseActivity mActivity;

    protected void onFragmentChangedToTopBackStack() {
    }

    public boolean touchOutSizeClearEditTextFocus() {
        return false;
    }

    public boolean onBackPress() {
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
            this.mActivity.onFragmentAttached();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getCoordinateLayout(), container, false);
        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (mActivity != null && mActivity.getSupportFragmentManager().getBackStackEntryCount() == 1 && enter) {
            return super.onCreateAnimation(transit, true, nextAnim);
        } else {
            if (FragmentUtils.sDisableFragmentAnimations) {
                nextAnim = enter ? R.anim.pop_enter : R.anim.pop_exit;
            } else {
                nextAnim = enter ? R.anim.enter : R.anim.exit;
            }
            return AnimationUtils.loadAnimation(getActivity(), nextAnim);
        }
    }

    @Override
    public void showLoading() {
        if (mActivity != null) {
            mActivity.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mActivity != null) {
            mActivity.hideLoading();
        }
    }

    @Override
    public void showToast(int resId) {
        if (mActivity != null) {
            mActivity.showToast(resId);
        }
    }

    @Override
    public void showToast(String message) {
        if (mActivity != null) {
            mActivity.showToast(message);
        }
    }

    @Override
    public void showErrorDialog(int resId) {
        if (mActivity != null) {
            mActivity.showErrorDialog(resId);
        }
    }

    @Override
    public void showErrorDialog(String message) {
        if (mActivity != null) {
            mActivity.showErrorDialog(message);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mActivity.onFragmentDetached(getTag());
        mActivity = null;
        super.onDetach();
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
