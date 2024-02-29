package org.example.anovelfantasy;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.example.anovelfantasy.Game;

public class StartScreen {
    private Scene current;
    //@FXML
    //private Button playBtn;

    @FXML
    public void setScene(Scene currentScene){ // lets the game start in the same window
        this.current = currentScene;
    }

    @FXML
    private void playBtnClick(){
        if(current != null){
            Game.show(current);
        } else {
            System.err.println("Current scene is null");
        }
    }
}
