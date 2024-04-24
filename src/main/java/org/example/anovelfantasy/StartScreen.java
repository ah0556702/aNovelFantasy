package org.example.anovelfantasy;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import org.example.anovelfantasy.Game;

public class StartScreen{
    private Scene current;

    @FXML
    public void setScene(Scene currentScene) {
        this.current = currentScene;
    }

    @FXML
    private void playBtnClick() {
        if (current != null) {
            // Load the Game scene
            Loading.show(current);
        } else {
            System.err.println("Current scene is null");
        }
    }
}
