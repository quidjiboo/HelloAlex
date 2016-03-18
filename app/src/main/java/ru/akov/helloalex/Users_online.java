package ru.akov.helloalex;

/**
 * Created by User on 10.02.2016.
 */
public class Users_online {
    private String name;
    private String text;

    public Users_online() {
        // necessary for Firebase's deserializer
    }
    public Users_online(String name, String text) {
        this.name = name;
        this.text = text;

    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
