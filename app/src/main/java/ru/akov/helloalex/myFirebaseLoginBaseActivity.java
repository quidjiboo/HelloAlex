package ru.akov.helloalex;

import android.app.FragmentManager;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexandr on 11.03.2016.
 */
public abstract class myFirebaseLoginBaseActivity extends FirebaseLoginBaseActivity implements Labal_change_my {
    Firebase con;
    Firebase con2;
    public void set_mylistner(){
        getFirebaseRef().child("users").child(this.getAuth().getUid()).child("My_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println("ЗАШЁЛ ТАКИМИ ПОЛЯМИ" + snapshot.getValue());

                //ПОЧИТАТЬ ПРО ИСКЛЮЧЕНИЯ!!!!
if(snapshot.getValue()!=null){
                Accont_info_my_sington.getInstance().setname(snapshot.getValue().toString());}
                else{ Accont_info_my_sington.getInstance().setname("none");

}

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
    protected void onFirebaseLoggedIn(AuthData authData) {
        if(!authData.toString().equals(Accont_info_my_sington.getInstance().getauth())){

        System.out.println("Я ПОДКЛЮЧЕН!!!!!!!!!!" + authData);
        Map<String, String> map = new HashMap<String, String>();
            map.put("provider", authData.getProvider());
        if (authData.getProviderData().containsKey("displayName")) {
            map.put("displayName", authData.getProviderData().get("displayName").toString());
        }
        getFirebaseRef().child("users").child(authData.getUid()).child("provider").setValue(map.get("provider"));
        getFirebaseRef().child("users").child(authData.getUid()).child("displayName").setValue(map.get("displayName"));
            getFirebaseRef().child("users").child(authData.getUid()).child("My_name").setValue(Accont_info_my_sington.getInstance().getname());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

       // getFirebaseRef().child("users").child(authData.getUid()).child("data_last_connect").setValue(dateFormat.format(new Date()));

            set_mylistner();

    //    izmenit_label();


            final Firebase listofuseronline = new Firebase(getFirebaseRef()+"/usersonline/");
            final Firebase myConnectionsRef = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/connections");
            final Firebase lastOnlineRef = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/lastOnline");
            final Firebase connectedRef = new Firebase(getFirebaseRef()+"/.info/connected");

        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    // add this device to my connections list
                    // this value could contain info about the device or a timestamp too
                    //   myConnectionsRef.removeValue();
                    con = myConnectionsRef.push();

                    //getFirebaseRef().child("/users/" + getAuth().getUid() + "/devasies/" + con.getKey() + "/").setValue(Boolean.TRUE);
                    con.setValue(Boolean.TRUE);

                    // when this device disconnects, remove it
                    con.onDisconnect().removeValue();
                    // when I disconnect, update the last time I was seen online

                  //расеоментировать потом!!!
                //    lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);

                }
            }

            @Override
            public void onCancelled(FirebaseError error) {

                System.err.println("Listener was cancelled at .info/connected");
            }
        });
            myConnectionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists() && snapshot.getChildrenCount() == 1) {
                        con2 = listofuseronline.push();
                        con2.setValue(Boolean.TRUE);
                    }

                }

                @Override
                public void onCancelled(FirebaseError error) {

                    System.err.println("Listener was cancelled at .info/connected");
                }
            });

        Accont_info_my_sington.getInstance().seauth(authData.toString());}
    }
    @Override
    protected void onFirebaseLoggedOut() {
        super.onFirebaseLoggedOut();
        if(con!=null){
        con.removeValue();}

        System.out.println("РАЗРЫВ!!!!!!!!!!");
        Accont_info_my_sington.getInstance().clerar(); //может и не надо удалять

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
