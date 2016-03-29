package ru.akov.helloalex;

/**
 * Created by User on 10.02.2016.
 */
public class Users_online {

    private String My_name;
/*    private String data_last_connect;
    private Map<String,Boolean> connections;
    private long lastOnline;*/
    private String provider;
    private String phonemodel1;
  //  private Boolean onlineu;

    public Users_online() {
        // necessary for Firebase's deserializer
    }



    public Users_online(String My_name,String provider,String phonemodel1) {
        this.My_name = My_name;

        this.provider = provider;
        this.phonemodel1 = phonemodel1;

    }
    public String getMy_name() {
        return My_name;
    }


    public String getProvider() {
        return provider;
    }
    public String getPhonemodel1() {
        return  phonemodel1;
    }

}
