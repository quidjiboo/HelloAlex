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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.io.CharArrayReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
private static final String FIREBASE_URL = "https://resplendent-inferno-864.firebaseio.com/";
    private Firebase firebaseRef;
    private EditText inpuText;
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


        ListView lvMain = (ListView) findViewById(R.id.listViewAkov);
        lvMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("dsfsd", "паовпролвап !!!!!!!!!! !!!!!!!!!!!");

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inpuText.getWindowToken(), 0);

                return false;
            }
        });

    }
   public void sendMessage(){
     //  Log.d("dsfsd","паовпролвап !!!!!!!!!! !!!!!!!!!!!");
       EditText Textinput = (EditText) findViewById(R.id.messageText);
        String message = inpuText.getText().toString();
       if(!message.equals("")){
           Random rand = new Random();
           String author = "Tesuser" + rand.nextInt(1000) ;
           ChatmessAlex cMasg = new ChatmessAlex(author,message);
           firebaseRef.push().setValue(cMasg);
           inpuText.setText("");
       }

    }

    public void OnclickMy(View view) {
        sendMessage();
    }
}
