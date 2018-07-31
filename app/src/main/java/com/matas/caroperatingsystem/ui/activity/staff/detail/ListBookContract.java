package com.matas.caroperatingsystem.ui.activity.staff.detail;

import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.ConfirmBooking;
import com.matas.caroperatingsystem.data.model.User;

public interface ListBookContract {

    interface ListBookView extends MvpView {
        void getAllBookingSuccess();

        void confirmBookingSuccess(ConfirmBooking confirmBooking);
    }

    interface ListBookPresenter {
        void getAllBooking();

        void confirmBooking(String bookId);
    }
}
