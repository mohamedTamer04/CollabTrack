package com.example.collabtrack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MusicianMessagePage {
    private boolean isSet=false;
    private int loggedInUserId;
    private Musician loggedInUser;
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    @FXML
    private TextArea messageArea1;
    @FXML
    private TextArea messageArea2;
    @FXML
    private TextArea messageArea3;
    @FXML
    private TextArea messageArea4;
    @FXML
    private TextArea messageArea5;

    @FXML
    private Button sender1;
    @FXML
    private Button sender2;
    @FXML
    private Button sender3;
    @FXML
    private Button sender4;
    @FXML
    private Button sender5;

    @FXML
    private ArrayList<Button> senderButtons = new ArrayList<>();
    @FXML
    private ArrayList<TextArea> messageAreas = new ArrayList<>();

    public void setLoggedInUser(Musician loggedInUser) {
        this.loggedInUser = loggedInUser;
        loggedInUserId = loggedInUser.getMusicianId();
        if (!isSet){
            loadMessages();
            isSet = true;
        }
    }

    @FXML
    public void initialize() {
        // Add TextAreas to the list
        messageAreas.add(messageArea1);
        messageAreas.add(messageArea2);
        messageAreas.add(messageArea3);
        messageAreas.add(messageArea4);
        messageAreas.add(messageArea5);

        // Add Buttons to the list
        senderButtons.add(sender1);
        senderButtons.add(sender2);
        senderButtons.add(sender3);
        senderButtons.add(sender4);
        senderButtons.add(sender5);
        if (isSet)
        // Load messages
        loadMessages();
    }

    private void loadMessages() {
        ArrayList<String> messages = databaseHandler.getMusicianMessages(loggedInUserId);

        for (int i = 0; i < messages.size(); i++) {
            String[] parts = messages.get(i).split("\\|",2);
            if (parts.length == 2) {
                String message = parts[0];
                int senderId = Integer.parseInt(parts[1]);
                String senderUsername = databaseHandler.getSingerUsername(senderId);
                Button senderButton = senderButtons.get(i);
                senderButton.setText(senderUsername);
                senderButton.setVisible(true);
                TextArea messageArea = messageAreas.get(i);
                messageArea.setText(message);
                messageArea.setVisible(false); // Initially hide all messages
            }
        }
    }

    @FXML
    public void recieveMessage(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int index = senderButtons.indexOf(clickedButton);

        // Show the message associated with the clicked sender
        messageAreas.get(index).setVisible(true);
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
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
            Stage currentStage = (Stage) messageArea1.getScene().getWindow(); // Assuming usernameField is part of the current scene
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
