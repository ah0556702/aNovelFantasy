package org.example.anovelfantasy;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;

public class GameOver {

    @FXML
    private Label scoreLabel;

    public void setScore(Label score) {
        scoreLabel.setText("Score: " + score);
    }
    public GameOver(int score) {
    }

    @FXML
    private Text scoreTxt;

    public GameOver() {
        // This constructor is needed by FXMLLoader
    }

//    public GameOver(Label score){
//        this.score = score;
//    }
    public static void show(Scene current) {
        try{
            FXMLLoader loader = new FXMLLoader(Game.class.getResource("gameOver.fxml"));
            Parent root = loader.load();
            current.setRoot(root);
        } catch( IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private Button playAgainBtn;

    @FXML
    private void handlePlayAgain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loading.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) playAgainBtn.getScene().getWindow();

            // Create a new scene with the start screen
            Scene scene = new Scene(root);

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    Button exitBtn;

    @FXML
    private void exitGame(){
        Platform.exit();
    }

}
