package com.matas.caroperatingsystem.ui.fragment.profile_staff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BaseFragment;
import com.matas.caroperatingsystem.base.TopBarFragment;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.utils.CommonUtils;
import com.matas.caroperatingsystem.widget.AppEditText;
import com.matas.caroperatingsystem.widget.AppTextView;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class ProfileFragment extends TopBarFragment implements ProfileContract.ProfileView, View.OnClickListener {

    public static final String TAG = ProfileFragment.class.getSimpleName();

    private AppEditText edtFirstName;
    private AppEditText edtLastName;
    private AppEditText edtAddress;
    private AppTextView tvMale;
    private AppTextView tvFemale;
    private AppTopBar topBar;

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
        tvMale.setSelected(true);
    }

    private void initListener() {
        tvMale.setOnClickListener(this);
        tvFemale.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvMale) {
            tvMale.setSelected(!tvMale.isSelected());
            if(tvMale.isSelected()){
                tvFemale.setSelected(false);
            } else {
                tvFemale.setSelected(true);
            }
        } else if (v == tvFemale) {
            tvFemale.setSelected(!tvFemale.isSelected());
            if(tvFemale.isSelected()){
                tvMale.setSelected(false);
            } else {
                tvMale.setSelected(true);
            }
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
            topBar.initData(0, R.string.action_back, R.string.staff_update_profile, R.string.action_save, 0);
            topBar.setVisible(View.GONE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.GONE);

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
                    getData();
                }

                @Override
                public void onImvRightOneClick() {

                }
            });
        }
    }

    private void getData() {
        CharSequence firstNameStr = edtFirstName.getText();
        CharSequence lastNameStr = edtLastName.getText();
        CharSequence addressStr = edtAddress.getText();

        if (TextUtils.isEmpty(firstNameStr)) {
            showErrorDialog(getString(R.string.profile_please_enter_first_name));
            return;
        }

        if (TextUtils.isEmpty(lastNameStr)) {
            showErrorDialog(getString(R.string.profile_please_enter_last_name));
            return;
        }

        if (TextUtils.isEmpty(addressStr)) {
            showErrorDialog(getString(R.string.profile_please_enter_address));
            return;
        }

        if (!tvMale.isSelected() && !tvFemale.isSelected()) {
            showErrorDialog(getString(R.string.profile_please_select_gender));
            return;
        }

        if (mOnProfileListener != null) {
            mOnProfileListener.onUpdateProfile(firstNameStr.toString(), lastNameStr.toString(), addressStr.toString(), tvMale.isSelected() ? "male" : "female");
        }
    }

    public interface OnProfileListener {
        void onUpdateProfile(String firstName, String lastName, String address, String gender);
    }

    @Override
    public void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }
}