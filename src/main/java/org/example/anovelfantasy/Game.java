package org.example.anovelfantasy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    public static void show(Scene current) {
        try{

            FXMLLoader loader = new FXMLLoader(Game.class.getResource("game.fxml"));
            Parent root = loader.load();
            current.setRoot(root);
        } catch( IOException e){
            e.printStackTrace();
        }
    }
    private final BooksAPI apiService = new BooksAPI();
    @FXML
    public TextArea testing;

    @FXML
    private void fetchData(){
        try{
            String apiResponse = BooksAPI.fetchApiData("https://api.nytimes.com/svc/books/v3/lists/full-overview.json?api-key=c3amEnaHm0AqXLz4ejrGL5jGRIeyVygF");
            String jsonData = new String(apiResponse);
            testing.setText(jsonData);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public GridPane gridPane;

    @FXML
    private void initialize() {
        // Load image paths
        String[] imgPaths = {
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\blueGreenR.png",
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\brownR.png",
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\darkBlueR.png",
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\greyR.png",
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\maroonR.png",
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\orangeR.png",
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\purpleR.png",
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\royalBlueR.png",
                "C:\\Users\\snide\\OneDrive - Ozarks Technical Community College\\Java 2\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\silverPurpleR.png"
        };

        // Create a list to store Image objects
        List<Image> images = new ArrayList<>();

        // Load each image into the list
        for (String path : imgPaths) {
            URI uri = new File(path).toURI();
            images.add(new Image(uri.toString()));
        }

        // Shuffle the images
        Collections.shuffle(images);

        int imageIndex = 0;
        int numRows = gridPane.getRowConstraints().size();
        int numCols = gridPane.getColumnConstraints().size();

// Calculate the width and height of each grid slot
        double slotWidth = gridPane.getWidth() / numCols;
        double slotHeight = gridPane.getHeight() / numRows;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (imageIndex < images.size()) {
                    // Create ImageView and set its fitWidth and fitHeight
                    ImageView imageView = new ImageView(images.get(imageIndex));
                    resizeImage(imageView, slotWidth, slotHeight);

                    // Add ImageView to the grid pane
                    gridPane.add(imageView, col, row);
                    imageIndex++;
                } else {
                    // If there are no more images, break out of the loop
                    break;
                }
            }
        }


    }

    // Method to resize an image to fit inside the grid slot
    private void resizeImage(ImageView imageView, double slotWidth, double slotHeight) {
        // Get the original dimensions of the image
        double originalWidth = imageView.getImage().getWidth();
        double originalHeight = imageView.getImage().getHeight();

        // Calculate the scale factors for width and height to fit inside the slot
        double widthScaleFactor = slotWidth / originalWidth;
        double heightScaleFactor = slotHeight / originalHeight;

        // Choose the smaller scale factor to maintain aspect ratio
        double scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);

        // Calculate the new dimensions for the image
        double fitWidth = originalWidth * scaleFactor;
        double fitHeight = originalHeight * scaleFactor;

        // Set the fitWidth and fitHeight properties to resize the image
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
    }

}
