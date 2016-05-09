package ru.akov.helloalex;

/**
 * Created by User on 10.02.2016.
 */
public class Nearest_dudes {

    private String name;
    private String uid;

/*    private String data_last_connect;
    private Map<String,Boolean> connections;
    private long lastOnline;*/


  //  private Boolean onlineu;

    public Nearest_dudes() {
        // necessary for Firebase's deserializer
    }



    public Nearest_dudes(String name,String uid) {
        this.name = name;
        this.uid = uid;
    }
    public String getuid() {
        return uid;
    }
    public String getname() {
        return name;
    }





}
