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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SingerMessagePage implements Initializable {
    private int index;
    private int loggedInUserId;
    private Singer loggedInUser; // Assuming Singer can follow musicians

    private boolean isSet = false;
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
    private Button message1;

    @FXML
    private Button message2;

    @FXML
    private Button message3;

    @FXML
    private Button message4;

    @FXML
    private Button message5;

    @FXML
    private Button remove1;

    @FXML
    private Button remove2;

    @FXML
    private Button remove3;

    @FXML
    private Button remove4;

    @FXML
    private Button remove5;

    @FXML
    private Label messagetext;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button confirmMessage;

    private ArrayList<Label> musicianLabels;
    private ArrayList<Button> messageButtons;
    private ArrayList<Button> removeButtons;
    private ArrayList<Integer> removedMusicianIds;
    private DatabaseHandler databaseHandler;

    public void setLoggedInUser(Singer loggedInUser) {
        this.loggedInUser = loggedInUser;
        loggedInUserId = loggedInUser.getSingerId();
        if (!isSet){
            updateLabels();
            isSet = true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        musicianLabels = new ArrayList<>(Arrays.asList(musician1, musician2, musician3, musician4, musician5));
        messageButtons = new ArrayList<>(Arrays.asList(message1, message2, message3, message4, message5));
        removeButtons = new ArrayList<>(Arrays.asList(remove1, remove2, remove3, remove4, remove5));
        removedMusicianIds = new ArrayList<>();
        databaseHandler = new DatabaseHandler();
        if (isSet)
        updateLabels();
    }

    private void updateLabels() {
        List<Musician> musicians = databaseHandler.getSingerFollowings(loggedInUserId);
        try {
            List<Musician> filteredMusicians = new ArrayList<>();
            for (Musician musician : musicians) {
                if (!removedMusicianIds.contains(musician.getMusicianId())) {
                    filteredMusicians.add(musician);
                }
            }

            for (int i = 0; i < musicianLabels.size(); i++) {
                if (i < filteredMusicians.size()) {
                    musicianLabels.get(i).setText(filteredMusicians.get(i).getUsername());
                    musicianLabels.get(i).setVisible(true);
                    messageButtons.get(i).setVisible(true);
                    removeButtons.get(i).setVisible(true);
                } else {
                    musicianLabels.get(i).setVisible(false);
                    messageButtons.get(i).setVisible(false);
                    removeButtons.get(i).setVisible(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while updating the labels: " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveAction(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        int index = removeButtons.indexOf(sourceButton);

        if (index != -1 && index < musicianLabels.size()) {
            String musicianUsername = musicianLabels.get(index).getText();

            int removedMusicianId = getMusicianIdByUsername(musicianUsername);
            if (removedMusicianId != -1) {
                removedMusicianIds.add(removedMusicianId);
                showAlert("Removed", "Musician " + musicianUsername + " has been removed.");
                updateLabels();
            } else {
                showErrorAlert("Error", "Musician not found.");
            }
        }
    }

    @FXML
    private void handleMessageAction(ActionEvent event) {
        messageArea.setVisible(true);
        messagetext.setVisible(true);
        confirmMessage.setVisible(true);
        confirmMessage.setText("Send");
        Button sourceButton = (Button) event.getSource();
        index = messageButtons.indexOf(sourceButton);
    }

    @FXML
    private void sendMessage(ActionEvent event){
        confirmMessage.setText("Sent");
        System.out.println(index);

        if (index != -1 && index < musicianLabels.size()) {
            System.out.println(getMusicianIdByUsername(musicianLabels.get(index).getText()) + "   " + messageArea.getText());
            databaseHandler.insertMessage(getMusicianIdByUsername(musicianLabels.get(index).getText()),messageArea.getText(),loggedInUserId);
        }
        messageArea.setText("");
    }

    private int getMusicianIdByUsername(String username) {
        for (Musician musician : databaseHandler.getAllMusicians()) {
            if (musician.getUsername().equals(username)) {
                return musician.getMusicianId();
            }
        }
        return -1;
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

    public void handleBackButton(ActionEvent event){
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
            Stage currentStage = (Stage) message2.getScene().getWindow(); // Assuming usernameField is part of the current scene
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
