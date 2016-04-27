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

public class MainActivity extends myFirebaseLoginBaseActivity   {


    private static final String FIREBASE_UR1L = "https://resplendent-inferno-864.firebaseio.com/";
    private My_app app;
    //Костылёк потом переделать !!!
    //  static Firebase firebaseRef;
    static String ipString = "0.0.0.0";
    private EditText inpuText;
    FirebaseListAdapter<ChatmessAlex> mListAdapter;


    static private ValueEventListener zamena_list_online1;
   // public static GoogleApiClient mGoogleApiClient;
   // static public  Location mLastLocation;
    static String mLatitudeText;
    static String mLongitudeText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

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
       // app.mGoogleApiClient.connect();

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

     //   app.mGoogleApiClient.disconnect();

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
        this.finish();
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
}



