package com.matas.caroperatingsystem.ui.activity.staff;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.activity.user.UserActivity;
import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import javax.inject.Inject;

public class StaffActivity extends TopBarActivity implements StaffContract.StaffView,
        OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location mLocation;

    private AppTopBar topBar;

    @Inject
    StaffPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StaffActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getCoordinateLayout() {
        return R.layout.activity_staff;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        topBar = findViewById(R.id.top_bar);
        topBar.initData(0, 0, R.string.staff, R.string.action_logout, 0);
        topBar.setVisible(View.GONE, View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.GONE);

        mPresenter.updateStatus(true);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected int getContainerId() {
        return R.id.fr_container;
    }


    @Override
    public AppTopBar getTopBar() {
        return topBar;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocationIfPermitted();

        initData();
        initListener();
    }

    private void initData() {

    }

    private void initListener() {
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (mLocation != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 15.0f));
                }
            }
        });

        topBar.setOnTopBarListener(new AppTopBar.OnTopBarListener() {
            @Override
            public void onImvLeftOneClick() {

            }

            @Override
            public void onTvLeftOneClick() {

            }

            @Override
            public void onTvRightOneClick() {
                showConfirmDialog(StaffActivity.this, null, getString(R.string.home_do_you_want_to_logout), new ConfirmDialog.OnConfirmDialogListener() {
                    @Override
                    public void onConfirmDialogPositiveClick(ConfirmDialog dialog) {
                        mPresenter.updateStatus(false);
                        dialog.dismiss();
                    }

                    @Override
                    public void onConfirmDialogNegativeClick(ConfirmDialog dialog) {
                        dialog.dismiss();
                    }
                }, null);
            }

            @Override
            public void onImvRightOneClick() {

            }
        });
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void updateStatusSucess(boolean status) {
        if(!status){
            mPresenter.setLogOut();
            AuthActivity.startActivity(StaffActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDetach();
        super.onDestroy();
    }
}