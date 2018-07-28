package com.matas.caroperatingsystem.ui.activity.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.data.model.Route;
import com.matas.caroperatingsystem.helper.DirectionHelper;
import com.matas.caroperatingsystem.helper.DirectionHelperListener;
import com.matas.caroperatingsystem.ui.activity.login.LoginActivity;
import com.matas.caroperatingsystem.ui.activity.manage.ManageActivity;
import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppEditText;
import com.matas.caroperatingsystem.widget.AppTextView;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserActivity extends TopBarActivity implements OnMapReadyCallback,
        UserContract.UserView,
        DirectionHelperListener,
        View.OnClickListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location mLocation;

    private AppEditText edtOrigin;
    private AppEditText edtDestination;
    private AppTextView tvDistance;
    private AppTextView tvClock;
    private AppButton btnBook;
    private AppTopBar topBar;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ManageActivity.class);
        context.startActivity(intent);
    }

    @Inject
    UserPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocationIfPermitted();

        initData();
        initListener();
    }

    private void initListener() {
        btnBook.setOnClickListener(this);
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
                showConfirmDialog(UserActivity.this, null, getString(R.string.home_do_you_want_to_logout), new ConfirmDialog.OnConfirmDialogListener() {
                    @Override
                    public void onConfirmDialogPositiveClick(ConfirmDialog dialog) {
                        mPresenter.setLogOut();
                        dialog.dismiss();
                        LoginActivity.startActivity(UserActivity.this);
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

    private void initData() {
        edtOrigin = findViewById(R.id.edt_your_location);
        edtDestination = findViewById(R.id.edt_destination);
        tvDistance = findViewById(R.id.tv_distance);
        tvClock = findViewById(R.id.tv_clock);
        btnBook = findViewById(R.id.btn_book);
        topBar = findViewById(R.id.top_bar);

        topBar.initData(0, 0, R.string.home, R.string.action_logout, 0);
        topBar.setVisible(View.GONE, View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.GONE);
    }

    ///////////////////////////////
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
    public int getCoordinateLayout() {
        return R.layout.activity_user;
    }

    @Override
    public AppTopBar getTopBar() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (v == btnBook) {
            sendRequest();
        }
    }

    private void sendRequest() {
        String origin = edtOrigin.getText().toString();
        String destination = edtDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (origin.equalsIgnoreCase("Your location")) {
                new DirectionHelper(this, mLocation.getLatitude(), mLocation.getLongitude(), destination).execute();
            } else {
                new DirectionHelper(this, origin, destination).execute();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionHelperStart() {
        showLoading();
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionHelperSuccess(List<Route> routes) {
        hideLoading();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.getStartLocation(), 16));

            tvClock.setText(route.getDuration().getText());
            tvDistance.setText(route.getDistance().getText());

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.getStartAddress())
                    .position(route.getStartLocation())));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.getEndAddress())
                    .position(route.getEndLocation())));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.getPoints().size(); i++)
                polylineOptions.add(route.getPoints().get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
