package ru.akov.helloalex;

/**
 * Created by User on 10.02.2016.
 */
public class User_online_id {


    private Boolean showall;
/*    private String data_last_connect;
    private Map<String,Boolean> connections;
    private long lastOnline;*/

    private String phonemodel1;
  //  private Boolean onlineu;

    public User_online_id() {
        // necessary for Firebase's deserializer
    }



    public User_online_id( Boolean showall, String phonemodel1) {

        this.showall = showall;
        this.phonemodel1 = phonemodel1;

    }
    public Boolean getshowall() {
        return showall;
    }




    public String getPhonemodel1() {
        return  phonemodel1;
    }

}
