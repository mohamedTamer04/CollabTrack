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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SingersList implements Initializable {
    private boolean isSet = false;
    int loggedInUserId;
    private Musician loggedInUser;
    @FXML
    private Label singer1;

    @FXML
    private Label singer2;

    @FXML
    private Label singer3;

    @FXML
    private Label singer4;

    @FXML
    private Label singer5;

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

    public void setLoggedInUser(Musician loggedInUser) {
        this.loggedInUser = loggedInUser;
        loggedInUserId = loggedInUser.getMusicianId();
        if (!isSet){
            updateLabels();
            isSet = true;
        }
    }

    private ArrayList<Button> buttonList = new ArrayList<>();
    private ArrayList<Label> labelList = new ArrayList<>();
    private ArrayList<Singer> singers = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonList.add(follow1);
        buttonList.add(follow2);
        buttonList.add(follow3);
        buttonList.add(follow4);
        buttonList.add(follow5);
        labelList.add(singer1);
        labelList.add(singer2);
        labelList.add(singer3);
        labelList.add(singer4);
        labelList.add(singer5);
        databaseHandler = new DatabaseHandler();
        if (isSet)
        updateLabels();
    }

    public void updateLabels() {
        singers = databaseHandler.getAllSingers();
        try {
            List<Singer> filteredSingers = new ArrayList<>();
            for (Singer singer : singers) {
                if (!databaseHandler.isAlreadyFollowing(loggedInUserId, singer.getSingerId())) {
                    filteredSingers.add(singer);
                }
            }

            for (int i = 0; i < labelList.size(); i++) {
                if (i < filteredSingers.size()) {
                    labelList.get(i).setText(filteredSingers.get(i).getUsername());
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
            String singerUsername = labelList.get(index).getText();

            int followedUserId = getSingerIdByUsername(singerUsername);
            if (followedUserId != -1) {
                if (databaseHandler.isAlreadyFollowing(loggedInUserId, followedUserId)) {
                    showErrorAlert("Already Following", "You are already following this singer.");
                } else {
                    databaseHandler.saveFollowing(loggedInUserId, followedUserId);
                    showAlert("Success", "You are now following " + singerUsername + ".");
                    updateLabelAfterFollowing();
                }
            } else {
                showErrorAlert("Error", "Singer not found.");
            }
        }
    }


    private int getSingerIdByUsername(String username) {
        for (Singer singer : singers) {
            if (singer.getUsername().equals(username)) {
                return singer.getSingerId(); // Assuming `Singer` has a method `getId()`
            }
        }
        return -1; // User not found
    }

    private boolean isAlreadyFollowing(int userId, int followedUserId) {
        List<String> followings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("musicianFollowings.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                followings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while reading the followings file.");
        }

        String followingRecord = userId + "-" + followedUserId;
        return followings.contains(followingRecord);
    }

    private void saveFollowing(int userId, int followedUserId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("musicianFollowings.txt", true))) {
            writer.write(userId + "-" + followedUserId);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while saving the following information.");
        }
    }

    public void navigateToProfile(ActionEvent event){
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicianProfilePage.fxml"));
        Parent root = loader.load();

        // Pass the logged-in musician's data to the profile page controller
        MusicianProfilePage controller = loader.getController();
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

    private void updateLabelAfterFollowing() {

        // Update labels and buttons
        updateLabels();
    }
}
