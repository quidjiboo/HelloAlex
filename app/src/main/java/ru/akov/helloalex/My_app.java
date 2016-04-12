package ru.akov.helloalex;

import android.app.Application;

import com.firebase.client.Firebase;

import android.content.Context;


/**
 * Created by User on 21.03.2016.
 */
public class My_app extends Application {
    private static final String FIREBASE_UR1L = "https://resplendent-inferno-864.firebaseio.com/";

    private Firebase firebaseRef;


    @Override
    public void onCreate() {
       super.onCreate();



        Firebase.setAndroidContext(this);
        if(firebaseRef==null){
System.out.println("Сделал firebaseRef");

            firebaseRef = new Firebase(FIREBASE_UR1L);

        }


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

}
