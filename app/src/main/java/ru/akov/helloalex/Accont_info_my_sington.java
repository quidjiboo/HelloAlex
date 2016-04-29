package ru.akov.helloalex;

/**
 * Created by Alexandr on 13.03.2016.
 */
public class Accont_info_my_sington {
    private String name_for_log;
    private String name_use_afterlogin;
    private String pass;
    private String mail;
    private static Accont_info_my_sington instance;
    private String auth;
    private String GPSLatitude;
    private String GPSLongitude;
    private Accont_info_my_sington(){
        name_for_log="";
        pass="";
        mail="";
        name_use_afterlogin="new instane";
        GPSLatitude="";
        GPSLongitude="";
    }

    public void setGPS(String gpsx, String gpsy){
        this.GPSLatitude=gpsx;this.GPSLongitude=gpsy;

    }
    public String getGPSLatitude(){
        return GPSLatitude;
    }
    public String getGPSLongitude(){
        return GPSLongitude;
    }

    public static void  initInstance() {
        if(instance==null){
            instance = new Accont_info_my_sington();   /// спорное решение !!!
        }
    }

    public static synchronized Accont_info_my_sington getInstance() {

        return instance;
    }

    public String getname(){
        return name_use_afterlogin;
    }
    public void setname(String name){

        this.name_for_log=name;
        name_use_afterlogin=name_for_log;
    }
    public void seauth(String name){
        this.auth=name;
    }
    public String getauth(){
        return auth;
    }
    public void clerar(){
        name_use_afterlogin="ВЫ НЕ ПОДКЛЮЧЕНЫ!";
        pass="";
        mail="";
        name_for_log="";
    }




}
