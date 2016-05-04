package ru.akov.helloalex;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;


import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 18.03.2016.
 */
public class Spisok_online extends AppCompatActivity implements  MyCallback,  Labal_change_my {

    protected static final int REQUEST_CHECK_SETTINGS = 5;
     My_app app;
    FirebaseListAdapter<Users_online> Listonline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spisok_online);
       app = ((My_app) getApplicationContext());
        app.registerCallBack(this);

        final ListView lvMain1 = (ListView) this.findViewById(R.id.listView_online);
        Listonline = new FirebaseListAdapter<Users_online>(this, Users_online.class,
          //      android.R.layout.simple_list_item_1, getFirebaseRef().child("Test123")) {
               android.R.layout.test_list_item, getFirebaseRef().child("onlineusers"))


        {
            @Override
            protected void populateView(View v, Users_online model, int position) {

                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getUid());
         //       ((TextView)v.findViewById(android.R.id.text2)).setText(model.getPhonemodel1());
            }
        };

        lvMain1.setAdapter(Listonline);

        TextView edf = (TextView) findViewById(R.id.textView_my_online);
        edf.setText(Accont_info_my_sington.getInstance().getname());


        lvMain1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();

                System.out.println("Я ВЫБРАЛ ДРУГА!!!!!!!!!!" + strText);
                Map<String, String> map = new HashMap<String, String>();
                map.put("name_of_friend", strText);

                getFirebaseRef().child("users").child(getFirebaseRef().getAuth().getUid()).child("friends").push().setValue(map.get("name_of_friend"));


                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();

            }
        });




    }

   // public void back(View view) {
  //  Intent intent = new Intent(Spisok_online.this, MainActivity.class);
//    startActivity(intent);}

    protected Firebase getFirebaseRef() {

        return                app.getFirebaseRef();
      //  return  MainActivity.firebaseRef;
    }




    @Override
    public void izmenit_label() {
        TextView edf = (TextView) findViewById(R.id.textView_my_online);
        edf.setText(Accont_info_my_sington.getInstance().getname());
    }
    @Override
    public void
    izmenit_singltone(String name){
        Accont_info_my_sington.getInstance().setname(name);

    }


    @Override
    protected void onStart() {
        super.onStart();

 app.mGoogleApiClient.connect();

        //createLocationRequest();


    }


    @Override
    protected void onStop() {
        super.onStop();
        //    mListAdapter.cleanup();
        if(app.mGoogleApiClient.isConnected()){
        app.stopLocationUpdates();
        app.mGoogleApiClient.disconnect();}
    }
    @Override
    protected void onPause() {
        super.onPause();
        //  mListAdapter.cleanup();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Listonline.cleanup();
    }

    public void OnclickMy_back(View view) {
        this.finish();
        Intent intent = new Intent(Spisok_online.this, MainActivity.class);

        startActivity(intent);

    }

    public void next_scr_spisok_friends(View view) {
        Intent intent = new Intent(Spisok_online.this, Spisok_friends.class);

        startActivity(intent);
    }



public void lastcoord(){
    String mLatitudeText = "test";
    String mLongitudeText = "test";
    if(app.mLastLocation!=null){
        mLatitudeText = (String.valueOf(app.mLastLocation.getLatitude()).toString());
        mLongitudeText = (String.valueOf(app.mLastLocation.getLongitude()).toString());

        TextView edf1 = (TextView) findViewById(R.id.textView_my_Latitude);

        edf1.setText(mLatitudeText);

        TextView edf2 = (TextView) findViewById(R.id.textView_my_Longitude);
        edf2.setText(mLongitudeText);}
}

    public void GPS_coord(View view) {


    }




    public void OnclickMy_setGPS(View view) {
       // app.startLocationUpdates();

       // createLocationRequest();
    }


    @Override
    public void callBackReturn() {
        System.out.println("КАЛЛ БЭК");
        String mLatitudeText = "test";
        String mLongitudeText = "test";
        if(app.mCurrentLocation!=null){
            mLatitudeText = (String.valueOf(app.mCurrentLocation.getLatitude()).toString());
            mLongitudeText = (String.valueOf(app.mCurrentLocation.getLongitude()).toString());

            TextView edf1 = (TextView) findViewById(R.id.textView_my_Latitude);

            edf1.setText(mLatitudeText);

            TextView edf2 = (TextView) findViewById(R.id.textView_my_Longitude);
            edf2.setText(mLongitudeText);

            Accont_info_my_sington.getInstance().setGPS(String.valueOf(app.mCurrentLocation.getLatitude()).toString(), String.valueOf(app.mCurrentLocation.getLongitude()).toString());
        System.out.print("ЗАписал координыты"+Accont_info_my_sington.getInstance().getGPSLatitude());
        }



    }

    @Override
    public void lastlocation() {
        lastcoord();
    }

    @Override
    public void badpremission() {

        ActivityCompat.requestPermissions(
                Spisok_online.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},14);
    }

    @Override
    public void badpremissioninsettings_gps(Status status) {

        try {
            System.out.println("RESOLUTION_REQUIRED");
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            status.startResolutionForResult(Spisok_online.this, REQUEST_CHECK_SETTINGS);
        } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
        }

       }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


      //  super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        System.out.println(resultCode);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                System.out.println("Я ТУТ ГОТОВ !!!");
                switch (resultCode) {
                    case Activity.RESULT_OK:{
                     System.out.println("РАЗРЕШИЛИ");}
                        break;
                    case Activity.RESULT_CANCELED:{
                        System.out.println("ЗАКРЫЛ !!!НЕ РАЗРЕШИЛИ");
                     //   finish();
                    }
                        break;
                }
                break;
        }
    }
}
