// HelloController.java
package com.example.collabtrack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Pane singerPane;

    @FXML
    private Pane musicianPane;

    @FXML
    private Pane studioPane;

    @FXML
    private Button singerButton;

    @FXML
    private Button musicianButton;

    @FXML
    private Button studioButton;

    boolean show = false;
    public void init() {

        if (singerPane == null || musicianPane == null || studioPane == null) {
            System.err.println("FXMLLoader did not inject the Pane objects properly.");
        }
        studioButton.hoverProperty().addListener(l->{
            if (show == false){
                handleStudioButtonClick();
                show = true;
            } else {
                hidePanes();
                show = false;
            }
        });


        singerButton.hoverProperty().addListener(l->{
            if (show == false){
                handleSingerButtonClick();
                show = true;
            } else {
                hidePanes();
                show = false;
            }
        });

        musicianButton.hoverProperty().addListener(l->{
            if (show == false){
                handleMusicianButtonClick();
                show = true;
            } else {
                hidePanes();
                show = false;
            }
        });
    }

    @FXML
    public void hidePanes() {
        singerPane.setVisible(false);
        musicianPane.setVisible(false);
        studioPane.setVisible(false);
    }
    @FXML
    public void handleSingerButtonClick() {
        singerPane.setVisible(true);
        musicianPane.setVisible(false);
        studioPane.setVisible(false);
    }

    @FXML
    public void handleMusicianButtonClick() {
        singerPane.setVisible(false);
        musicianPane.setVisible(true);
        studioPane.setVisible(false);
    }

    @FXML
    public void handleStudioButtonClick() {
        singerPane.setVisible(false);
        musicianPane.setVisible(false);
        studioPane.setVisible(true);
    }

    @FXML
    public void loadSingerHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingerHomePage.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788);
            newStage.setTitle("CollabTrack");
            newStage.setScene(scene);
            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.show();

            SingerHomePage singerHomeController = loader.getController();
            Stage currentStage = (Stage) singerButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadMusicianHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicianHomePage.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root, 1400, 788);
            newStage.setTitle("CollabTrack");
            newStage.setScene(scene);
            newStage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            newStage.getIcons().add(icon);
            newStage.show();

            MusicianHomePage singerHomeController = loader.getController();
            Stage currentStage = (Stage) singerButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
