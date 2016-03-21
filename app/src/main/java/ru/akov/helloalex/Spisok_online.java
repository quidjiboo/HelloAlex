package ru.akov.helloalex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.FirebaseLoginError;

/**
 * Created by User on 18.03.2016.
 */
public class Spisok_online extends myFirebaseLoginBaseActivity implements Labal_change_my  {
    My_app app;
    FirebaseListAdapter<Users_online> Listonline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spisok_online);
        app = ((My_app) getApplicationContext());

        final ListView lvMain1 = (ListView) this.findViewById(R.id.listView_online);
        Listonline = new FirebaseListAdapter<Users_online>(this, Users_online.class,
          //      android.R.layout.simple_list_item_1, getFirebaseRef().child("Test123")) {
               android.R.layout.simple_list_item_1, getFirebaseRef().child("users")) {
            @Override
            protected void populateView(View v, Users_online model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getMy_Name());
             //   ((TextView)v.findViewById(android.R.id.text2)).setText(model.getText());
            }
        };

        lvMain1.setAdapter(Listonline);

        TextView edf = (TextView) findViewById(R.id.textView_my_online);
        edf.setText(Accont_info_my_sington.getInstance().getname());
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
    protected void onStop() {
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
        Listonline.cleanup();
    }

}
