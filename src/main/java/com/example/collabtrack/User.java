package com.example.collabtrack;

public class User {
    private String name;
    private String email;
    private String phoneNum;
    private String genre;
    private String language;
    private String gender;
    private String bio;
    private String username;
    private String password;

    public User(String name, String email, String phoneNum, String genre, String language, String gender, String bio, String username, String password) {
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.genre = genre;
        this.language = language;
        this.gender = gender;
        this.bio = bio;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public String getGender() {
        return gender;
    }

    public String getBio() {
        return bio;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
