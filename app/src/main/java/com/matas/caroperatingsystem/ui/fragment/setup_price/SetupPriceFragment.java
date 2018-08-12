package com.matas.caroperatingsystem.ui.fragment.setup_price;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarFragment;
import com.matas.caroperatingsystem.data.model.Price;
import com.matas.caroperatingsystem.utils.KeyboardUtils;
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

    public static SetupPriceFragment newInstance() {
        Bundle args = new Bundle();
        SetupPriceFragment fragment = new SetupPriceFragment();
        fragment.setArguments(args);
        return fragment;
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
        mPresenter.getAllPriceByTime();
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
                    KeyboardUtils.hideSoftInput(getActivity());
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
        double str0to6 = Double.parseDouble(edt1.getText().toString());
        double str6to9 = Double.parseDouble(edt2.getText().toString());
        double str9to12 = Double.parseDouble(edt3.getText().toString());
        double str12to13 = Double.parseDouble(edt4.getText().toString());
        double str13to17 = Double.parseDouble(edt5.getText().toString());
        double str17to20 = Double.parseDouble(edt6.getText().toString());
        double str20to24 = Double.parseDouble(edt7.getText().toString());
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

        List<Price> list = mPresenter.getListPrice();
        list.get(0).setPrice(str0to6);
        list.get(1).setPrice(str0to6);
        list.get(2).setPrice(str0to6);
        list.get(3).setPrice(str0to6);
        list.get(4).setPrice(str0to6);
        list.get(5).setPrice(str0to6);
        list.get(6).setPrice(str6to9);
        list.get(7).setPrice(str6to9);
        list.get(8).setPrice(str6to9);
        list.get(9).setPrice(str9to12);
        list.get(10).setPrice(str9to12);
        list.get(11).setPrice(str9to12);
        list.get(12).setPrice(str12to13);
        list.get(13).setPrice(str13to17);
        list.get(14).setPrice(str13to17);
        list.get(15).setPrice(str13to17);
        list.get(16).setPrice(str13to17);
        list.get(17).setPrice(str17to20);
        list.get(18).setPrice(str17to20);
        list.get(19).setPrice(str17to20);
        list.get(20).setPrice(str20to24);
        list.get(21).setPrice(str20to24);
        list.get(22).setPrice(str20to24);
        list.get(23).setPrice(str20to24);
        mPresenter.updatePriceByTime();
    }

    @Override
    public void getAllPriceByTimeSuccess() {
        edt1.setText(String.valueOf(mPresenter.getListPrice().get(0).getPrice()));
        edt2.setText(String.valueOf(mPresenter.getListPrice().get(6).getPrice()));
        edt3.setText(String.valueOf(mPresenter.getListPrice().get(9).getPrice()));
        edt4.setText(String.valueOf(mPresenter.getListPrice().get(12).getPrice()));
        edt5.setText(String.valueOf(mPresenter.getListPrice().get(13).getPrice()));
        edt6.setText(String.valueOf(mPresenter.getListPrice().get(17).getPrice()));
        edt7.setText(String.valueOf(mPresenter.getListPrice().get(20).getPrice()));
    }

    @Override
    public void updatePriceByTimeSuccess() {
        KeyboardUtils.hideSoftInput(getActivity());
        getBaseActivity().onBackPressed();
    }

    @Override
    public void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }
}