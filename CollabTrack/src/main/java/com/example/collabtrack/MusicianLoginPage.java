package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MusicianLoginPage {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Musician loggedInMusician; // Store the logged-in musician

    private DatabaseHandler databaseHandler;

    @FXML
    private void initialize() {
        databaseHandler = new DatabaseHandler(); // Instantiate DatabaseHandler
    }

    @FXML
    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        loggedInMusician = databaseHandler.loginMusician(username, password);

        if (loggedInMusician != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicianProfilePage.fxml"));
                Parent root = loader.load();

                // Pass the logged-in musician's data to the profile page controller
                MusicianProfilePage controller = loader.getController();
                controller.initData(loggedInMusician);
                controller.setLoggedInUser(loggedInMusician);
                Stage newStage = new Stage();
                Scene scene = new Scene(root, 1400, 788);
                newStage.setTitle("CollabTrack");
                newStage.setScene(scene);
                newStage.setResizable(false);
                Image icon = new Image("com/example/collabtrack/CollabTrack.png");
                newStage.getIcons().add(icon);
                newStage.show();
                Stage currentStage = (Stage) usernameField.getScene().getWindow(); // Assuming usernameField is part of the current scene
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showAlert(Alert.AlertType.INFORMATION, "Success", "Login Successful");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Username or Password");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MusicianHomePage.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow(); // Get the current stage
            Scene scene = new Scene(root, 1400, 788); // Set the scene dimensions to match the initial settings
            stage.setTitle("CollabTrack");
            stage.setScene(scene);
            stage.setResizable(false); // Ensure the stage is not resizable
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            stage.getIcons().add(icon);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
