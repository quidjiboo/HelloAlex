package ru.akov.helloalex;

import android.app.FragmentManager;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;

/**
 * Created by Alexandr on 11.03.2016.
 */
public abstract class myFirebaseLoginBaseActivity extends FirebaseLoginBaseActivity implements Labal_change_my {


    public void set_mylistner(){
        getFirebaseRef().child("users").child(this.getAuth().getUid()).child("My_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println("ЗАШЁЛ ТАКИМИ ПОЛЯМИ" + snapshot.getValue());
                Accont_info_my_sington.getInstance().setname(snapshot.getValue().toString());

                izmenit_label();
          //      TextView edf = (TextView) findViewById(R.id.textView_my);
           //     edf.setText(Accont_info_my_sington.getInstance().getname());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });
    }
    @Override
    protected void onFirebaseLoggedOut() {
        super.onFirebaseLoggedOut();
        System.out.println("РАЗРЫВ!!!!!!!!!!");
        Accont_info_my_sington.getInstance().clerar();

        izmenit_label();
    //    TextView edf = (TextView) findViewById(R.id.textView_my);
    //    edf.setText(Accont_info_my_sington.getInstance().getname());
    }
    public void newAcc(AuthData authData) {
        if(authData==null){

            FragmentManager manager = getFragmentManager();
            fragmenttest myDialogFragment = new fragmenttest();
            // myfragment myDialogFragment = new myfragment();
            myDialogFragment.setRef(getFirebaseRef());
            myDialogFragment.show(manager, "dialog");
        }
        else{return;}


    }

}
