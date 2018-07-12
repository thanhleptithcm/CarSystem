package com.matas.caroperatingsystem.ui.fragment.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseFragment;
import com.matas.caroperatingsystem.widget.AppEditText;
import com.matas.caroperatingsystem.widget.AppTextView;

import javax.inject.Inject;

public class ProfileFragment extends BaseFragment implements ProfileContract.ProfileView, View.OnClickListener {

    public static final String TAG = ProfileFragment.class.getSimpleName();

    private AppEditText edtFirstName;
    private AppEditText edtLastName;
    private AppEditText edtAddress;
    private AppTextView tvMale;
    private AppTextView tvFemale;

    @Inject
    ProfilePresenter mPresenter;

    private OnProfileListener mOnProfileListener;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnProfileListener(OnProfileListener onProfileListener) {
        this.mOnProfileListener = onProfileListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        edtFirstName = view.findViewById(R.id.edt_first_name);
        edtLastName = view.findViewById(R.id.edt_last_name);
        edtAddress = view.findViewById(R.id.edt_address);
        tvMale = view.findViewById(R.id.tv_male);
        tvFemale = view.findViewById(R.id.tv_female);

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
        if (mOnProfileListener != null) {

        }
    }

    public interface OnProfileListener {

    }
}