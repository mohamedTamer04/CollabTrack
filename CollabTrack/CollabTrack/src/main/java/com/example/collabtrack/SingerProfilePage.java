package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingerProfilePage {

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private Label bioLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label usernameLabel;

    // Method to initialize data with logged-in singer's information
    public void initData(Singer loggedInSinger) {
        // Set the labels with singer's information
        nameLabel.setText(loggedInSinger.getName());
        emailLabel.setText(loggedInSinger.getEmail());
        phoneLabel.setText(loggedInSinger.getPhoneNum());
        genreLabel.setText(loggedInSinger.getGenre());
        languageLabel.setText(loggedInSinger.getLanguage());
        bioLabel.setText(loggedInSinger.getBio());
        genderLabel.setText(loggedInSinger.getGender());
        usernameLabel.setText(loggedInSinger.getUsername());
    }

    public void navigateToHome(ActionEvent actionEvent) {
    }

    public void navigateToProfile(ActionEvent actionEvent) {
    }
}
