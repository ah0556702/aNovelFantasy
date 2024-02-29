package org.example.anovelfantasy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class Game {
    @FXML
    private GridPane gridPane;
    public static void show(Scene current) {
        try{
            FXMLLoader loader = new FXMLLoader(Game.class.getResource("game.fxml"));
            Parent root = loader.load();
            current.setRoot(root);
        } catch( IOException e){
            e.printStackTrace();
        }
    }

    public void initialize(){ // initiliazes the grid pane to show the books on the bookshelves
        int numRows = gridPane.getRowCount();
        int numCol = gridPane.getColumnCount();

        for(int i = 0; i < numRows; i++){ // counts against the num of rows
            if(i % 2 == 0){ // determines if they are even
                for(int j = 0; j < numCol; j++){ // inner for loop counts against the num of columns
                    Separator sep = new Separator(); // declare a new separator
                    sep.getStyleClass().add("separator");
                    gridPane.add(sep, j, i); // apply separator to each column on an even row
                    GridPane.setHgrow(sep, Priority.NEVER);
                    GridPane.setVgrow(sep, Priority.NEVER);

                    for(int k = 0; k < numCol; k++){
                        Separator separator = new Separator(Orientation.VERTICAL);
                        separator.getStyleClass().add("separator");
                        gridPane.add(separator, i, k);
                        GridPane.setHgrow(separator, Priority.NEVER);
                        GridPane.setVgrow(separator, Priority.NEVER);
                    }
                }
            } else {
                // add else statement to load books onto the shelf
            }
        }

    }
}
