package org.example.anovelfantasy;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
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
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import java.util.Random;

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
    private Pane bookPane;

    @FXML
    private ImageView bookShelfBack;
    @FXML
    private void initialize() throws Exception {
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
        ArrayList<Books> booksList = new ArrayList<>(40);
        // Calculate the width and height of each grid slot
        String popularBooksData = BooksAPI.fetchClassicBooks();

        // Parse the JSON data to extract book titles
        JSONArray items = new JSONObject(popularBooksData).getJSONArray("items");
        StringBuilder titlesBuilder = new StringBuilder();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                imageIndex %= images.size();
                bookIndex %= items.length(); // Ensure the index wraps around the JSON array size

                JSONObject item = items.getJSONObject(bookIndex); // Get the JSONObject representing the book
                JSONObject volumeInfo = item.getJSONObject("volumeInfo"); // Get the "volumeInfo" JSONObject
                String title = volumeInfo.getString("title"); // Extract the book title

                // Assuming 'volumeInfo' is your JSONObject
                JSONArray authorsArray = volumeInfo.optJSONArray("authors"); // Safely get the authors array, will return null if not found
                StringBuilder authorsBuilder = new StringBuilder();

                if (authorsArray != null) {
                    for (int j = 0; j < authorsArray.length(); j++) {
                        // Extract each author and append to StringBuilder
                        String author = authorsArray.optString(j, "No Author");
                        authorsBuilder.append(author);
                        if (j < authorsArray.length() - 1) {
                            authorsBuilder.append(", ");
                        }
                    }
                } else {
                    authorsBuilder.append("Author information not available");
                }

                String authors = authorsBuilder.toString();


                ImageView imageView = new ImageView(images.get(imageIndex));
                setClickEvent(imageView, title, bookIndex, volumeInfo, authors);
                gridPane.add(imageView, col, row);

                Collections.shuffle(images); // shuffle array again

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


    @FXML
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
    private Image getWholeImageForBook(JSONObject book) {

        Random randNum = new Random(bookImages.length); // random number generated
        int num = randNum.nextInt(bookImages.length); // random number cast to int
        File file = new File(bookImages[num]); // picks an image from the bookImages array at random
        String imagePath = file.toURI().toString();
        return new Image(imagePath);
    }
@FXML
    private void setClickEvent(ImageView imageView, String title, int bookIndex, JSONObject book, String authors){
    String description = book.optString("description", "No description available.");
    currentBookTitle = title;
    imageView.setOnMouseClicked(event -> {
            testing.setText(book.getString("title") + " " + authors + " " + description);
            System.out.println(title);
            bookPane.setVisible(true);
            gridPane.setVisible(false);

            GaussianBlur gaussianBlur = new GaussianBlur();
            bookShelfBack.setEffect(gaussianBlur);
        });

        Image wholeBook = getWholeImageForBook(book);

    wholeBookImg.setImage(wholeBook);

    String detailsTxt = String.format("%s\n\nAuthor: %s\n\nSummary:\n%s",
            book.getString("title"), authors.toString(), description);
    bookDetails.setText(detailsTxt);


        bookPane.setVisible(false);
    }

    @FXML
    private TextField userText;

    private String currentBookTitle; // Assume this is set when a book is clicked
    private int guessCount = 0;

    @FXML
    private void userGuess() {
        String userInput = userText.getText();
        StringBuilder filteredInput = new StringBuilder();

        if (currentBookTitle == null) {
            System.out.println("No book selected or title not set");
            // Optionally clear the userText or inform the user to select a book first
            userText.setText("");
            return; // Exit the method early
        }
        // Increase guess count
        guessCount++;

        // Filter characters
        for (int i = 0; i < userInput.length(); i++) {
            char c = userInput.charAt(i);
            if (currentBookTitle.contains(String.valueOf(c))) {
                filteredInput.append(c);
            }
        }

        userText.setText(filteredInput.toString());

        // Check if the guess is correct or if the user has guessed three times
        if (userText.getText().equalsIgnoreCase(currentBookTitle) || guessCount >= 3) {
            bookPane.setVisible(false);
            gridPane.setVisible(true);
            bookShelfBack.setEffect(null); // Remove blur

            if (guessCount >= 3 && !userText.getText().equalsIgnoreCase(currentBookTitle)) {
                // Shake gridPane and fade random ImageViews
                shakeGridPane();
                fadeRandomImages();
            }

            // Reset for the next guess
            guessCount = 0;
            userText.setText("");
        }
    }

    private void shakeGridPane() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), gridPane);
        tt.setByX(5);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);

        tt.setOnFinished(event -> {
            // If you need to perform any action after shaking ends, you can do it here
        });

        tt.play();
    }


    private void fadeRandomImages() {
        Random rand = new Random();
        List<Node> children = new ArrayList<>(gridPane.getChildren()); // Get all children of the gridPane
        int numberOfImagesToFade = 3; // Adjust as needed, based on your UI design

        // Shuffle the list to randomize which images are selected
        Collections.shuffle(children, rand);

        for (int i = 0; i < Math.min(numberOfImagesToFade, children.size()); i++) {
            Node child = children.get(i);
            if (child instanceof ImageView) {
                FadeTransition ft = new FadeTransition(Duration.seconds(1), child);
                ft.setToValue(0); // Fade to completely transparent
                ft.setOnFinished(event -> {
                    child.setVisible(false); // Optionally make the node invisible after fade out
                    // Remove click event
                    child.setOnMouseClicked(null);
                });
                ft.play();
            }
        }
    }

}
