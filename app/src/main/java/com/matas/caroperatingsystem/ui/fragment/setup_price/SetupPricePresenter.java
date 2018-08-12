package com.matas.caroperatingsystem.ui.fragment.setup_price;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.Price;
import com.matas.caroperatingsystem.data.network.BaseResponse;
import com.matas.caroperatingsystem.data.network.manage.ManageApi;
import com.matas.caroperatingsystem.data.network.manage.request.StaffStatusRequest;
import com.matas.caroperatingsystem.data.network.manage.request.UpdatePriceRequest;
import com.matas.caroperatingsystem.data.network.manage.response.GetPriceResponse;
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


public class SetupPricePresenter extends BasePresenter<SetupPriceContract.SetupPriceView>
        implements SetupPriceContract.SetupPricePresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final ManageApi mManageApi;
    private List<Price> mList;

    @Inject
    public SetupPricePresenter(CompositeDisposable compositeDisposable,
                               PreferencesHelper prefs,
                               ManageApi manageApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mManageApi = manageApi;
        mList = new ArrayList<>();
    }

    public List<Price> getListPrice() {
        return mList;
    }

    @Override
    public void getAllPriceByTime() {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        mCompositeDisposable.add(mManageApi.getAllPriceByTime(apiHeaders)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetPriceResponse>() {
                    @Override
                    public void accept(GetPriceResponse response) {
                        if (isViewAttached()) {
                            if (mList != null) {
                                mList.clear();
                            }
                            mList.addAll(response.getDataPrice().getPriceByTime());
                            getMvpView().hideLoading();
                            getMvpView().getAllPriceByTimeSuccess();
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

    @Override
    public void updatePriceByTime() {
        Map<String, String> apiHeaders = new HashMap<>();
        apiHeaders.put("Content-Type", "application/json");
        apiHeaders.put("access-token", mPrefs.getToken());

        getMvpView().showLoading();
        UpdatePriceRequest request = new UpdatePriceRequest();
        request.byTime.addAll(mList);

        mCompositeDisposable.add(mManageApi.updatePriceByTime(apiHeaders, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) {
                        if (isViewAttached()) {
                            getMvpView().hideLoading();
                            getMvpView().updatePriceByTimeSuccess();
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