package com.matas.caroperatingsystem.ui.fragment.manage_staff_detail;

import com.matas.caroperatingsystem.base.BasePresenter;
import com.matas.caroperatingsystem.data.model.Book;
import com.matas.caroperatingsystem.data.network.authenticate.AuthenticateApi;
import com.matas.caroperatingsystem.data.prefs.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DetailManageStaffPresenter extends BasePresenter<DetailManageStaffContract.DetailManageStaffView>
        implements DetailManageStaffContract.DetailManageStaffPresenter {

    private final CompositeDisposable mCompositeDisposable;
    private final AuthenticateApi mAuthenticateApi;
    private List<Book> mListBook;
    @Inject
    public DetailManageStaffPresenter(CompositeDisposable compositeDisposable,
                                      PreferencesHelper prefs,
                                      AuthenticateApi authenticateApi) {
        super(prefs);
        this.mCompositeDisposable = compositeDisposable;
        this.mAuthenticateApi = authenticateApi;
        mListBook = new ArrayList<>();
    }

    public List<Book> getListBook() {
        return mListBook;
    }

    public void setListBook(List<Book> mListBook) {
        this.mListBook = mListBook;
    }
}