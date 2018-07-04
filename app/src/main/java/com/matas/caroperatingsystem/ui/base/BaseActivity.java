package com.matas.caroperatingsystem.ui.base;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.matas.caroperatingsystem.MainApplication;
import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.di.component.ActivityComponent;
import com.matas.caroperatingsystem.di.component.DaggerActivityComponent;
import com.matas.caroperatingsystem.di.module.ActivityModule;
import com.matas.caroperatingsystem.ui.activity.login.LoginActivity;
import com.matas.caroperatingsystem.ui.dialog.AppLoadingDialog;
import com.matas.caroperatingsystem.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements MvpView,
        BaseFragment.Callback,
        FragmentManager.OnBackStackChangedListener {

    public static final String TAG = BaseActivity.class.getSimpleName();

    private List<String> mTagList = new ArrayList<>();

    private ActivityComponent mActivityComponent;
    private AppLoadingDialog mProgressDialog;
    protected BaseFragment mCurrentFragment;

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(MainApplication.getApplicationComponent())
                .build();
        setContentView(getCoordinateLayout());

        getSupportFragmentManager().removeOnBackStackChangedListener(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }


    @Override
    public void showLoading() {
        mProgressDialog = new AppLoadingDialog(this);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(BaseActivity.this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDialog(@StringRes int resId) {
        showOKAlertMessage(getString(resId), null);
    }

    @Override
    public void showErrorDialog(String message) {
        showOKAlertMessage(message, null);
    }

    @Override
    public boolean isNetworkConnected() {
        return true;
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            KeyboardUtils.hideSoftInput(view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    //region push pop fragments
    protected int getContainerId() {
        return 0;
    }

    private int getBackStackFragmentCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    public void pushFragment(BaseFragment fragment, String tag) {
        pushFragment(fragment, tag, false);
    }

    public void pushFragment(BaseFragment fragment, String tag, boolean anim) {
        pushFragment(fragment, getContainerId(), true, tag, anim);
    }

    public void pushFragment(BaseFragment fragment, int container, boolean isHide, String tag, boolean anim) {
        if (fragment == null) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mTagList.add(tag);
        if (anim) {
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        }
        if (isHide && mCurrentFragment != null) {
            ft.hide(mCurrentFragment);
        }
        ft.add(container, fragment, tag);
        ft.addToBackStack(tag);
        mCurrentFragment = fragment;
        ft.commitAllowingStateLoss();
    }

    protected boolean canPopFragment() {
        return getBackStackFragmentCount() > 1;
    }

    public void popFragment(String tag) {
        FragmentUtils.sDisableFragmentAnimations = true;
        if (TextUtils.isEmpty(tag)) {
            if (canPopFragment()) {
                mTagList.remove(mCurrentFragment.getTag());
                FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(getBackStackFragmentCount() - 2);
                mCurrentFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(entry.getName());
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                finish();
            }
        } else {
            List<String> remainTagList = new ArrayList<>();
            for (String s : mTagList) {
                if (TextUtils.equals(s, tag)) {
                    mTagList = remainTagList;
                    break;
                } else {
                    remainTagList.add(s);
                }
            }
            if (mTagList.size() > 0) {
                mCurrentFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(mTagList.get(mTagList.size() - 1));
            }
            getSupportFragmentManager().popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        if (mCurrentFragment != null) {
            mCurrentFragment.onFragmentChangedToTopBackStack();
        }
        FragmentUtils.sDisableFragmentAnimations = false;
    }

    protected void newProcess(BaseFragment fragment, String tag, boolean anim) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        pushFragment(fragment, tag, anim);
    }

    //endregion push pop fragments

    //region handle back press

    @Override
    public void onBackPressed() {
        if (isBack()) {
            String tag = null;
            popFragment(tag);
        }
    }

    protected boolean isBack() {
        return mCurrentFragment == null || mCurrentFragment.onBackPress();
    }

    //endregion handle back press

    //region fragment back stack changed (just use for add fragment)
    @Override
    public void onBackStackChanged() {
        if (!FragmentUtils.sDisableFragmentAnimations && mCurrentFragment != null) {
            mCurrentFragment.onFragmentChangedToTopBackStack();
        }
    }

    //endregion fragment back stack changed

    public void showOKAlertMessage(String message, DialogInterface.OnClickListener listener) {
        DialogInterface.OnClickListener defaultListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        };
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.action_ok, (listener == null ? defaultListener : listener))
                .create()
                .show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mCurrentFragment != null && mCurrentFragment.touchOutSizeClearEditTextFocus() && event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
