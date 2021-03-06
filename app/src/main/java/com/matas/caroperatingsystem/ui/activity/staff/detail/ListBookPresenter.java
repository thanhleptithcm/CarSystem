package com.matas.caroperatingsystem.ui.activity.staff.detail;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.Book;
import com.matas.caroperatingsystem.data.network.staff.StaffApi;
import com.matas.caroperatingsystem.data.network.staff.request.ConfirmRequest;
import com.matas.caroperatingsystem.data.network.staff.response.AllBookingResponse;
import com.matas.caroperatingsystem.data.network.staff.response.ConfirmResponse;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ListBookPresenter extends BasePresenter<ListBookContract.ListBookView> implements ListBookContract.ListBookPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final StaffApi mStaffApi;
    private List<Book> mListBooking;
    private String idLogin;

    @Inject
    public ListBookPresenter(CompositeDisposable compositeDisposable,
                             PreferencesHelper prefs,
                             StaffApi staffApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mStaffApi = staffApi;
        mListBooking = new ArrayList<>();
        idLogin = mPrefs.getUserLogin().getId();
    }

    public List<Book> getListBooking() {
        return mListBooking;
    }

    @Override
    public void getAllBooking() {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        mCompositeDisposable.add(mStaffApi.getAllBooking(apiHeaders)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AllBookingResponse>() {
                    @Override
                    public void accept(AllBookingResponse response) {
                        if (isViewAttached()) {
                            if (mListBooking != null) {
                                mListBooking.clear();
                            }
                            for (Book book : response.getBooks()) {
                                if (book.getDriverId().equalsIgnoreCase(idLogin) &&
                                        book.getStatus().equalsIgnoreCase("ACCEPT")) {
                                    mListBooking.add(book);
                                }
                            }
                            getMvpView().hideLoading();
                            getMvpView().getAllBookingSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            if (throwable instanceof HttpException) {
                                HttpException exception = (HttpException) throwable;
                                if (exception.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                                    getMvpView().showErrorDialog(R.string.login_failed);
                                }
                            } else if (throwable instanceof UnknownHostException) {
                                getMvpView().showErrorDialog(R.string.connection_error);
                            } else {
                                getMvpView().showErrorDialog(throwable.getMessage());
                            }
                        }
                    }
                }));
    }
}