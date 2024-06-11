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

import java.io.IOException;

public class SingerProfilePage {

    private Singer loggedInUser;

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

    public void setLoggedInUser(Singer loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

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

    @FXML
    private void showMusicianList(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("musiciansList.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            MusicianList controller = loader.getController();
            controller.setLoggedInUser(loggedInUser);

            // Create a new stage
            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788); // Set scene size here
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.setTitle("Musician List");
            newStage.show();

            // Close the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the musician list.");
        }
    }

    @FXML
    public void openMessagePage(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("singerMessagePage.fxml"));
            Parent root = loader.load();

            SingerMessagePage controller = loader.getController();
            System.out.println(loggedInUser.getSingerId());
            controller.setLoggedInUser(loggedInUser);

            // Create a new stage
            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788); // Set scene size here
            newStage.setScene(scene);
            newStage.setTitle("Messaging Page");
            newStage.show();
            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            // Close the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the message page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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


