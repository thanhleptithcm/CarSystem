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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
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
import com.matas.caroperatingsystem.BuildConfig;
import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.data.model.Driver;
import com.matas.caroperatingsystem.data.model.PosLocation;
import com.matas.caroperatingsystem.data.model.Route;
import com.matas.caroperatingsystem.data.network.user.response.BookingResponse;
import com.matas.caroperatingsystem.helper.DirectionHelper;
import com.matas.caroperatingsystem.helper.DirectionHelperListener;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.ui.dialog.OkDialog;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppEditText;
import com.matas.caroperatingsystem.widget.AppTextView;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserActivity extends TopBarActivity implements OnMapReadyCallback,
        UserContract.UserView,
        AppEditText.OnFocusChangeListener,
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

    private LatLng originLatLng, destinationLatLng;
    private double distance;
    private boolean isConnected = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }

    @Inject
    UserPresenter mPresenter;

    private Socket mSocket;
    private OkDialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onViewAttach(this);

        IO.Options opts = new IO.Options();
        opts.query = "token=" + mPresenter.getToken();
        try {
            mSocket = IO.socket(BuildConfig.HOME_URL, opts);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("acceptBooking", onAcceptBooking);
        mSocket.connect();

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

    private void initListener() {
        btnBook.setOnClickListener(this);
        edtOrigin.setOnClickListener(this);
        edtDestination.setOnClickListener(this);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (mLocation != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 15.0f));
                    mPresenter.getListDriver(mLocation.getLatitude(), mLocation.getLongitude());
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
                        AuthActivity.startActivity(UserActivity.this);
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

        edtOrigin.setOnFocusChangeListener(this);
        edtDestination.setOnFocusChangeListener(this);
    }


    private Emitter.Listener onAcceptBooking = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            showOKDialog(UserActivity.this, "The driver is picking you. Please waiting... ",
                    new OkDialog.IOkDialogListener() {
                        @Override
                        public void onIOkDialogAnswerOk(OkDialog dialog) {
                            btnBook.setText("Cancel");
                            edtOrigin.setEnabled(false);
                            edtDestination.setEnabled(false);
                            dialog.dismiss();
                        }
                    }, null);
        }
    };


    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isConnected) {
                        Toast.makeText(getApplicationContext(),
                                R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "diconnected");
                    isConnected = false;
                    Toast.makeText(getApplicationContext(),
                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Error connecting");
                    Toast.makeText(getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };


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
        return topBar;
    }

    @Override
    public void onClick(View v) {
        if (v == btnBook) {
            if (btnBook.getText().toString().equalsIgnoreCase(getString(R.string.maps_search))) {
                searchSpacing();
            } else if (btnBook.getText().toString().equalsIgnoreCase(getString(R.string.maps_book))) {
                if (originLatLng != null && destinationLatLng != null) {
                    mPresenter.bookingDrivers(distance, originLatLng, destinationLatLng);
                }
            } else {
                mMap.clear();
                mPresenter.cancelBooking();
            }
        }
    }

    @Override
    public void cancelBookingSuccess() {
        btnBook.setText(getString(R.string.maps_search));
        edtOrigin.setEnabled(true);
        edtDestination.setEnabled(true);

        mPresenter.getListDriver(mLocation.getLatitude(), mLocation.getLongitude());
    }

    private void searchSpacing() {
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
            originLatLng = route.getStartLocation();
            destinationLatLng = route.getEndLocation();
            distance = Double.parseDouble(route.getDistance().getText().split(" ")[0]);

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

        btnBook.setText(getString(R.string.maps_book));
    }

    @Override
    public void getListDriverNearSuccess() {
        PosLocation location;
        LatLng latLng;
        MarkerOptions markerOption;
        for (Driver driver : mPresenter.getListDriverNear()) {
            location = driver.getLocation();
            latLng = new LatLng(location.getCoordinates().get(1), location.getCoordinates().get(0));
            markerOption = new MarkerOptions().position(latLng);
            markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_moto));
            mMap.addMarker(markerOption);
        }
    }

    @Override
    public void bookingDriverSuccess(BookingResponse response) {
        mDialog = new OkDialog(this, null, "Please waiting! \n We finding driver for you"
                , new OkDialog.IOkDialogListener() {
            @Override
            public void onIOkDialogAnswerOk(OkDialog dialog) {
                dialog.dismiss();
            }
        });
        mDialog.show();
    }

    @Override
    public void onFocus(AppEditText editText, boolean hasFocus) {
        if (hasFocus) {
            btnBook.setText(getString(R.string.maps_search));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDetach();
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("acceptBooking", onAcceptBooking);
    }
}
