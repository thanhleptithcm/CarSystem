package com.matas.caroperatingsystem.ui.fragment.setup_price;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarFragment;
import com.matas.caroperatingsystem.data.AppStorage;
import com.matas.caroperatingsystem.widget.AppEditText;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SetupPriceFragment extends TopBarFragment implements SetupPriceContract.SetupPriceView {

    public static final String TAG = SetupPriceFragment.class.getSimpleName();
    private AppTopBar topBar;
    private AppEditText edt1;
    private AppEditText edt2;
    private AppEditText edt3;
    private AppEditText edt4;
    private AppEditText edt5;
    private AppEditText edt6;
    private AppEditText edt7;
    @Inject
    SetupPricePresenter mPresenter;

    private OnSetUpPriceListener mOnSetUpPriceListener;

    public static SetupPriceFragment newInstance() {
        Bundle args = new Bundle();
        SetupPriceFragment fragment = new SetupPriceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnSetUpPriceListener(OnSetUpPriceListener onSetUpPriceListener) {
        this.mOnSetUpPriceListener = onSetUpPriceListener;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.fragment_manage_set_up_price;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);
        edt1 = view.findViewById(R.id.edt1);
        edt2 = view.findViewById(R.id.edt2);
        edt3 = view.findViewById(R.id.edt3);
        edt4 = view.findViewById(R.id.edt4);
        edt5 = view.findViewById(R.id.edt5);
        edt6 = view.findViewById(R.id.edt6);
        edt7 = view.findViewById(R.id.edt7);
        initListener();
        initData();
    }

    private void initData() {

    }

    private void initListener() {
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
            topBar.initData(0, R.string.action_back, R.string.manage_set_up_price, R.string.action_save, 0);
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
        if (TextUtils.isEmpty(edt1.getText())) {
            showErrorDialog("Please Input Price 0h -> 6h");
            return;
        }

        if (TextUtils.isEmpty(edt2.getText())) {
            showErrorDialog("Please Input Price 6h -> 9h");
            return;
        }

        if (TextUtils.isEmpty(edt3.getText())) {
            showErrorDialog("Please Input Price 9h -> 12h");
            return;
        }

        if (TextUtils.isEmpty(edt4.getText())) {
            showErrorDialog("Please Input Price 12h -> 13h");
            return;
        }

        if (TextUtils.isEmpty(edt5.getText())) {
            showErrorDialog("Please Input Price 13h -> 17h");
            return;
        }

        if (TextUtils.isEmpty(edt6.getText())) {
            showErrorDialog("Please Input Price 17h -> 20h");
            return;
        }

        if (TextUtils.isEmpty(edt7.getText())) {
            showErrorDialog("Please Input Price 20h -> 24h");
            return;
        }

        List<Double> listPrice = new ArrayList<>();
        listPrice.add(Double.parseDouble(edt1.getText().toString()));
        listPrice.add(Double.parseDouble(edt2.getText().toString()));
        listPrice.add(Double.parseDouble(edt3.getText().toString()));
        listPrice.add(Double.parseDouble(edt4.getText().toString()));
        listPrice.add(Double.parseDouble(edt5.getText().toString()));
        listPrice.add(Double.parseDouble(edt6.getText().toString()));
        listPrice.add(Double.parseDouble(edt7.getText().toString()));

        AppStorage.setDefaultPrices(listPrice);
    }

    public interface OnSetUpPriceListener {

    }

    @Override
    public void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }
}