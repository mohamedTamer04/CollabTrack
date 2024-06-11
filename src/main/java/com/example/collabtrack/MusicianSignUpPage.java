package com.example.collabtrack;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MusicianSignUpPage {

    public Button signupButton;
    @FXML
    private GridPane gridPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> genreComboBox;

    @FXML
    private ComboBox<String> instrumentComboBox;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private TextArea bioTextArea;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField usernameField; // Add this line

    @FXML
    private PasswordField passwordField; // Add this line

    @FXML
    private void initialize() {
        // Initialize your scene here
    }

    @FXML
    private void signUp() {
        if (validateData()) {
            saveToFile();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Sign Up Successful");
        }
    }

    private boolean validateData() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNumber = phoneField.getText().trim();
        String genre = genreComboBox.getValue();
        String instrument = instrumentComboBox.getValue();
        String language = languageComboBox.getValue();
        String bio = bioTextArea.getText().trim();
        String gender = genderComboBox.getValue();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || genre == null || language == null || bio.isEmpty() || gender == null || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return false;
        }

        if (name.contains("-") || email.contains("-") || phoneNumber.contains("-") || bio.contains("-") || username.contains("-") || password.contains("-")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Input fields should not contain a dash (-).");
            return false;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid email address.");
            return false;
        }
        if (!phoneNumber.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Phone number should contain digits only.");
            return false;
        }

        if (password.length()<8){
            showAlert(Alert.AlertType.ERROR, "Error", "Password is too short.");
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password should contain at least one lowercase letter.");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password should contain at least one uppercase letter.");
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password should contain at least one number.");
            return false;
        }

        if (!password.matches(".*[!@#$%^&*()+=_].*")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password should contain at least one special character.");
            return false;
        }


        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void saveToFile() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNumber = phoneField.getText().trim();
        String genre = genreComboBox.getValue();
        String instrument = instrumentComboBox.getValue();
        String language = languageComboBox.getValue();
        String bio = bioTextArea.getText().trim();
        String gender = genderComboBox.getValue();
        String username = usernameField.getText().trim(); // Get username
        String password = passwordField.getText().trim(); // Get password

        String data = name + "-" + email + "-" + phoneNumber + "-" + genre + "-" + language + "-" + gender + "-" + bio + "-" + username + "-" + password + "\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("signup_data.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save data to file.");
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
            // Load the initial page (replace "SingerHomePage.fxml" with your actual FXML file for the initial page)
            Parent root = FXMLLoader.load(getClass().getResource("MusicianHomePage.fxml"));
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

