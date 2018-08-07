package com.matas.caroperatingsystem.ui.fragment.manage_staff_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarFragment;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.widget.AppTextView;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import java.util.ArrayList;

import javax.inject.Inject;

public class DetailManageStaffFragment extends TopBarFragment implements DetailManageStaffContract.DetailManageStaffView,
        View.OnClickListener,
        DetailManageStaffAdapter.OnItemClickListener {

    public static final String TAG = DetailManageStaffFragment.class.getSimpleName();

    @Inject
    DetailManageStaffPresenter mPresenter;

    private OnStaffListener mOnStaffListener;
    private User mUser;
    private DetailManageStaffAdapter mDetailManageStaffAdapter;
    private AppTextView tvName;
    private AppTextView tvPhone;
    private AppTextView tvAddress;
    private AppTextView tvGender;
    private RecyclerView rcvHistory;
    private AppTopBar topBar;
    private ImageView imvGender;

    public static DetailManageStaffFragment newInstance(User user) {
        Bundle args = new Bundle();
        DetailManageStaffFragment fragment = new DetailManageStaffFragment();
        args.putParcelable(User.TAG, user);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnStaffListener(OnStaffListener onStaffListener) {
        this.mOnStaffListener = onStaffListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_manage_detail_staff;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        imvGender = view.findViewById(R.id.imv_gender);
        tvName = view.findViewById(R.id.tv_name);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvAddress = view.findViewById(R.id.tv_address);
        tvGender = view.findViewById(R.id.tv_gender);
        rcvHistory = view.findViewById(R.id.rcv_history);

        initListener();
        initData();
    }

    private void initData() {
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(User.TAG);
        }

        tvName.setText(String.format("%s %s %s", getString(R.string.name), mUser.getFirstName(), mUser.getLastName()));
        tvPhone.setText(String.format("%s %s", getString(R.string.phone_number), mUser.getPhone()));
        tvAddress.setText(String.format("%s %s", getString(R.string.address), mUser.getAddress()));
        imvGender.setImageResource(mUser.getGender().equalsIgnoreCase("male") ? R.drawable.ic_male : R.drawable.ic_female);
        mPresenter.setListBook(mUser.getBooking());
        mDetailManageStaffAdapter = new DetailManageStaffAdapter(getContext(),
                mPresenter.getListBook(),
                this);
        rcvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvHistory.setNestedScrollingEnabled(false);
        rcvHistory.setAdapter(mDetailManageStaffAdapter);
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
    public void onItemClick(int position) {

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
            topBar.initData(0, R.string.action_back, R.string.detail_manage_staff, 0, 0);
            topBar.setVisible(View.GONE, View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.GONE);

            topBar.setOnTopBarListener(new AppTopBar.OnTopBarListener() {
                @Override
                public void onImvLeftOneClick() {

                }

                @Override
                public void onTvLeftOneClick() {
                    getBaseActivity().onBackPressed();
                }

                @Override
                public void onTvRightOneClick() {

                }

                @Override
                public void onImvRightOneClick() {

                }
            });
        }
    }


    public interface OnStaffListener {
        void onStaffClick();
    }
}