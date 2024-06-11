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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MusicianLoginPage {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Musician loggedInMusician; // Store the logged-in singer

    public void login(ActionEvent actionEvent) {
        String username = usernameField.getText(); // Get username from TextField
        String password = passwordField.getText(); // Get password from PasswordField

        // Create an array list to store singers from the signup file
        ArrayList<Musician> musicians = new ArrayList<>();

        // Read singers from the signup file
        try (BufferedReader br = new BufferedReader(new FileReader("signup_data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into individual fields
                String[] fields = line.split("-");

                // Check if the line has enough fields
                if (fields.length >= 9) {
                    // Create a new singer object with the fields from the signup file
                    Musician musician = new Musician(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8], fields[9]);

                    // Add the singer to the array list
                    musicians.add(musician);
                } else {
                    // Log a warning if the line doesn't have enough fields
                    System.out.println("Invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Display an alert if there's an error reading the signup file
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Reading Signup File");
            alert.setContentText("An error occurred while reading the signup file.");
            alert.showAndWait();
        }

        // Check if the input username and password match with any singer's credentials
        boolean loggedIn = false;
        for (Musician musician : musicians) {
            if (musician.getUsername().equals(username) && musician.getPassword().equals(password)) {
                loggedIn = true;
                // Store the logged-in singer
                loggedInMusician = musician;
                // Perform actions for successful login, such as opening a new window or setting a flag
                break; // No need to continue checking once logged in
            }
        }

        if (loggedIn) {
            // Load the profile page with logged-in singer's data
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicianProfilePage.fxml"));
                Parent root = loader.load();

                // Pass the logged-in singer's data to the profile page controller
                MusicianProfilePage controller = loader.getController();
                controller.initData(loggedInMusician);

                Stage newStage = new Stage();
                Scene scene = new Scene(root, 1400, 788);
                newStage.setTitle("CollabTrack");
                newStage.setScene(scene);
                newStage.setResizable(false);

                newStage.show();
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
