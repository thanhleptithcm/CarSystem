package com.matas.caroperatingsystem.ui.activity.maps;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.ui.base.BaseMainActivity;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

public class MapsActivity extends BaseMainActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.activity_maps;
    }

    @Override
    public AppTopBar getTopBar() {
        return null;
    }
}
