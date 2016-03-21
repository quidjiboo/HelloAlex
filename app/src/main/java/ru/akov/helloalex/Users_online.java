package ru.akov.helloalex;

import java.util.Map;

/**
 * Created by User on 10.02.2016.
 */
public class Users_online {
    private String My_name;
    private String data_last_connect;
    private Map<String,Boolean> connections;
    private long lastOnline;
    private String provider;

    public Users_online() {
        // necessary for Firebase's deserializer
    }
    public Users_online(String Myname,String data_last_connect, Map<String,Boolean> connections,  long lastOnline, String provider) {
        this.My_name = Myname;
        this.connections=connections;
        this.data_last_connect = data_last_connect;
        this.lastOnline = lastOnline;
        this.provider = provider;
    }


    public String getMy_Name() {
        return My_name;
    }
    public Map<String,Boolean>  getconnections() {
        return connections;
    }
    public String  getdata_last_connect() {
        return data_last_connect;
    }
    public long getlastOnline() {
        return lastOnline;
    }
    public String  getprovider() {
        return provider;
    }



}
