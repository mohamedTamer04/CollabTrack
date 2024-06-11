package com.example.collabtrack;

public class Singer extends User{
    private int singerId;
    private SingerId id = new SingerId();
    private static int counter=0;
    public Singer(String name, String email, String phoneNum, String genre, String language, String gender, String bio, String username, String password) {
        super(name, email, phoneNum, genre, language, gender, bio, username, password);
        counter++;
        singerId = id.getSingerId(counter);
    }

    public static void resetCounter() {
        Singer.counter = 0;
    }

    public int getSingerId() {
        return singerId;
    }
}
