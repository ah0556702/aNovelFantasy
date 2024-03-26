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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.json.JSONArray;
import org.json.JSONObject;

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
    public GridPane gridPane;

    @FXML
    private TextArea testing;
    @FXML
    private void initialize() {
        updateTestingTextArea();
        // Load image paths
        String[] imgPaths = {
                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\blueGreenR.png",
        "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\brownR.png",
        "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\darkBlueR.png",
        "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\greyR.png",
        "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\maroonR.png",
        "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\orangeR.png",
        "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\purpleR.png",
        "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\royalBlueR.png",
        "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\silverPurpleR.png"
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
        int bookIndex = 0;
        int numRows = gridPane.getRowConstraints().size();
        int numCols = gridPane.getColumnConstraints().size();

        // API Call
        ArrayList<Books> booksList = Books.fetchBooksFromAPI("https://api.nytimes.com/svc/books/v3/lists/full-overview.json?api-key=c3amEnaHm0AqXLz4ejrGL5jGRIeyVygF");

// Calculate the width and height of each grid slot

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                imageIndex %= images.size();
                bookIndex %= booksList.size();

                ImageView imageView = new ImageView(images.get(imageIndex));
                setClickEvent(imageView, booksList.get(bookIndex));
                gridPane.add(imageView, col, row);

                imageIndex++;
                bookIndex++;
            }
        }
    }


    // Test function to update the testing text area with all titles from API
    private void updateTestingTextArea() {

        try {
            // Fetch popular books data from API
            String popularBooksData = BooksAPI.fetchClassicBooks();

            // Parse the JSON data to extract book titles
            JSONArray items = new JSONObject(popularBooksData).getJSONArray("items");
            StringBuilder titlesBuilder = new StringBuilder();

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                titlesBuilder.append(title).append("\n");
            }

            // Update the testing TextArea with book titles
            testing.setText(titlesBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    String[] bookImages = {
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\blueGreen.png",
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\brown.png",
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\darkBlue.png",
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\grey.png",
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\maroon.png",
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\orange.png",
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\purple.png",
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\royalBlue.png",
            "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\wholeBook\\silverPurple.png"
    };

    @FXML
    private ImageView wholeBookImg;
    @FXML
    private Label bookDetails;
    @FXML
    private Pane bookPane;
@FXML
    private Image getWholeImageForBook(Books book) {
        // Example: return new Image(book.getLargeImageUrl());
        // For now, just return a placeholder or an example image
        File file = new File(bookImages[0]);
        String imagePath = file.toURI().toString();
        return new Image(imagePath);
    }
@FXML
    private void setClickEvent(ImageView imageView, Books book){
        imageView.setOnMouseClicked(event -> {
            testing.setText(book.getTitle() + " " + book.getAuthor() + " " + book.getSummary());
        });

        Image wholeBook = getWholeImageForBook(book);
        wholeBookImg.setImage(wholeBook);

        String detailsTxt = String.format("%s\n\nAuthor: %s\n\nSummary:\n%s",
                book.getTitle(), book.getAuthor(), book.getSummary());
        bookDetails.setText(detailsTxt);

        bookPane.setVisible(true);
    }
}
