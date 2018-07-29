package com.matas.caroperatingsystem.ui.fragment.manage_staff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarFragment;
import com.matas.caroperatingsystem.data.model.Staff;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class ManageStaffFragment extends TopBarFragment implements ManageStaffContract.ManageStaffView,
        ManageStaffAdapter.OnItemClickListener {

    public static final String TAG = ManageStaffFragment.class.getSimpleName();
    private RecyclerView mRcvStaff;
    private AppTopBar topBar;
    @Inject
    ManageStaffPresenter mPresenter;

    private OnStaffListener mOnStaffListener;
    private ManageStaffAdapter mAdapter;

    public static ManageStaffFragment newInstance() {
        Bundle args = new Bundle();
        ManageStaffFragment fragment = new ManageStaffFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnStaffListener(OnStaffListener onStaffListener) {
        this.mOnStaffListener = onStaffListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_manage_staff;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        mRcvStaff = view.findViewById(R.id.rcv_staff);

        initListener();
        initData();
    }

    private void initData() {
        mAdapter = new ManageStaffAdapter(getContext(), mPresenter.getListStaff(), this);
        mRcvStaff.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcvStaff.setAdapter(mAdapter);

        mPresenter.fetchListStaff();
    }

    private void initListener() {
    }

    @Override
    public void getListStaffSuccess() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        if (mOnStaffListener != null) {
            mOnStaffListener.onStaffClick(mPresenter.getListStaff().get(position));
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
            topBar.initData(0, 0, R.string.manage_staff, R.string.action_logout, 0);
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
                            mPresenter.setLogOut();
                            dialog.dismiss();
                            AuthActivity.startActivity(getActivity());
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
        void onStaffClick(Staff staff);
    }

    @Override
    public void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }
}