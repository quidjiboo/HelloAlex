package ru.akov.helloalex;

/**
 * Created by User on 10.02.2016.
 */
public class ChatmessAlex {
    private String name;
    private String text;

    public ChatmessAlex() {
        // necessary for Firebase's deserializer
    }
    public ChatmessAlex(String name, String text) {
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
