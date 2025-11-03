package org.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.demo.ui.GamePanel;

import java.io.IOException;

public class HelloApplication extends Application {
    private GamePanel gamePanel;

    @Override
    public void start(Stage stage) throws IOException {
        gamePanel = new GamePanel();
        Scene scene = new Scene(gamePanel, 1000, 700);
        stage.setTitle("Idle Clicker RPG - Die & Retry");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> gamePanel.stop());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
