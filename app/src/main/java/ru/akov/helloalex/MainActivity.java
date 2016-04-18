package ru.akov.helloalex;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginError;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

;


import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends myFirebaseLoginBaseActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {


    private static final String FIREBASE_UR1L = "https://resplendent-inferno-864.firebaseio.com/";
    static My_app app;
    //Костылёк потом переделать !!!
    //  static Firebase firebaseRef;
    static String ipString = "0.0.0.0";
    private EditText inpuText;
    FirebaseListAdapter<ChatmessAlex> mListAdapter;


    static private ValueEventListener zamena_list_online1;
    static private GoogleApiClient mGoogleApiClient;
    static private  Location mLastLocation;
    static String mLatitudeText;
    static String mLongitudeText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if(!mGoogleApiClient.isConnected())
        mGoogleApiClient.connect();



  /*      WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ipString = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));*/

        setContentView(R.layout.activity_main);
        app = ((My_app) getApplicationContext());

        inpuText = (EditText) findViewById(R.id.messageText);


        inpuText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                }
                return true;
            }
        });


        final ListView lvMain = (ListView) this.findViewById(R.id.listViewAkov);

        mListAdapter = new FirebaseListAdapter<ChatmessAlex>(this, ChatmessAlex.class,
                android.R.layout.two_line_list_item, getFirebaseRef().child("Test123")) {
            @Override
            protected void populateView(View v, ChatmessAlex model, int position) {
                ((TextView) v.findViewById(android.R.id.text1)).setText(model.getName());
                ((TextView) v.findViewById(android.R.id.text2)).setText(model.getText());
            }
        };

        lvMain.setAdapter(mListAdapter);

        this.findViewById(R.id.listViewAkov).setFocusable(true);
        this.findViewById(R.id.listViewAkov).requestFocus();


        TextView edf = (TextView) findViewById(R.id.textView_my);
        edf.setText(Accont_info_my_sington.getInstance().getname());


    }


    public void sendMessage() {

        String message = inpuText.getText().toString();
        if (!message.equals("")) {
            Random rand = new Random();
            String author = Accont_info_my_sington.getInstance().getname();

            ChatmessAlex cMasg = new ChatmessAlex(author, message);

            getFirebaseRef().child("Test123").child("-KCVXxZze4WNA4gRPrWF").setValue(cMasg);


            inpuText.setText("");

        }

    }


    public void OnclickMy(View view) {
        sendMessage();

    }

    @Override
    protected Firebase getFirebaseRef() {


        return app.getFirebaseRef();
        //     firebaseRef;


    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        System.out.println("ЧТО ТО ТИПА ТОГО onFirebaseLoginProviderError!!!!!!!!!!");
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {

        System.out.println("ЧТО ТО ТИПА ТОГО onFirebaseLoginUserError!!!!!!!!!!");
        System.out.println(firebaseLoginError.toString());
        dismissFirebaseLoginPrompt();
        showToast();

    }

    public void showToast() {
        //создаем и отображаем текстовое уведомление
        Toast toast = Toast.makeText(getApplicationContext(),
                "Ошибка соедединения!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // All providers are optional! Remove any you don't want.
        //   if(getFirebaseRef().getAuth()==null)
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
        mGoogleApiClient.connect();
    }


    public void OnclickMy_test(View view) {

        //    Firebase.goOnline();

        if (getFirebaseRef().getAuth() != null) {

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Пользователь" + getFirebaseRef().getAuth().getUid() + "Разлонтесь!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            System.out.println("такой пользователь подключен " + getFirebaseRef().getAuth());
            showFirebaseLoginPrompt();
        }

        //   getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void button_my_new_acc(View view) {
        if (getFirebaseRef().getAuth() != null) {

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Пользователь" + getFirebaseRef().getAuth().getUid() + "Разлонтесь!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            newAcc(getFirebaseRef().getAuth());
        }

    }


    public void button_my(View view) {


        logout();

        getFirebaseRef().unauth();
        //   Firebase.goOffline();


        //   app.remove();
    }


    @Override
    protected void onStop() {

        mGoogleApiClient.disconnect();

        super.onStop();
        //    mListAdapter.cleanup();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  mListAdapter.cleanup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListAdapter.cleanup();
    }

    public void next_scr(View view) {
        Intent intent = new Intent(MainActivity.this, Spisok_online.class);

        startActivity(intent);
        //  firebaseRef.unauth();

    }


    @Override
    public void izmenit_label() {
        TextView edf = (TextView) findViewById(R.id.textView_my);
        edf.setText(Accont_info_my_sington.getInstance().getname());
    }

    @Override
    public void
    izmenit_singltone(String name) {
        Accont_info_my_sington.getInstance().setname(name);

    }


    public void OnclickMy_showall(View view) {


        if (getFirebaseRef().getAuth() != null) {


            Map<String, Object> boolmay = new HashMap<String, Object>();
            boolmay.put("showall", Boolean.TRUE);
            getFirebaseRef().child("users/").child(getAuth().getUid()).updateChildren(boolmay);
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {

        System.out.println("КООРДИНАТЫ_РАЗ_РАЗ!!!!!!!!!!");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
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
            mLatitudeText = "test";
            mLongitudeText = "test";
            mLatitudeText = (String.valueOf(mLastLocation.getLatitude()).toString());
            mLongitudeText = (String.valueOf(mLastLocation.getLongitude()).toString());
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void createLocationRequest() {

        System.out.println("createLocationRequest АУАУАУАУ");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
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
                        // All location settings are satisfied. The client can
                        // initialize location requests here.

                        break;}
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {System.out.println("RESOLUTION_REQUIRED");
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, 0x1);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
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

    }
    public void OnclickMy_setGPS(View view) {
        createLocationRequest();
    }
}
