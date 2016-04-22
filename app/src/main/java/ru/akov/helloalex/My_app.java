package ru.akov.helloalex;

import android.Manifest;
import android.app.Application;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created by User on 21.03.2016.
 */
public class My_app extends Application implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult> {
    private static final String FIREBASE_UR1L = "https://resplendent-inferno-864.firebaseio.com/";
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mLastLocation;
    protected Location mCurrentLocation;
    protected Spisok_online test;
    protected LocationSettingsRequest mLocationSettingsRequest;

    String mLatitudeText = "test";
    String mLongitudeText = "test";
    private Firebase firebaseRef;



    MyCallback myCallback;

    void registerCallBack(MyCallback callback){
        this.myCallback = callback;
    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    protected void startLocationUpdates() {
        System.out.println("НОВЫЕ КООРДИНВТЫ!Ё!!!!!!!!!!");
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Недостаточно прав у приложения",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            myCallback.badpremission();
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        System.out.println("НОВЫЕ СЕРВИС ДЛЯ НВОЫХ  КООРДИНВТЫ!Ё!!!!!!!!!!");
    }
    @Override
    public void onCreate() {
       super.onCreate();

        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();



        Firebase.setAndroidContext(this);
        if(firebaseRef==null){
System.out.println("Сделал firebaseRef");

            firebaseRef = new Firebase(FIREBASE_UR1L);

        }
        Accont_info_my_sington.initInstance();

    }

    protected Firebase getFirebaseRef() {
        return

                        firebaseRef;


    }
    protected void remove() {


                Firebase.goOffline();


    }
    protected void renew() {


        firebaseRef = new Firebase(FIREBASE_UR1L);
        Firebase.setAndroidContext(this);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        System.out.println("КООРДИНАТЫ_РАЗ_РАЗ!!!!!!!!!!");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling


                Toast toast = Toast.makeText(getApplicationContext(),
                    "Недостаточно прав у приложения",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            myCallback.badpremission();

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("ВЫШЕЛ!!!!!!!!!!!");
            return;
        }
        System.out.println("ВСё Ок!!!!!!!!!!!");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            System.out.println("КООРДИНАТЫ!!!!!!!!!!");
            System.out.println(mLastLocation.getLatitude());
            System.out.println(mLastLocation.getLongitude());
            myCallback.lastlocation();

    }
        checkLocationSettings();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location) {

        System.out.println("НОВЫЕ КООРДИНАТЫ!!!!!!!!!!");
        mCurrentLocation = location;

        if (mCurrentLocation != null) {

            mLatitudeText = (String.valueOf(mCurrentLocation.getLatitude()).toString());
            mLongitudeText = (String.valueOf(mCurrentLocation.getLongitude()).toString());

            myCallback.callBackReturn();

             Toast.makeText(this, location.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(10000);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(5000);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.setAlwaysShow(true);
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }
    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
    //    final Status status = result.getStatus();
        final Status status = locationSettingsResult.getStatus();
     //   final LocationSettingsStates df = result.getLocationSettingsStates();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
            {   System.out.println("SUCCESS");
                System.out.println("Настройки локайшен соответсвуют требованиям приложения ");
                // All location settings are satisfied. The client can
                // initialize location requests here.

                break;}
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: {
                System.out.println("НЕ ПАШЕТ!");
                myCallback.badpremissioninsettings_gps(status);
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.

            }
            break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
            {    System.out.println("SETTINGS_CHANGE_UNAVAILABLE");
                // Location settings are not satisfied. However, we have no way
                // to fix the settings so we won't show the dialog.

                break;}
        }
    }

    /*protected void createLocationRequest() {

        System.out.println("createLocationRequest АУАУАУАУ");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest( mLocationRequest);

        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates df = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                    {   System.out.println("SUCCESS");
                        System.out.println("Настройки локайшен соответсвуют требованиям приложения ");
                        // All location settings are satisfied. The client can
                        // initialize location requests here.

                        break;}
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: {
                        System.out.println("НЕ ПАШЕТ!");
                        myCallback.badpremissioninsettings_gps(status);
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.

                    }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    {    System.out.println("SETTINGS_CHANGE_UNAVAILABLE");
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.

                        break;}
                }

            }
        });

    }*/

}
