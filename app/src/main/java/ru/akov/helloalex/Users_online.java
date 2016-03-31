package ru.akov.helloalex;

/**
 * Created by User on 10.02.2016.
 */
public class Users_online {

    private String my_name;
/*    private String data_last_connect;
    private Map<String,Boolean> connections;
    private long lastOnline;*/

    private String phonemodel1;
  //  private Boolean onlineu;

    public Users_online() {
        // necessary for Firebase's deserializer
    }



    public Users_online(String my_name,String phonemodel1) {
        this.my_name = my_name;

        this.phonemodel1 = phonemodel1;

    }
    public String getmy_name() {
        return my_name;
    }



    public String getPhonemodel1() {
        return  phonemodel1;
    }

}
