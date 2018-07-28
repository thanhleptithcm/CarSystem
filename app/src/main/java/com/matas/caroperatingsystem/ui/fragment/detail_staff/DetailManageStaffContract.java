package com.matas.caroperatingsystem.ui.fragment.detail_staff;

import com.matas.caroperatingsystem.base.MvpView;

public interface DetailManageStaffContract {

    interface DetailManageStaffView extends MvpView {
        void fetchHistoryStaffSuccess();
    }

    interface DetailManageStaffPresenter {
        void fetchHistoryStaff();
    }
}
