package com.matas.caroperatingsystem.ui.base;

import android.text.TextUtils;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Response;

public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;
    protected final PreferencesHelper mPrefs;

    public BasePresenter(PreferencesHelper prefs) {
        this.mPrefs = prefs;
    }

    @Override
    public void onViewAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onViewDetach() {
        mMvpView = null;
    }

    @Override
    public V getMvpView() {
        return mMvpView;
    }

    @Override
    public boolean isViewAttached() {
        return mMvpView != null;
    }

    @Override
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    @Override
    public void handleApiError(Response response) {
        if (response == null || response.errorBody() == null || TextUtils.isEmpty(response.message())) {
            getMvpView().showErrorDialog(R.string.api_default_error);
            return;
        }

        switch (response.code()) {
            case HttpsURLConnection.HTTP_UNAUTHORIZED:
            case HttpsURLConnection.HTTP_INTERNAL_ERROR:
            case HttpsURLConnection.HTTP_NOT_FOUND:
            default:
                getMvpView().showErrorDialog(response.message());
        }
    }

    @Override
    public void setUserAsLoggedOut() {
        mPrefs.setUserLogin(null);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onViewAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}