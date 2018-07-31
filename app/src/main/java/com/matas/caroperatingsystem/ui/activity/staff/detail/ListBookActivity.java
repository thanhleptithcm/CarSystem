package com.matas.caroperatingsystem.ui.activity.staff.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.data.model.ConfirmBooking;
import com.matas.caroperatingsystem.ui.activity.staff.main.StaffActivity;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class ListBookActivity extends TopBarActivity implements ListBookContract.ListBookView, ListBookAdapter.OnItemClickListener {
    private AppTopBar topBar;
    private RecyclerView rcvAllBooking;
    private ListBookAdapter mAdapter;

    @Inject
    ListBookPresenter mPresenter;

    @Override
    public int getCoordinateLayout() {
        return R.layout.activity_list_book;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        initData();
        initListener();
    }


    private void initData() {
        rcvAllBooking = findViewById(R.id.rcv_all_booking);
        topBar = findViewById(R.id.top_bar);
        topBar.initData(0, R.string.action_back, R.string.all_booking, 0, 0);
        topBar.setVisible(View.GONE, View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.GONE);

        mAdapter = new ListBookAdapter(this, mPresenter.getListBooking(), this);
        rcvAllBooking.setLayoutManager(new LinearLayoutManager(this));
        rcvAllBooking.setAdapter(mAdapter);

        mPresenter.getAllBooking();
    }

    private void initListener() {
        topBar.setOnTopBarListener(new AppTopBar.OnTopBarListener() {
            @Override
            public void onImvLeftOneClick() {

            }

            @Override
            public void onTvLeftOneClick() {
                onBackPressed();
            }

            @Override
            public void onTvRightOneClick() {

            }

            @Override
            public void onImvRightOneClick() {

            }
        });


    }

    @Override
    protected int getContainerId() {
        return R.id.fr_container;
    }

    @Override
    public AppTopBar getTopBar() {
        return topBar;
    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }

    @Override
    public void getAllBookingSuccess() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void confirmBookingSuccess(ConfirmBooking confirmBooking) {
        Intent intent = new Intent();
        intent.putExtra("BOOKING", new Gson().toJson(confirmBooking));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onAccept(int position) {
        mPresenter.confirmBooking(mPresenter.getListBooking().get(position).getId());
    }
}