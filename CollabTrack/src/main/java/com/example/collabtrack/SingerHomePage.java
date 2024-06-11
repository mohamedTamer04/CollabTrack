package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class SingerHomePage {

    @FXML
    private Button SignUp;

    @FXML
    private void handleSignUpButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingerSignUpPage.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788);
            newStage.setTitle("CollabTrack");
            newStage.setScene(scene);
            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.show();

            SingerSignUpPage singerSignUpPage = loader.getController();

            Stage currentStage = (Stage) SignUp.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogInButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingerLoginPage.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788);
            newStage.setTitle("CollabTrack");
            newStage.setScene(scene);
            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.show();

            SingerLoginPage singerLoginPage = loader.getController();
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
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.show();

            HelloController helloController = loader.getController();
            helloController.init();
            Stage currentStage = (Stage) SignUp.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

