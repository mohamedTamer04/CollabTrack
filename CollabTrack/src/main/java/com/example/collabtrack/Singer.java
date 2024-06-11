package com.example.collabtrack;

public class Singer extends User{
    private int singerId;

    public Singer(String name, String email, String phoneNum, String genre, String language, String gender, String bio, String username, String password, int singerId) {
        super(name, email, phoneNum, genre, language, gender, bio, username, password);
        this.singerId = singerId;
    }

    public int getSingerId() {
        return singerId;
    }
}
