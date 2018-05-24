package com.example.gabriel.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
//import android.location.Location;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.example.gabriel.myapplication.modelo.Locais;
import com.example.gabriel.myapplication.modelo.localizacao;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private static final String TAG = "myapplication";
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private static List list = new ArrayList();
    //Object mGeoDataClient;

    // Construct a PlaceDetectionClient.
    //PlaceDetectionClient mPlaceDetectionClient;

    // Construct a FusedLocationProviderClient.
    FusedLocationProviderClient mFusedLocationProviderClient ;
    //FusedLocationProviderClient mFusedLocationProviderClient = new FusedLocationProviderClient();
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;
    public static int a = 0;
    //public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;
    private double lat=0;
    private double lon=0;
    localizacao loc = new localizacao();
    String json="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


//        getDeviceLocation();

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            //Log.i(TAG,String.valueOf(a));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        Log.i(TAG, String.valueOf(MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION));
//        switch (requestCode) {
//
//            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            //}
       // }
        updateLocationUI();
    }
 Location mLastKnownLocation;
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        try {
            if (mLocationPermissionGranted == true) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                getDeviceLocation();
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        Log.i(TAG,"teste2");

        try {
            //Log.i(TAG,mLocationPermissionGranted);
            //if (mLocationPermissionGranted) {
                //mMap.setMyLocationEnabled(true);
                //mMap.getUiSettings().setMyLocationButtonEnabled(true);
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                Log.i(TAG,"teste3");
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            Log.i(TAG,"teste");
                            lat = mLastKnownLocation.getLatitude();
                            lon = mLastKnownLocation.getLongitude();
                            Log.i(TAG, String.valueOf(lat));
                            Log.i(TAG, String.valueOf(lon));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), 15));
                            Log.i(TAG,"local");
                            Log.i(TAG, String.valueOf(lat));
                            Log.i(TAG, String.valueOf(lon));
                            loc.setLatitude(lat);
                            loc.setLongitude(lon);
                            loc.setRaio(100000);
                            Rest rest = new Rest(loc);
                            AsyncTask<String, String, String> a = rest.execute();
                            try {
                                json = a.get();
                                Log.i(TAG, json);
                                Gson g = new Gson();
                                //list = g.fromJson(json,ArrayList.class);
                                list = stringToArray(json, Locais[].class);
                                Log.i(TAG, String.valueOf(list.size()));
                                //Locais locais = list.get(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            if (list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    double lat = 0;
                                    double lon = 0;
                                    Locais locais = (Locais) list.get(i);
                                    Log.i(TAG, locais.getNome());
                                    lat = locais.getLatitude();
                                    lon = locais.getLongitude();
                                    Log.i(TAG, String.valueOf(lat));
                                    Log.i(TAG, String.valueOf(lon));
                                    LatLng l = new LatLng(lat, lon);
                                    Log.i(TAG, "lat : " + String.valueOf(l.latitude));
                                    Log.i(TAG, "lon : " + String.valueOf(l.longitude));
                                    mMap.addMarker(new MarkerOptions().position(l).title(locais.getNome()));
                                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
                                }
                                //LatLng sydney = new LatLng(-23.680556, -46.620556);
                                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            //Log.e(TAG, "Exception: %s", task.getException());
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                        //PegarDados();
                    }
                });

        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    public void PegarDados(){
        Rest rest = new Rest(new localizacao());
        AsyncTask<String, String, String> a =  rest.execute();
        try {
            String json = a.get();
            Log.i(TAG,json);
            Gson g = new Gson();
            //list = g.fromJson(json,ArrayList.class);
            list =  stringToArray(json,Locais[].class);
            Log.i(TAG, String.valueOf(list.size()));
            //Locais locais = list.get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
    @Override
        public void onMapReady (GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, ListActivity.class);
                Bundle bundle = new Bundle();

                //bundle.putParcelableArrayList("t1", (ArrayList<? extends Parcelable>) list);
                bundle.putString("json",json);
                intent.putExtras(bundle);
                startActivity(intent);
                Log.i(TAG,"teste");
            }
        });

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }
    }
