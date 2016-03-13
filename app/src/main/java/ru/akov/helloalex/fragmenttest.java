package ru.akov.helloalex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by Alexandr on 11.03.2016.
 */
public class fragmenttest extends  DialogFragment{
    View view=null;
    Firebase mRefmy;

    public fragmenttest setRef(Firebase ref) {
        this.mRefmy = ref;
        return this;
    }
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        view= inflater.inflate(R.layout.dialog1, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

     //   return(builder.setTitle("Форма авторизации").setView(form)
     //   .setPositiveButton(android.R.string.ok, this)
    //    .setNegativeButton(android.R.string.cancel, null).create());



        view.findViewById(R.id.password_button).setOnClickListener(
                new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                                EditText loginBox=(EditText)view.findViewById(R.id.email_my);
                                EditText passwordBox1=(EditText)view.findViewById(R.id.password1);
                                EditText passwordBox2=(EditText)view.findViewById(R.id.password2);
                                String login = loginBox.getText().toString();

                            view.findViewById(R.id.loading_section).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.email_my).setVisibility(View.GONE);
                            view.findViewById(R.id.password1).setVisibility(View.GONE);
                            view.findViewById(R.id.password2).setVisibility(View.GONE);
                            view.findViewById(R.id.password_button).setVisibility(View.GONE);
                                if(passwordBox1.getText().toString().equals(passwordBox2.getText().toString())&&passwordBox1.getText().toString()!=null&&passwordBox2.getText().toString()!=null){
                                        String password = passwordBox1.getText().toString();
                                        Log.i("ЛОГИН", "одинаковые поля");

                                    mRefmy.createUser(login,password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                                        @Override
                                        public void onSuccess(Map<String, Object> result) {

                                            System.out.println("Создал аккаунт" + result.get("uid") + " Залогиньтесь");
                                            Toast.makeText(view.getContext(), "Создал аккаунт" + result.get("uid") + " Залогиньтесь", Toast.LENGTH_SHORT).show();
                                            view.findViewById(R.id.button_ok).setVisibility(View.VISIBLE);
                                            view.findViewById(R.id.loading_section).setVisibility(View.GONE);
                                        }
                                        @Override
                                        public void onError(FirebaseError firebaseError) {
                                            view.findViewById(R.id.loading_section).setVisibility(View.GONE);
                                            view.findViewById(R.id.email_my).setVisibility(View.VISIBLE);
                                            view.findViewById(R.id.password1).setVisibility(View.VISIBLE);
                                            view.findViewById(R.id.password2).setVisibility(View.VISIBLE);
                                            view.findViewById(R.id.password_button).setVisibility(View.VISIBLE);
                                            // there was an error
                                            Toast.makeText(view.getContext(), "Ошибка создания: " + firebaseError.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                       } else {
                                    view.findViewById(R.id.loading_section).setVisibility(View.GONE);
                                    view.findViewById(R.id.email_my).setVisibility(View.VISIBLE);
                                    view.findViewById(R.id.password1).setVisibility(View.VISIBLE);
                                    view.findViewById(R.id.password2).setVisibility(View.VISIBLE);
                                    view.findViewById(R.id.password_button).setVisibility(View.VISIBLE);
                                        Log.i("ЛОГИН", "НЕ одинаковые поля!!");
                                 //       Activity activity = getActivity();
                                        Toast.makeText(view.getContext(), "Пароли не одинаковы!! Повторите", Toast.LENGTH_SHORT).show();

                                      //  dismiss();
                                        }


                        }
                }

        );
    view.findViewById(R.id.button_ok).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }}
    );

    view.findViewById(R.id.loading_section).setVisibility(View.INVISIBLE);
    view.findViewById(R.id.button_ok).setVisibility(View.INVISIBLE);
        builder.setView(this.view);
        this.setRetainInstance(true);
        return builder.create();
         }


@Override
public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
        }
@Override
public void onCancel(DialogInterface unused) {
        super.onCancel(unused);
        }
   }




