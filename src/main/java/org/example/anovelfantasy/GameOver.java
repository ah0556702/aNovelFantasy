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

            Stage stage = (Stage) playAgainBtn.getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);

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
