package com.example.collabtrack;

public class Musician extends User{
    private int musicianId;
    private MusicianId id = new MusicianId();
    private String instrument;
    private static int counter=0;
    public Musician(String name, String email, String phoneNum, String genre, String language, String gender, String bio, String username, String password,String instrument) {
        super(name, email, phoneNum, genre, language, gender, bio, username, password);
        this.instrument = instrument;
        counter++;
        musicianId = id.getMusicianId(counter);
    }

    public static void resetCounter() {
        Musician.counter = 0;
    }

    public int getSingerId() {
        return musicianId;
    }

    public String getInstrument() {
        return instrument;
    }
}
