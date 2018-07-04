package com.matas.caroperatingsystem.data.caches;

import android.content.Context;

import com.matas.caroperatingsystem.di.ApplicationContext;

import java.util.ArrayList;

import javax.inject.Inject;

public class AppCachesHelper implements CachesHelper {
    private final Context mContext;

    @Inject
    public AppCachesHelper(@ApplicationContext Context context) {
        this.mContext = context;
    }

    @Override
    public ArrayList<String> getListCaches() {
        return ListCaches.readCaches(mContext);
    }

    @Override
    public void setListCaches(ArrayList<String> listCaches) {
        ListCaches.writeCaches(mContext, listCaches);
    }
}
