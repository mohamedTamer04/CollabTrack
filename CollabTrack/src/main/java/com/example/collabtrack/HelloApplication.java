// HelloApplication.java
package com.example.collabtrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();

            HelloController controller = fxmlLoader.getController();
            controller.init();

            Scene scene = new Scene(root, 1400, 788);
            stage.setTitle("CollabTrack");
            stage.setScene(scene);
            stage.setResizable(false);
            Image icon = new Image("com/example/collabtrack/CollabTrack.png");
            stage.getIcons().add(icon);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
