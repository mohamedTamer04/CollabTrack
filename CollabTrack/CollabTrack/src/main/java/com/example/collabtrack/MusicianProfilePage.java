package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MusicianProfilePage {

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label instrumentLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private Label bioLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label usernameLabel;

    // Method to initialize data with logged-in singer's information
    public void initData(Musician loggedInMusician) {
        // Set the labels with singer's information
        nameLabel.setText(loggedInMusician.getName());
        emailLabel.setText(loggedInMusician.getEmail());
        phoneLabel.setText(loggedInMusician.getPhoneNum());
        genreLabel.setText(loggedInMusician.getGenre());
        languageLabel.setText(loggedInMusician.getInstrument());
        instrumentLabel.setText(loggedInMusician.getInstrument());
        bioLabel.setText(loggedInMusician.getBio());
        genderLabel.setText(loggedInMusician.getGender());
        usernameLabel.setText(loggedInMusician.getUsername());
    }

    public void navigateToHome(ActionEvent actionEvent) {
    }

    public void navigateToProfile(ActionEvent actionEvent) {
    }
}
