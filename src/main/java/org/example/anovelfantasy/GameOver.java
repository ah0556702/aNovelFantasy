package org.example.anovelfantasy;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GameOver {
    public static void show(Scene current) {
        try{
            FXMLLoader loader = new FXMLLoader(Game.class.getResource("gameOver.fxml"));
            Parent root = loader.load();
            current.setRoot(root);
        } catch( IOException e){
            e.printStackTrace();
        }
    }
}
