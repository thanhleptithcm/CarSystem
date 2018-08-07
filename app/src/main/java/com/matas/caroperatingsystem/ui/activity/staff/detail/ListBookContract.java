package com.matas.caroperatingsystem.ui.activity.staff.detail;

import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.ConfirmBooking;
import com.matas.caroperatingsystem.data.model.User;

public interface ListBookContract {

    interface ListBookView extends MvpView {
        void getAllBookingSuccess();
    }

    interface ListBookPresenter {
        void getAllBooking();
    }
}
