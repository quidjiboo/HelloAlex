package ru.akov.helloalex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.io.CharArrayReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
private static final String FIREBASE_URL = "https://resplendent-inferno-864.firebaseio.com/";
    private Firebase firebaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_URL);

        EditText inpuText = (EditText) findViewById(R.id.messageText);
        inpuText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                }
                return true;
            }
        });
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
   public void sendMessage(){
       EditText Textinput = (EditText) findViewById(R.id.messageText);
        String message = Textinput.getText().toString();
       if(!message.equals("")){
           Random rand = new Random();
           String author = "Tesuser" + rand.nextInt(1000) ;
           ChatmessAlex cMasg = new ChatmessAlex(author,message);
           firebaseRef.push().setValue(cMasg);
           Textinput.setText("");
       }
    }

}
