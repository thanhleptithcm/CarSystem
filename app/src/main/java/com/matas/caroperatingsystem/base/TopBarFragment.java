package com.matas.caroperatingsystem.base;

import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

public abstract class TopBarFragment extends BaseFragment {
    protected AppTopBar getTopBar() {
        if (getBaseActivity() instanceof TopBarActivity) {
            return ((TopBarActivity) getBaseActivity()).getTopBar();
        }
        return null;
    }

    protected abstract void setupTopBar();
}
