package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MusicianHomePage {

    @FXML
    private Button SignUp;

    @FXML
    private void handleSignUpButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicianSignUpPage.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788);
            newStage.setTitle("CollabTrack");
            newStage.setScene(scene);
            newStage.setResizable(false);

            newStage.show();

            MusicianSignUpPage musicianSignUpPage = loader.getController();
            Stage currentStage = (Stage) SignUp.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogInButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicianLoginPage.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788);
            newStage.setTitle("CollabTrack");
            newStage.setScene(scene);
            newStage.setResizable(false);

            newStage.show();

            MusicianLoginPage musicianLoginPage = loader.getController();
            Stage currentStage = (Stage) SignUp.getScene().getWindow();
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

            newStage.show();

            HelloController helloController = loader.getController();
            Stage currentStage = (Stage) SignUp.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}