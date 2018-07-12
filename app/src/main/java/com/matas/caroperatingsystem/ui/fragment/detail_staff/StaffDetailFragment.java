package com.matas.caroperatingsystem.ui.fragment.detail_staff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseFragment;

import javax.inject.Inject;

public class StaffDetailFragment extends BaseFragment implements StaffDetailContract.StaffDetailView, View.OnClickListener {

    public static final String TAG = StaffDetailFragment.class.getSimpleName();

    @Inject
    StaffDetailPresenter mPresenter;

    private OnStaffListener mOnStaffListener;

    public static StaffDetailFragment newInstance() {
        Bundle args = new Bundle();
        StaffDetailFragment fragment = new StaffDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnStaffListener(OnStaffListener onStaffListener) {
        this.mOnStaffListener = onStaffListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_detail_staff;
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

    public interface OnStaffListener {
        void onStaffClick();
    }
}