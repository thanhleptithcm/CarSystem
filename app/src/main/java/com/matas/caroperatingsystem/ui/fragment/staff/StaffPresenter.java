package com.matas.caroperatingsystem.ui.fragment.staff;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.request.SignUpRequest;
import com.matas.caroperatingsystem.data.network.serialize.authenticate.response.SignUpResponse;
import com.matas.caroperatingsystem.data.network.serialize.staff.StaffApi;
import com.matas.caroperatingsystem.data.network.serialize.staff.request.StatusRequest;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class StaffPresenter extends BasePresenter<StaffContract.StaffView> implements StaffContract.StaffPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final StaffApi mStaffApi;

    @Inject
    public StaffPresenter(CompositeDisposable compositeDisposable,
                          PreferencesHelper prefs,
                          StaffApi staffApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mStaffApi = staffApi;
    }
}