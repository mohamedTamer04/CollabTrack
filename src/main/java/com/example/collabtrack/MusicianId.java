package com.example.collabtrack;

public class MusicianId {
    private static int musicianId = -1;

    public int getMusicianId(int i) {
        return musicianId+i;
    }
}