package com.example.collabtrack;

import java.util.ArrayList;

public class Musician extends User{
    private int musicianId;
    private String instrument;
    private ArrayList<String> messages = new ArrayList<>();
    public Musician(String name, String email, String phoneNum, String genre, String language, String gender, String bio, String username, String password,String instrument, int musicianId) {
        super(name, email, phoneNum, genre, language, gender, bio, username, password);
        this.instrument = instrument;
        this.musicianId = musicianId;
    }
    public int getMusicianId() {
        return musicianId;
    }

    public String getInstrument() {
        return instrument;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

}
