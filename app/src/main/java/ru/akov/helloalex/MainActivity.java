package ru.akov.helloalex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginError;

import java.util.Random;

public class MainActivity extends myFirebaseLoginBaseActivity  {
private static final String FIREBASE_UR1L = "https://resplendent-inferno-864.firebaseio.com/";
    My_app app;
    //Костылёк потом переделать !!!
    static Firebase firebaseRef;
    private EditText inpuText;
    FirebaseListAdapter<ChatmessAlex> mListAdapter;

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



        final ListView   lvMain = (ListView) this.findViewById(R.id.listViewAkov);

        mListAdapter = new FirebaseListAdapter<ChatmessAlex>(this, ChatmessAlex.class,
                android.R.layout.two_line_list_item, getFirebaseRef().child("Test123")) {
            @Override
            protected void populateView(View v, ChatmessAlex model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getName());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getText());
            }
        };

      lvMain.setAdapter(mListAdapter);

        this.findViewById(R.id.listViewAkov).setFocusable(true);
        this.findViewById(R.id.listViewAkov).requestFocus();



             TextView edf = (TextView) findViewById(R.id.textView_my);
             edf.setText(Accont_info_my_sington.getInstance().getname());




    }




    public void sendMessage(){

        String message = inpuText.getText().toString();
       if(!message.equals("")){
           Random rand = new Random();
           String author = Accont_info_my_sington.getInstance().getname() ;

          ChatmessAlex cMasg = new ChatmessAlex(author,message);

           getFirebaseRef().child("Test123").child("-KCVXxZze4WNA4gRPrWF").setValue(cMasg);


           inpuText.setText("");

       }

    }


    public void OnclickMy(View view) {
        sendMessage();

    }

    @Override
    protected Firebase getFirebaseRef() {


        return                app.getFirebaseRef();
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

    }



    public void OnclickMy_test(View view) {


        if (getFirebaseRef().getAuth()!=null){

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Пользователь" + getFirebaseRef().getAuth().getUid()  + "Разлонтесь!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();}
        else{
            System.out.println("такой пользователь подключен " + getFirebaseRef().getAuth());
            showFirebaseLoginPrompt();
        }

     //   getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public void button_my_new_acc(View view) {
        if (getFirebaseRef().getAuth()!=null){

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Пользователь" + getFirebaseRef().getAuth().getUid()  + "Разлонтесь!",
                    Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();}
        else{
        newAcc(getFirebaseRef().getAuth());
            }

    }


    public void button_my(View view) {


       logout();

       getFirebaseRef().unauth();
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
        mListAdapter.cleanup();
    }

    public void next_scr(View view) {
        Intent intent = new Intent(MainActivity.this, Spisok_online.class);

        startActivity(intent);
        //  firebaseRef.unauth();

    }


    @Override
    public void izmenit_label() {
        TextView edf = (TextView) findViewById(R.id.textView_my);
        edf.setText(Accont_info_my_sington.getInstance().getname());
    }
}
