package ru.akov.helloalex;

import android.app.FragmentManager;

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
public abstract class myFirebaseLoginBaseActivity extends FirebaseLoginBaseActivity implements Labal_change_my{


    private    Firebase con; //было статик зачемто!
    private    Firebase con1;
   private    Firebase i_do_photo;
    private    Firebase request_photo;
    private    Firebase nearest_dudes;
    private String uid_my=""; //было статик зачемто!
//    static private Firebase conectionlist;
//    static private GeoQueryEventListener  Geo123;

   static private ValueEventListener originalListener;
    static private ValueEventListener namechange;
   // static private ValueEventListener listconectionlistner;
    static private ValueEventListener zamena_list_online;




    public void set_mylistner(){
        getFirebaseRef().child("users").child(this.getAuth().getUid()).addValueEventListener(namechange = new  ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {



                //ПОЧИТАТЬ ПРО ИСКЛЮЧЕНИЯ!!!!
/*if(snapshot.getValue()!=null){
                Accont_info_my_sington.getInstance().setname(snapshot.getValue().toString());}
                else{ Accont_info_my_sington.getInstance().setname("none");

}*/

                if (snapshot.child("My_name").getValue() != null) {
                    System.out.println("ЗАШЁЛ ТАКИМИ ПОЛЯМИ" + snapshot.child("My_name").getValue());
                    izmenit_singltone(snapshot.child("My_name").getValue().toString());

                    System.out.println("ОТРАБОТАЛ!!!! " + snapshot.child("My_name").getValue().toString());

                } else {
                    getFirebaseRef().child("users").child(getAuth().getUid()).child("My_name").setValue("none");
                    getFirebaseRef().child("users").child(getAuth().getUid()).child("showall").setValue("false");
                    izmenit_singltone("none");
                }

                //   izmenit_singltone(snapshot.getValue().toString());.


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
    protected void onFirebaseLoggedIn(final AuthData authData) {


        set_mylistner();

        Accont_info_my_sington.getInstance().seauth(authData.toString());
    //    final Firebase showall = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/showall");
        final Firebase listof_accs_online_GPS = new Firebase(getFirebaseRef()+"/firebasehq");
        final Firebase listof_accs_online = new Firebase(getFirebaseRef()+"/onlineusers");
   //     final Firebase myConnectionsRef = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/connections");
   //     final Firebase lastOnlineRef = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/lastOnline");
        final Firebase connectedRef = new Firebase(getFirebaseRef()+"/.info/connected");
        final Firebase Userref = new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid());
        i_do_photo =  new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/i_do_photo");
        request_photo =  new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/request_photo");
        nearest_dudes  =  new Firebase(getFirebaseRef()+"/users/"+getAuth().getUid()+"/nearest_dudes");
        request_photo.onDisconnect().removeValue();
        i_do_photo.onDisconnect().removeValue();
        nearest_dudes.onDisconnect().removeValue();

        connectedRef.addValueEventListener(originalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    // add this device to my connections list
                    // this value could contain info about the device or a timestamp too
                    //   myConnectionsRef.removeValue();

                    //  con = myConnectionsRef.push();
                    //    con = listof_accs_online.push();
                    con=listof_accs_online.child(authData.getUid());
                    con1=listof_accs_online_GPS.child(authData.getUid());


                    Users_online cMasg = new Users_online(Accont_info_my_sington.getInstance().getname(), uid_my, android.os.Build.MODEL.toString());

                    //getFirebaseRef().child("/users/" + getAuth().getUid() + "/devasies/" + con.getKey() + "/").setValue(Boolean.TRUE);
                    //        con.setValue(Boolean.TRUE);
                    con.setValue(cMasg);
                    // when this device disconnects, remove it

                 //   listof_accs_online.removeValue(); //Для проверки прав!!!


                    con.onDisconnect().removeValue();
                    con1.onDisconnect().removeValue();
                    // when I disconnect, update the last time I was seen online

                    //расеоментировать потом!!!
                    //    lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);
                    getFirebaseRef().child("users").child(authData.getUid()).child("phonemodel").setValue(android.os.Build.MODEL.toString());


                }

            }

            @Override
            public void onCancelled(FirebaseError error) {

                System.err.println("Listener was cancelled at .info/connected");
            }
        });

// вродебы всё работает
        Userref.addValueEventListener(zamena_list_online = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean showall = dataSnapshot.child("/showall").getValue(Boolean.class);
                if (!showall&&getFirebaseRef().getAuth()!=null) {
                    if (con != null&&getAuth()!=null) {
                        con.removeValue();
                        i_do_photo.removeValue();
                        request_photo.removeValue();
                        nearest_dudes.removeValue();
                    }
                    if (con1 != null&&getAuth()!=null) {
                        con1.removeValue();

                    }
                }

                if (showall&&getFirebaseRef().getAuth()!=null){
                    System.out.print("ТУТУТТУТУ");
                    if(con==null){
                        con=listof_accs_online.child(authData.getUid());  }
                    if(con1==null){
                        con1=listof_accs_online_GPS.child(authData.getUid());  }
                    Users_online cMasg = new Users_online(Accont_info_my_sington.getInstance().getname(), uid_my, android.os.Build.MODEL.toString());

                    //getFirebaseRef().child("/users/" + getAuth().getUid() + "/devasies/" + con.getKey() + "/").setValue(Boolean.TRUE);
                    //        con.setValue(Boolean.TRUE);
                    con.setValue(cMasg);}


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        System.out.println("ВОТ ТАК ПОДКЛЮЧИЛСЯ ОПЯТЬ" + authData);
      uid_my=getAuth().getUid();




        if(con!=null&&!authData.toString().equals(Accont_info_my_sington.getInstance().getauth())){
            con.removeValue();
            i_do_photo.removeValue();
            request_photo.removeValue();
            nearest_dudes.removeValue();
        }
        if(con1!=null&&!authData.toString().equals(Accont_info_my_sington.getInstance().getauth())){
            con1.removeValue();
            i_do_photo.removeValue();
            request_photo.removeValue();
            nearest_dudes.removeValue();}
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
System.out.println("Записываю в базу кординаты"+Accont_info_my_sington.getInstance().getGPSLatitude());


            getFirebaseRef().child("users").child(authData.getUid()).child("phonemodel").setValue(android.os.Build.MODEL.toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

       // getFirebaseRef().child("users").child(authData.getUid()).child("data_last_connect").setValue(dateFormat.format(new Date()));







            }


    }
//переопределил , что бы при раз-логинивании отваливать записи в списках онлайн
    @Override
    public void logout() {
        final Firebase connectedRef = new Firebase(getFirebaseRef()+"/.info/connected");
      //  final Firebase myConnectionsRef = new Firebase(getFirebaseRef()+"/users/"+uid_my+"/connections");
        final Firebase Userref = new Firebase(getFirebaseRef()+"/users/"+uid_my);

        if(zamena_list_online!=null){
            Userref.removeEventListener(zamena_list_online);}
        if(namechange!=null){
            Userref.removeEventListener(namechange);}

        if(originalListener!=null){
            connectedRef.removeEventListener(originalListener);}



        if(con!=null){
            con.removeValue();
            i_do_photo.removeValue();
            request_photo.removeValue();
            nearest_dudes.removeValue();
        }
        if(con1!=null){
            con1.removeValue();
            i_do_photo.removeValue();
            request_photo.removeValue();
            nearest_dudes.removeValue();
        }

/*        if(conectionlist!=null){
            conectionlist.removeValue();}*/

        super.logout();
    }

    @Override
    protected void onFirebaseLoggedOut() {
        super.onFirebaseLoggedOut();




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
