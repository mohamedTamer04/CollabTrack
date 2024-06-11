package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MusicianList implements Initializable {
    private boolean isSet = false;
    private int loggedInUserId;
    private Singer loggedInUser; // Assuming Singer can follow musicians
    @FXML
    private Label musician1;

    @FXML
    private Label musician2;

    @FXML
    private Label musician3;

    @FXML
    private Label musician4;

    @FXML
    private Label musician5;

    @FXML
    private Button follow1;

    @FXML
    private Button follow2;

    @FXML
    private Button follow3;

    @FXML
    private Button follow4;

    @FXML
    private Button follow5;

    public void setLoggedInUser(Singer loggedInUser) {
        this.loggedInUser = loggedInUser;
        loggedInUserId = loggedInUser.getSingerId();
        if(!isSet){
            updateLabels();
            isSet = true;
        }
    }

    private ArrayList<Button> buttonList = new ArrayList<>();
    private ArrayList<Label> labelList = new ArrayList<>();
    private ArrayList<Musician> musicians = new ArrayList<>();
    private DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonList.add(follow1);
        buttonList.add(follow2);
        buttonList.add(follow3);
        buttonList.add(follow4);
        buttonList.add(follow5);
        labelList.add(musician1);
        labelList.add(musician2);
        labelList.add(musician3);
        labelList.add(musician4);
        labelList.add(musician5);
        databaseHandler = new DatabaseHandler();
        if (isSet)
        updateLabels();
    }

    public void updateLabels() {
        musicians = databaseHandler.getAllMusicians();
        try {
            List<Musician> filteredMusicians = new ArrayList<>();
            for (Musician musician : musicians) {
                if (!databaseHandler.isAlreadyFollowingSinger(loggedInUserId, musician.getMusicianId())) {
                    filteredMusicians.add(musician);
                }
            }

            for (int i = 0; i < labelList.size(); i++) {
                if (i < filteredMusicians.size()) {
                    labelList.get(i).setText(filteredMusicians.get(i).getUsername());
                    labelList.get(i).setVisible(true);
                    buttonList.get(i).setVisible(true);
                } else {
                    labelList.get(i).setVisible(false);
                    buttonList.get(i).setVisible(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while updating the labels: " + e.getMessage());
        }
    }

    @FXML
    private void handleFollowAction(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        int index = buttonList.indexOf(sourceButton);

        if (index != -1 && index < labelList.size()) {
            String musicianUsername = labelList.get(index).getText();

            int followedUserId = getMusicianIdByUsername(musicianUsername);
            if (followedUserId != -1) {
                if (databaseHandler.isAlreadyFollowingSinger(loggedInUserId, followedUserId)) {
                    showErrorAlert("Already Following", "You are already following this musician.");
                } else {
                    databaseHandler.saveFollowingSinger(loggedInUserId, followedUserId);
                    showAlert("Success", "You are now following " + musicianUsername + ".");
                    updateLabelAfterFollowing();
                }
            } else {
                showErrorAlert("Error", "Musician not found.");
            }
        }
    }

    private int getMusicianIdByUsername(String username) {
        for (Musician musician : musicians) {
            if (musician.getUsername().equals(username)) {
                return musician.getMusicianId();
            }
        }
        return -1; // User not found
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void navigateToProfile(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingerProfilePage.fxml"));
            Parent root = loader.load();

            // Pass the logged-in musician's data to the profile page controller
            SingerProfilePage controller = loader.getController();
            controller.initData(loggedInUser);
            controller.setLoggedInUser(loggedInUser);
            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788);
            newStage.setTitle("CollabTrack");
            newStage.setScene(scene);
            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.show();
            Stage currentStage = (Stage) follow1.getScene().getWindow(); // Assuming usernameField is part of the current scene
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateLabelAfterFollowing() {
        updateLabels();
    }
}
