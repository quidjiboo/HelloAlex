package ru.akov.helloalex;

/**
 * Created by User on 10.02.2016.
 */
public class ChatmessAlex {
    private String author;
    private String message;
    public ChatmessAlex(){

    }
    public ChatmessAlex(String author, String message){
        this.message = message;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
