package com.matas.caroperatingsystem.ui.fragment.staff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseFragment;
import com.matas.caroperatingsystem.base.TopBarFragment;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.widget.AppEditText;
import com.matas.caroperatingsystem.widget.AppTextView;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class StaffFragment extends TopBarFragment implements StaffContract.StaffView, View.OnClickListener {

    public static final String TAG = StaffFragment.class.getSimpleName();
    private AppTopBar topBar;

    @Inject
    StaffPresenter mPresenter;

    private OnStaffListener mOnStaffListener;

    public static StaffFragment newInstance() {
        Bundle args = new Bundle();
        StaffFragment fragment = new StaffFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnStaffListener(OnStaffListener onStaffListener) {
        this.mOnStaffListener = onStaffListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_staff;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        initListener();
        initData();
    }

    private void initData() {

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
        if (mOnStaffListener != null) {

        }
    }


    @Override
    protected void onFragmentChangedToTopBackStack() {
        super.onFragmentChangedToTopBackStack();
        setupTopBar();
    }

    @Override
    protected void setupTopBar() {
        topBar = getTopBar();
        if (topBar != null) {
            topBar.initData(0, 0, R.string.staff, R.string.action_logout, 0);
            topBar.setVisible(View.GONE, View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.GONE);

            topBar.setOnTopBarListener(new AppTopBar.OnTopBarListener() {
                @Override
                public void onImvLeftOneClick() {

                }

                @Override
                public void onTvLeftOneClick() {

                }

                @Override
                public void onTvRightOneClick() {
                    showConfirmDialog(getContext(), null, getString(R.string.home_do_you_want_to_logout), new ConfirmDialog.OnConfirmDialogListener() {
                        @Override
                        public void onConfirmDialogPositiveClick(ConfirmDialog dialog) {
                            if(mOnStaffListener != null){
                                mOnStaffListener.onLogout();
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onConfirmDialogNegativeClick(ConfirmDialog dialog) {
                            dialog.dismiss();
                        }
                    }, null);
                }

                @Override
                public void onImvRightOneClick() {

                }
            });
        }
    }

    public interface OnStaffListener {
        void onLogout();
    }
}