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

    private Accont_info_my_sington(){
        name_for_log="";
        pass="";
        mail="";
        name_use_afterlogin="new instane";
    }

    public static synchronized Accont_info_my_sington getInstance() {
        if(instance==null){
            instance = new Accont_info_my_sington();
        }
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
