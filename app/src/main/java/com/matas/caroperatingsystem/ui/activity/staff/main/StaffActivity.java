package com.matas.caroperatingsystem.ui.activity.staff.main;

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
import com.google.gson.Gson;
import com.matas.caroperatingsystem.BuildConfig;
import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.base.TopBarActivity;
import com.matas.caroperatingsystem.data.model.ConfirmBooking;
import com.matas.caroperatingsystem.data.model.PosLocation;
import com.matas.caroperatingsystem.data.model.Route;
import com.matas.caroperatingsystem.helper.DirectionHelper;
import com.matas.caroperatingsystem.helper.DirectionHelperListener;
import com.matas.caroperatingsystem.ui.activity.auth.AuthActivity;
import com.matas.caroperatingsystem.ui.activity.staff.detail.ListBookActivity;
import com.matas.caroperatingsystem.ui.dialog.ConfirmDialog;
import com.matas.caroperatingsystem.widget.AppTextView;
import com.matas.caroperatingsystem.widget.topbar.AppTopBar;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StaffActivity extends TopBarActivity implements StaffContract.StaffView,
        DirectionHelperListener,
        OnMapReadyCallback {
    public static final int REQUEST_LIST_BOOKING_CODE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location mLocation;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    private AppTextView tvDistance;
    private AppTextView tvClock;
    private AppTopBar topBar;
    private Boolean isConnected = true;
    private Socket mSocket;

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

        tvDistance = findViewById(R.id.tv_distance);
        tvClock = findViewById(R.id.tv_clock);
        topBar = findViewById(R.id.top_bar);
        topBar.initData(0, R.string.action_logout, R.string.staff, 0, R.drawable.ic_list_book);
        topBar.setVisible(View.GONE, View.VISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);

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
        mSocket.on("booking", onNewBooking);
        mSocket.connect();

        mPresenter.updateStatus(true);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

                    mPresenter.updateLocation(mLocation.getLatitude(), mLocation.getLongitude(), "12345");
                }
            }
        });

        topBar.setOnTopBarListener(new AppTopBar.OnTopBarListener() {
            @Override
            public void onImvLeftOneClick() {

            }

            @Override
            public void onTvLeftOneClick() {
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
            public void onTvRightOneClick() {

            }

            @Override
            public void onImvRightOneClick() {
                Intent intent = new Intent(StaffActivity.this, ListBookActivity.class);
                startActivityForResult(intent, REQUEST_LIST_BOOKING_CODE);
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
    public void updateStatusSuccess(boolean status) {
        if (!status) {
            mPresenter.setLogOut();
            AuthActivity.startActivity(StaffActivity.this);
        }
    }

    @Override
    public void updateLocationSuccess() {
        showToast("Update Location Success");
    }

    private Emitter.Listener onNewBooking = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d("TAG", "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String username;
////                    String message;
//                    try {
//                        username = data.getString("message");
////                        message = data.getString("message");
//                    } catch (JSONException e) {
//                        return;
//                    }
//                }
//            });
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isConnected) {
                        Log.d(TAG, "connected");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ConfirmBooking confirmBooking = new Gson().fromJson(data.getStringExtra("BOOKING"), ConfirmBooking.class);
            PosLocation location = confirmBooking.getDriverLocation().getLocation();
            try {
                new DirectionHelper(this, mLocation.getLatitude(), mLocation.getLongitude(),
                        location.getCoordinates().get(1), location.getCoordinates().get(0)).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDetach();
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("booking", onNewBooking);
        super.onDestroy();
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