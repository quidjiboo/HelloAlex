package ru.akov.helloalex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.FirebaseLoginError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 18.03.2016.
 */
public class Spisok_online extends myFirebaseLoginBaseActivity {
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

    public void OnclickMy_back(View view) {
        Intent intent = new Intent(Spisok_online.this, MainActivity.class);

        startActivity(intent);
    }

    public void next_scr_spisok_friends(View view) {
        Intent intent = new Intent(Spisok_online.this, Spisok_friends.class);

        startActivity(intent);
    }
}
