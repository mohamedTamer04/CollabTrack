package com.example.collabtrack;

public class SingerId {
    private static int singerId = -1;

    public int getSingerId(int i) {
        return singerId+i;
    }
}
