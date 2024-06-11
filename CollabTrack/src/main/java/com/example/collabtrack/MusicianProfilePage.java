package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MusicianProfilePage {
    private Musician loggedInUser;

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

    public void setLoggedInUser(Musician loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    // Method to initialize data with logged-in singer's information
    public void initData(Musician loggedInMusician) {
        // Set the labels with singer's information
        nameLabel.setText(loggedInMusician.getName());
        emailLabel.setText(loggedInMusician.getEmail());
        phoneLabel.setText(loggedInMusician.getPhoneNum());
        genreLabel.setText(loggedInMusician.getGenre());
        languageLabel.setText(loggedInMusician.getLanguage());
        instrumentLabel.setText(loggedInMusician.getInstrument());
        bioLabel.setText(loggedInMusician.getBio());
        genderLabel.setText(loggedInMusician.getGender());
        usernameLabel.setText(loggedInMusician.getUsername());
    }

    public void showSingersList(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("singersList.fxml"));
            Parent root = loader.load();
            SingersList controller = loader.getController();
            controller.setLoggedInUser(loggedInUser);
            Scene scene = new Scene(root, 1400, 788);
            // Create a new stage
            Stage newStage = new Stage();

            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.setScene(scene);
            newStage.setTitle("Singers List");
            newStage.show();
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Close the current stage
            currentStage.close();


        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the singers list.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void openMessages(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("musicianMessagePage.fxml"));
            Parent root = loader.load();
            MusicianMessagePage controller = loader.getController();
            controller.setLoggedInUser(loggedInUser);
            Scene scene = new Scene(root, 1400, 788);
            // Create a new stage
            Stage newStage = new Stage();

            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.setScene(scene);
            newStage.setTitle("Dm");
            newStage.show();
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Close the current stage
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml")); // Adjust the resource name accordingly
            Parent root = loader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788);
            newStage.setTitle("CollabTrack");
            newStage.setScene(scene);
            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.show();

            HelloController helloController = loader.getController();
            helloController.init();
            Stage currentStage = (Stage) nameLabel.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
