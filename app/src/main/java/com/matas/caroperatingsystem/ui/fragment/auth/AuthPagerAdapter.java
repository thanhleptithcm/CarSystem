package com.matas.caroperatingsystem.ui.fragment.auth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matas.caroperatingsystem.base.BaseFragment;

import java.util.List;

public class AuthPagerAdapter  extends FragmentPagerAdapter {
    private List<BaseFragment> mAuthFragments;

    public AuthPagerAdapter(FragmentManager fm, List<BaseFragment> authFragments) {
        super(fm);
        this.mAuthFragments = authFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mAuthFragments.get(position);
    }

    @Override
    public int getCount() {
        return mAuthFragments.size();
    }
}