package org.example.anovelfantasy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class Loading {

    public static void show(Scene current) {
        try{
            FXMLLoader loader = new FXMLLoader(Loading.class.getResource("loading.fxml"));
            Parent root = loader.load();
            current.setRoot(root);
        } catch( IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private MediaView mediaView;

    @FXML
    public void initialize(){
        String mediaPath = "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\videos\\openBook.mp4";

        Media media = new Media(new File(mediaPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // needs to be fixed, not looping
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();

        PauseTransition pause = new PauseTransition(Duration.seconds(5)); // allows for the loading screen to be displayed for 3 seconds
        pause.setOnFinished(event -> {
            mediaPlayer.stop();
            Game.show(mediaView.getScene());
        });

        pause.play();
    }
}
