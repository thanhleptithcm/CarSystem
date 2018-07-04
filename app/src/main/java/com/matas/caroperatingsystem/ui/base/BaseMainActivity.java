package com.matas.caroperatingsystem.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseMainActivity extends TopBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (isBack()) {
            popFragment(null);
        }
    }
}
