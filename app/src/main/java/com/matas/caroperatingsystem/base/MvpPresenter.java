package com.matas.caroperatingsystem.base;

import retrofit2.Response;

public interface MvpPresenter<V extends MvpView> {

    void onViewAttach(V mvpView);

    void onViewDetach();

    V getMvpView();

    boolean isViewAttached();

    void checkViewAttached() throws BasePresenter.MvpViewNotAttachedException;

    void handleApiError(Response response);

    void setUserAsLoggedOut();
}
