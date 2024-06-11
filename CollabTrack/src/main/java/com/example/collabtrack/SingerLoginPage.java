package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SingerLoginPage {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    private Singer loggedInSinger;

    private final DatabaseHandler databaseHandler = new DatabaseHandler(); // Initialize DatabaseHandler

    public void login(ActionEvent actionEvent) {
        String username = usernameField.getText(); // Get username from TextField
        String password = passwordField.getText(); // Get password from PasswordField

        loggedInSinger = databaseHandler.loginSinger(username, password); // Authenticate login
        System.out.println(loggedInSinger.getSingerId());
        if (loggedInSinger != null) {
            // Load the profile page with logged-in singer's data
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SingerProfilePage.fxml"));
                Parent root = loader.load();

                // Pass the logged-in singer's data to the profile page controller
                SingerProfilePage controller = loader.getController();
                controller.initData(loggedInSinger);
                controller.setLoggedInUser(loggedInSinger);

                Stage newStage = new Stage();
                Scene scene = new Scene(root, 1400, 788);
                newStage.setTitle("CollabTrack");
                newStage.setScene(scene);
                newStage.setResizable(false);

                newStage.show();
                Stage currentStage = (Stage) usernameField.getScene().getWindow(); // Assuming singerButton is part of the current scene
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText("Login Successful");
            alert.setContentText("Loading Profile Page.");
            alert.showAndWait();
        } else {
            // Display an alert for unsuccessful login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Username or Password");
            alert.setContentText("Please check your username and password and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            // Load the initial page (replace "SingerHomePage.fxml" with your actual FXML file for the initial page)
            Parent root = FXMLLoader.load(getClass().getResource("SingerHomePage.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow(); // Get the current stage
            Scene scene = new Scene(root, 1400, 788); // Set the scene dimensions to match the initial settings
            stage.setTitle("CollabTrack");
            stage.setScene(scene);
            stage.setResizable(false); // Ensure the stage is not resizable
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
