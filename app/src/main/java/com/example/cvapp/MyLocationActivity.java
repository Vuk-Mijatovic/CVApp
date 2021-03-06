package com.example.cvapp;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.cvapp.databinding.ActivityMyLocationBinding;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final long LOCATION_UPDATE_INTERVAL = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 111;
    private GoogleMap googleMap;
    LocationManager locationManager;
    private ActivityMyLocationBinding binding;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        checkLocationServices();


    }

    private void findMe() {

        if (ActivityCompat.checkSelfPermission(MyLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyLocationActivity.this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            if (locationListener == null) {
                setLocationListener();
            }
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
                } else {
                    locationManager.getCurrentLocation(LocationManager.GPS_PROVIDER, null, MyLocationActivity.this.getMainExecutor(), new Consumer<Location>() {
                        @Override
                        public void accept(Location location) {
                            setPinOnMap(location);
                        }
                    });
                }
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
            } else {
                locationManager.getCurrentLocation(LocationManager.NETWORK_PROVIDER, null, MyLocationActivity.this.getMainExecutor(), new Consumer<Location>() {
                    @Override
                    public void accept(Location location) {
                        setPinOnMap(location);
                    }
                });
            }
        }

    }


    //I realized that requestSingleUpdate is better option for this use case
//    private void findMe() {
//        if (ActivityCompat.checkSelfPermission(MyLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MyLocationActivity.this, new String[]
//                            {Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_LOCATION_PERMISSION);
//        } else {
//            if (locationListener == null) {
//                setLocationListener();
//            }
//            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//            } else {
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (locationListener == null) {
                setLocationListener();
            }
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
                } else {
                    locationManager.getCurrentLocation(LocationManager.GPS_PROVIDER, null, MyLocationActivity.this.getMainExecutor(), new Consumer<Location>() {
                        @Override
                        public void accept(Location location) {
                            setPinOnMap(location);
                        }
                    });
                }
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
            } else {
                locationManager.getCurrentLocation(LocationManager.NETWORK_PROVIDER, null, MyLocationActivity.this.getMainExecutor(), new Consumer<Location>() {
                    @Override
                    public void accept(Location location) {
                        setPinOnMap(location);
                    }
                });
            }
        }
    }

    private void setLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                setPinOnMap(location);
            }
            //Not needed for singleUpdate
//            @Override
//            public void onProviderDisabled(@NonNull String provider) {
//                checkLocationServices();
//            }
//
//            @Override
//            public void onProviderEnabled(@NonNull String provider) {
//                checkLocationServices();
//            }
        };
    }


    private void setPinOnMap(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            String adress = addressList.get(0).getAddressLine(0) + " ";
            if (marker != null) {
                marker.remove();
            }
            marker = googleMap.addMarker(new MarkerOptions().position(latLng).title(adress));
            googleMap.setMaxZoomPreference(20);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationServices() {

        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(MyLocationActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        findMe();
                    }
                })
                .addOnFailureListener(MyLocationActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        if (e instanceof ResolvableApiException) {
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(MyLocationActivity.this,
                                        REQUEST_CHECK_SETTINGS);

                            } catch (IntentSender.SendIntentException sendEx) {
                                // Ignore the error.
                            }
                        }

                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == -1) {
            checkLocationServices();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
}
