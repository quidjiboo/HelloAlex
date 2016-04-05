package ru.akov.helloalex;

import android.app.FragmentManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
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
    static private   Firebase con;

    static private String uid_my="";
    static private Firebase conectionlist;
   static private ValueEventListener originalListener;
    static private ValueEventListener listconectionlistner;
    static private ValueEventListener zamena_list_online;


    public void set_mylistner(){
        getFirebaseRef().child("users").child(this.getAuth().getUid()).child("My_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println("ЗАШЁЛ ТАКИМИ ПОЛЯМИ" + snapshot.getValue());

                //ПОЧИТАТЬ ПРО ИСКЛЮЧЕНИЯ!!!!
/*if(snapshot.getValue()!=null){
                Accont_info_my_sington.getInstance().setname(snapshot.getValue().toString());}
                else{ Accont_info_my_sington.getInstance().setname("none");

}*/

                if (snapshot.getValue() != null) {
                    izmenit_singltone(snapshot.getValue().toString());

                } else {
                    izmenit_singltone("none");
                }

                //   izmenit_singltone(snapshot.getValue().toString());.
                System.out.println("ОТРАБОТАЛ!!!! " + snapshot.getValue().toString());
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
        System.out.println("ВОТ ТАК ПОДКЛЮЧИЛСЯ ОПЯТЬ" +authData);
      uid_my=getAuth().getUid();
        if(con!=null&&!authData.toString().equals(Accont_info_my_sington.getInstance().getauth())){
            con.removeValue();}
        if(!authData.toString().equals(Accont_info_my_sington.getInstance().getauth())){

        System.out.println("Я ПОДКЛЮЧЕН!!!!!!!!!!" + authData);
        Map<String, String> map = new HashMap<String, String>();
            map.put("provider", authData.getProvider());
        if (authData.getProviderData().containsKey("displayName")) {
            map.put("displayName", authData.getProviderData().get("displayName").toString());
        }
        getFirebaseRef().child("users").child(authData.getUid()).child("provider").setValue(map.get("provider"));
        getFirebaseRef().child("users").child(authData.getUid()).child("displayName").setValue(map.get("displayName"));
           // getFirebaseRef().child("users").child(authData.getUid()).child("My_name").setValue(Accont_info_my_sington.getInstance().getname());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

       // getFirebaseRef().child("users").child(authData.getUid()).child("data_last_connect").setValue(dateFormat.format(new Date()));



    //    izmenit_label();



String listof_accs_online_string = "/onlineusers";
String myConnectionsRef_string = "/users/"+getAuth().getUid()+"/connections";
String lastOnlineRef_string = "/users/"+getAuth().getUid()+"/lastOnline";
String connectedRef_string = "/.info/connected";
String Userref_string ="/users/"+getAuth().getUid();

            final Firebase listof_accs_online = new Firebase(getFirebaseRef()+"/onlineusers");
            final Firebase myConnectionsRef = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/connections");
            final Firebase lastOnlineRef = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/lastOnline");
            final Firebase connectedRef = new Firebase(getFirebaseRef()+"/.info/connected");
            final Firebase Userref = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid());





            connectedRef.addValueEventListener(originalListener = new ValueEventListener() {
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
if(conectionlist==null)
                        conectionlist = listof_accs_online.push();

                        Userref.addValueEventListener(zamena_list_online = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Users_online cMasg = new Users_online(dataSnapshot.child("My_name").getValue().toString(),uid_my, android.os.Build.MODEL.toString());


                                conectionlist.setValue(cMasg);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        conectionlist.onDisconnect().removeValue();
                    }
                }

                @Override
                public void onCancelled(FirebaseError error) {

                    System.err.println("Listener was cancelled at .info/connected");
                }
            });



        Accont_info_my_sington.getInstance().seauth(authData.toString());
            set_mylistner();}


    }



    @Override
    protected void onFirebaseLoggedOut() {
        super.onFirebaseLoggedOut();
        final Firebase connectedRef = new Firebase(getFirebaseRef()+"/.info/connected");
        final Firebase myConnectionsRef = new Firebase(getFirebaseRef()+"/users/"+uid_my+"/connections");
        final Firebase Userref = new Firebase(getFirebaseRef()+"/users/"+uid_my);
        if(zamena_list_online!=null){
            Userref.removeEventListener(zamena_list_online);}
        if(listconectionlistner!=null){
            myConnectionsRef.removeEventListener(listconectionlistner);}

        if(originalListener!=null){
            connectedRef.removeEventListener(originalListener);}



        if(con!=null){
            con.removeValue();}

        if(conectionlist!=null){
            conectionlist.removeValue();}



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
