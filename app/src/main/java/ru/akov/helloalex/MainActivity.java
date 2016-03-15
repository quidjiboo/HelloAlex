package ru.akov.helloalex;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends myFirebaseLoginBaseActivity  {
private static final String FIREBASE_UR1L = "https://resplendent-inferno-864.firebaseio.com/";
   // private String[] String_in_listview  = new String[10];
    private Firebase firebaseRef;
    private EditText inpuText;

     //private ListView lvMain;
    private ArrayAdapter<String> adapter;
 //   private String sostyanie="згыещ";

    FirebaseListAdapter<ChatmessAlex> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_UR1L);


     //   firebaseRef.unauth();









     //   firebaseRef.authWithPassword("quidjiboo@mail.ru", "qwer1ty", authResultHandler);
      //  System.out.println("JПОПЫТКА ПОДКЛЮЧИТСЬЯ " + firebaseRef.getAuth());
     //   firebaseRef.unauth();

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


     //   for (int i = 0; i < 10; i++){
     //       System.out.println("УРААА!!!!!!!!!!");
      //     String_in_listview[i] = "";}



      //  adapter = new ArrayAdapter <String>(this,	android.R.layout.simple_list_item_1, String_in_listview);
      //  adapter.notifyDataSetChanged();
       // adapter.setNotifyOnChange(true);


        final ListView   lvMain = (ListView) this.findViewById(R.id.listViewAkov);

        mListAdapter = new FirebaseListAdapter<ChatmessAlex>(this, ChatmessAlex.class,
                android.R.layout.two_line_list_item, firebaseRef.child("Test123")) {
            @Override
            protected void populateView(View v, ChatmessAlex model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getName());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getText());
            }
        };

      //  lvMain.setAdapter(adapter);
      lvMain.setAdapter(mListAdapter);
        this.findViewById(R.id.listViewAkov).setFocusable(true);
        this.findViewById(R.id.listViewAkov).requestFocus();
        //    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
     /*   firebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                if(firebaseRef.getAuth()!=null){
                    System.out.println("Я ПОДКЛЮЧЕН!!!!!!!!!! МОЁ ИМЯ" + snapshot.child("users").child(firebaseRef.getAuth().getUid()).child("users").child("My_name").getValue().toString());
                    Accont_info_my_sington.getInstance().setname(snapshot.child("users").child(firebaseRef.getAuth().getUid()).child("users").child("My_name").getValue().toString());}
                else{
                    Accont_info_my_sington.getInstance().setname("НЕЗАЛОГИНЕН");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/


/*        firebaseRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {

                //ЭТО НАДО ПЕРЕНЕСТИ В СОСЗДАНИЕ АККАУНТА
                if (authData != null) {
                    System.out.println("Я ПОДКЛЮЧЕН!!!!!!!!!!" + authData);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("provider", authData.getProvider());
                    if (authData.getProviderData().containsKey("displayName")) {
                        map.put("displayName", authData.getProviderData().get("displayName").toString());
                    }
                    firebaseRef.child("users").child(authData.getUid()).child("provider").setValue(map.get("provider"));
                    firebaseRef.child("users").child(authData.getUid()).child("displayName").setValue(map.get("displayName"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

                    firebaseRef.child("users").child(authData.getUid()).child("data_last_connect").setValue(dateFormat.format(new Date()));
                    // user is logged in

                } else {
                    // user is not logged in
                    System.out.println("РАЗРЫВ!!!!!!!!!!" + authData);

                }
            }

        });*/






/*
        firebaseRef.child("Test123").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue().toString());  //prints "Do you have data? You'll love Firebase."

                for (int i = 0; i < 9; i++) {
                    String_in_listview[i] = String_in_listview[i + 1];


                }

                String_in_listview[9] = snapshot.getValue().toString();
                adapter.notifyDataSetChanged();
                //       String_in_listview[1]=(String)snapshot.getValue();
                firebaseRef.removeEventListener(this);
            }




            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });*/

/*        lvMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("dsfsd", "паовпролвап !!!!!!!!!! !!!!!!!!!!!");

               InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inpuText.getWindowToken(), 0);

                return false;
            }
        });*/




    }




    public void sendMessage(){
     //  Log.d("dsfsd","паовпролвап !!!!!!!!!! !!!!!!!!!!!");
    //   EditText Textinput = (EditText) findViewById(R.id.messageText);
        String message = inpuText.getText().toString();
       if(!message.equals("")){
           Random rand = new Random();
           String author = Accont_info_my_sington.getInstance().getname() ;
     //      String author = "Tesuser" + rand.nextInt(1000) ;
          ChatmessAlex cMasg = new ChatmessAlex(author,message);

           firebaseRef.child("Test123").child("-KCVXxZze4WNA4gRPrWF").setValue(cMasg);

        //  firebaseRef.push().setValue(cMasg);

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

    @Override
    protected Firebase getFirebaseRef() {
        return firebaseRef;
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

        setEnabledAuthProvider(AuthProviderType.PASSWORD);

    }



    public void OnclickMy_test(View view) {
        System.out.println("такой пользователь подключен " + firebaseRef.getAuth());
        showFirebaseLoginPrompt();
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
        newAcc(firebaseRef.getAuth());
            }

    }


    public void button_my(View view) {
        logout();
      //  firebaseRef.unauth();

    }

   /* кнопка была
   public void new_acc(View view) {

        System.out.println("такой пользователь подключен " + firebaseRef.getAuth());
        showFirebaseLoginPromptmy(firebaseRef.getAuth());



        myfragment tetsobj = new myfragment();
        FragmentManager manager = getSupportFragmentManager();
        tetsobj.show(manager,"cvgdflksjvlkdsjflkjdlksdj");


        myfragment myDialogFragment = new myfragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        myDialogFragment.show(transaction, "dialog");

    }
*/
   @Override
   protected void onFirebaseLoggedIn(AuthData authData) {
       System.out.println("Я ПОДКЛЮЧЕН!!!!!!!!!!" + authData);
       Map<String, String> map = new HashMap<String, String>();
       map.put("provider", authData.getProvider());
       if (authData.getProviderData().containsKey("displayName")) {
           map.put("displayName", authData.getProviderData().get("displayName").toString());
       }
       firebaseRef.child("users").child(authData.getUid()).child("provider").setValue(map.get("provider"));
       firebaseRef.child("users").child(authData.getUid()).child("displayName").setValue(map.get("displayName"));
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

       firebaseRef.child("users").child(authData.getUid()).child("data_last_connect").setValue(dateFormat.format(new Date()));

       set_mylistner();

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
}
