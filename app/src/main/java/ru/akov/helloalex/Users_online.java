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

    public Users_online() {
        // necessary for Firebase's deserializer
    }



    public Users_online(String My_name,String provider) {
        this.My_name = My_name;
    /*    this.connections = connections;
        this.data_last_connect = data_last_connect;
        this.lastOnline = lastOnline;*/
        this.provider = provider;
    }
    public String getMy_name() {
        return My_name;
    }

/*    public String getData_last_connect() {
        return data_last_connect;
    }

    public Map<String, Boolean> getConnections() {
        return connections;
    }

    public long getLastOnline() {
        return lastOnline;
    }*/

    public String getProvider() {
        return provider;
    }
}
