package com.matas.caroperatingsystem.ui.fragment.setup_price;

import com.matas.caroperatingsystem.base.MvpView;
import com.matas.caroperatingsystem.data.model.Price;

import java.util.List;

public interface SetupPriceContract {

    interface SetupPriceView extends MvpView {
        void getAllPriceByTimeSuccess();

        void updatePriceByTimeSuccess();
    }

    interface SetupPricePresenter {
        void getAllPriceByTime();

        void updatePriceByTime();
    }
}
