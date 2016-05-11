package ru.akov.helloalex;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 18.03.2016.
 */
public class Spisok_online  extends FirebaseLoginBaseActivity implements  MyCallback,  Labal_change_my {
    private ChildEventListener namechange1;
    protected static final int REQUEST_CHECK_SETTINGS = 5;
     My_app app;
    FirebaseListAdapter<Nearest_dudes> Listonline;
    protected void onFirebaseLoggedIn(AuthData authData) {
        System.out.println("В НОВОМ ОКНЕ ПОДКОНЕКТИЛСЯ И ПОДКЛЮЧАЮ ЛИСТНЕР!!!");
        request_watch();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spisok_online);
       app = ((My_app) getApplicationContext());
        app.registerCallBack(this);
System.out.println(getFirebaseRef().child("users").child(getFirebaseRef().getAuth().getUid().toString()).child("nearest_dudes").toString());


        final ListView lvMain1 = (ListView) this.findViewById(R.id.listView_online);
        Listonline = new FirebaseListAdapter<Nearest_dudes>(this, Nearest_dudes.class,
                //      android.R.layout.simple_list_item_1, getFirebaseRef().child("Test123")) {
                android.R.layout.simple_list_item_2, getFirebaseRef().child("users").child(getFirebaseRef().getAuth().getUid().toString()).child("nearest_dudes"))


        {
            @Override
            protected void populateView(View v, Nearest_dudes model, int position) {

                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getname());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getuid());
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
                TextView textView1 = (TextView) itemClicked.findViewById(android.R.id.text1);
                TextView textView2 = (TextView) itemClicked.findViewById(android.R.id.text2);
                String strText = textView2.getText().toString();

                System.out.println("Я ВЫБРАЛ ДРУГ213123123А!!!!!!!!!!" + strText);
                Map<String, String> map = new HashMap<String, String>();
                map.put("name_of_friend", strText);

                getFirebaseRef().child("users").child(getFirebaseRef().getAuth().getUid()).child("i_do_photo").child(map.get("name_of_friend")).setValue("what");
                getFirebaseRef().child("users").child(map.get("name_of_friend")).child("request_photo").child(getFirebaseRef().getAuth().getUid().toString()).setValue("what");

                Toast.makeText(getApplicationContext(), textView1.getText(),
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
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {

    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {

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
    public void request_watch() {
        getFirebaseRef().child("users").child(this.getAuth().getUid()).child("request_photo").addChildEventListener(namechange1 = new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("= ТАКОЙ ПОЛЬЗОВАТЕЛЬ ХОЧЕТ СДЕЛАТЬ ФОТО!" + dataSnapshot.getValue().toString());
                System.out.println(dataSnapshot.getKey());
                getFirebaseRef().child("users").child(getFirebaseRef().getAuth().getUid()).child("request_photo").child(dataSnapshot.getKey()).setValue("true");
                getFirebaseRef().child("users").child(dataSnapshot.getKey()).child("i_do_photo").child(getFirebaseRef().getAuth().getUid().toString()).setValue("true");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue().toString() + "= ОТКОЛАЗСЯ ОТ ФОТО С ВАМИ!");

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


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
