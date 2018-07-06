package com.matas.caroperatingsystem.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.ui.base.BaseFragment;
import com.matas.caroperatingsystem.ui.base.TopBarFragment;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class HomeFragment extends TopBarFragment implements HomeContract.HomeView, View.OnClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    HomePresenter mPresenter;

    private OnHomeListener mOnHomeListener;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnHomeListener(OnHomeListener onHomeListener) {
        this.mOnHomeListener = onHomeListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_home;
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
        if (mOnHomeListener != null) {

        }
    }


    @Override
    protected void onFragmentChangedToTopBackStack() {
        super.onFragmentChangedToTopBackStack();
        setupTopBar();
    }

    @Override
    protected void setupTopBar() {
        if (isAdded()) {
            AppTopBar topBar = getTopBar();
            if (topBar != null) {
                topBar.initData(R.drawable.ic_menu, getString(R.string.home), 0);
                topBar.setVisible(View.VISIBLE, View.GONE, View.VISIBLE, View.INVISIBLE);
            }
        }
    }


    public interface OnHomeListener {
        void onHomeClick();
    }
}