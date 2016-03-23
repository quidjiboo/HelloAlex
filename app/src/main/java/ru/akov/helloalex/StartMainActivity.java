package ru.akov.helloalex;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginError;

public class StartMainActivity extends myFirebaseLoginBaseActivity  {
private static final String FIREBASE_UR1L = "https://resplendent-inferno-864.firebaseio.com/";
    My_app app;
    //Костылёк потом переделать !!!
    static Firebase firebaseRef;
    private EditText inpuText;
    FirebaseListAdapter<ChatmessAlex> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.startactivity_main);
        app = ((My_app) getApplicationContext());


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



    public void OnclickMy_test_login_new(View view) {


        if (getFirebaseRef().getAuth()!=null){

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Пользователь" + getFirebaseRef().getAuth().getUid()  + "ЧТОТО ПОШЛО НЕ ТАК",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();}
        else{
            System.out.println("такой пользователь подключен " + getFirebaseRef().getAuth());
            showFirebaseLoginPrompt();
        }

     //   getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public void button_my_new_acc_new(View view) {
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
     //   app.remove();
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


    @Override
    public void izmenit_label() {
        TextView edf = (TextView) findViewById(R.id.textView_my);
        edf.setText(Accont_info_my_sington.getInstance().getname());
    }
    @Override
    public void
    izmenit_singltone(String name){
        if(name!=null)
        Accont_info_my_sington.getInstance().setname(name);
        else
            Accont_info_my_sington.getInstance().setname("none");

    }
}
