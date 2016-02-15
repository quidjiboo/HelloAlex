package ru.akov.helloalex;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.CharArrayReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
private static final String FIREBASE_URL = "https://resplendent-inferno-864.firebaseio.com/";
    private String[] String_in_listview  = new String[10];
    private Firebase firebaseRef;
    private EditText inpuText;
    private ListView lvMain;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_URL);

        inpuText = (EditText) findViewById(R.id.messageText);


        inpuText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                }
                return true;
            }
        });


        for (int i = 0; i < 10; i++){
            System.out.println("УРААА!!!!!!!!!!");
           String_in_listview[i] = "123";}

        lvMain = (ListView) findViewById(R.id.listViewAkov);
        adapter = new ArrayAdapter <String>(this,	android.R.layout.simple_list_item_1, String_in_listview);
        adapter.notifyDataSetChanged();
        adapter.setNotifyOnChange(true);

        lvMain.setAdapter(adapter);

        firebaseRef.child("-KAF7jIEqeTk1idjnZto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."

                String_in_listview[0] = snapshot.getValue().toString();
                String_in_listview[2]= String_in_listview[0];
                String_in_listview[5]= String_in_listview[0];
                String_in_listview[8]= String_in_listview[0];
              adapter.notifyDataSetChanged();
                //       String_in_listview[1]=(String)snapshot.getValue();

            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

//        lvMain.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("dsfsd", "паовпролвап !!!!!!!!!! !!!!!!!!!!!");
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(inpuText.getWindowToken(), 0);
//
//                return false;
//            }
//        });



    }




   public void sendMessage(){
     //  Log.d("dsfsd","паовпролвап !!!!!!!!!! !!!!!!!!!!!");
       EditText Textinput = (EditText) findViewById(R.id.messageText);
        String message = inpuText.getText().toString();
       if(!message.equals("")){
           Random rand = new Random();
           String author = "Tesuser" + rand.nextInt(1000) ;
          ChatmessAlex cMasg = new ChatmessAlex(author,message);

          firebaseRef.child("-KAF7jIEqeTk1idjnZto").setValue(cMasg);
           inpuText.setText("");

//           firebaseRef.child("-KAF7jIEqeTk1idjnZto").addValueEventListener(new ValueEventListener() {
//               @Override
//               public void onDataChange(DataSnapshot snapshot) {
//                   System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
//                   outputText.setText((String)snapshot.getValue());
//               }
//               @Override
//               public void onCancelled(FirebaseError error) { }
//           });

       }

    }


    public void OnclickMy(View view) {
        sendMessage();

    }
}
