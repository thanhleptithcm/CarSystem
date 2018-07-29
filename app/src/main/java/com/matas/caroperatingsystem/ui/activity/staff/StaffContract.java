package com.matas.caroperatingsystem.ui.activity.staff;

import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.User;

public interface StaffContract {

    interface StaffView extends MvpView {
        void updateStatusSucess(boolean status);
    }

    interface StaffPresenter {
        User getUser();

        void updateStatus(boolean status);
    }
}
