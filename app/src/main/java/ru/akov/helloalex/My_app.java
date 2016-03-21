package ru.akov.helloalex;

import android.app.Application;
import android.os.Bundle;
import android.widget.EditText;

import com.firebase.client.Firebase;

/**
 * Created by User on 21.03.2016.
 */
public class My_app extends Application {
    private static final String FIREBASE_UR1L = "https://resplendent-inferno-864.firebaseio.com/";

    //Костылёк потом переделать !!!
    private Firebase firebaseRef;
    @Override
    public void onCreate() {
       super.onCreate();

        Firebase.setAndroidContext(this);
        if(firebaseRef==null){


            firebaseRef = new Firebase(FIREBASE_UR1L);}

    }

    protected Firebase getFirebaseRef() {
        return

                        firebaseRef;


    }
}
