package org.example.anovelfantasy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        ((AnchorPane) root).setPrefSize(850, 600);

        // scene = new Scene(fxmlLoader.load(), 350, 575);
        // scene.getStylesheets().add("/styles.css");

        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("A Novel Fantasy");
        stage.show();

        StartScreen startScreen = fxmlLoader.getController();
        startScreen.setScene(scene);

        Font.loadFont(getClass().getResourceAsStream("/fonts/VictorianFont.ttf"), 24);
    }

    public static void main(String[] args) {
        launch();
    }
}