package org.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.demo.ui.GamePanelEnhanced;

import java.io.IOException;

public class HelloApplication extends Application {
    private GamePanelEnhanced gamePanel;

    @Override
    public void start(Stage stage) throws IOException {
        gamePanel = new GamePanelEnhanced();
        Scene scene = new Scene(gamePanel, 1200, 800);
        stage.setTitle("🎮 Idle Clicker RPG - Die & Retry - ENHANCED EDITION");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> gamePanel.stop());
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
