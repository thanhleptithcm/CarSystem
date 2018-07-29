package com.matas.caroperatingsystem.ui.fragment.manage_staff;

import com.matas.caroperatingsystem.base.MvpView;

public interface ManageStaffContract {

    interface ManageStaffView extends MvpView {
        void getListStaffSuccess();
    }

    interface ManageStaffPresenter {
        void fetchListStaff();
    }
}
