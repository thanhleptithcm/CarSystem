package com.matas.caroperatingsystem.helper;

import com.matas.caroperatingsystem.data.model.Route;

import java.util.List;

public interface DirectionHelperListener {
    void onDirectionHelperStart();

    void onDirectionHelperSuccess(List<Route> route);
}
