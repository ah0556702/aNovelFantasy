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

        // Within the loop where you're adding books to the grid
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                // Ensure bookIndex wraps around the size of the items JSON array
                bookIndex %= items.length();

                // Get book information from items JSON array
                JSONObject item = items.getJSONObject(bookIndex);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                String authors = authorsArray == null ? "Author information not available" : authorsArray.join(", ");

                // Now, ensure that the imageView for the book uses the same bookIndex to fetch the corresponding cover image
                ImageView imageView = new ImageView(images.get(bookIndex % images.size()));
                setClickEvent(imageView, title, bookIndex, volumeInfo, authors, bookIndex); // Pass bookIndex to setClickEvent

                gridPane.add(imageView, col, row);
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
    private void setClickEvent(ImageView imageView, String title, int bookIndex, JSONObject book, String authors, int imageIndex){
        imageView.setOnMouseClicked(event -> {
            String description = book.optString("description", "No description available.");

            // Set the current book title for guessing
            currentBookTitle = title;

            // Update the text area with book details
            testing.setText(title + " " + authors + " " + description);

            // Set the cover image
            Image wholeBook = new Image(new File(bookImages[imageIndex % bookImages.length]).toURI().toString());
            wholeBookImg.setImage(wholeBook);

            // Update book details
            bookDetails.setText(String.format("%s\n\nAuthor: %s\n\nSummary:\n%s", title, authors, description));

            // Show and hide appropriate panes
            bookPane.setVisible(true);
            gridPane.setVisible(false);
            GaussianBlur gaussianBlur = new GaussianBlur();
            bookShelfBack.setEffect(gaussianBlur);
        });
    }


    @FXML
    private TextField userText;

    private String currentBookTitle;
    private int guessCount = 0;

    @FXML
    private String updateDisplayedTitle(String userInput) {
        StringBuilder displayedTitle = new StringBuilder();
        String[] words = currentBookTitle.split(" ");
        for (String word : words) {
            if (word.equalsIgnoreCase("the") || word.equalsIgnoreCase("and") || word.equalsIgnoreCase("or") || word.contains(":")) {
                displayedTitle.append(word); // Automatically display these words
            } else {
                for (int i = 0; i < word.length(); i++) {
                    char c = word.charAt(i);
                    if (userInput.indexOf(c) >= 0 || c == ':' || c == '\'' || c == '-') {
                        displayedTitle.append(c); // Display guessed letters and specific punctuation
                    } else {
                        displayedTitle.append('_'); // Display underscores for unguessed letters
                    }
                }
            }
            displayedTitle.append(" "); // Add a space after each word
        }

        return displayedTitle.toString().trim(); // Trim the trailing space
    }

    @FXML
    private void userGuess() {
        String userInput = userText.getText().toLowerCase();
        StringBuilder allGuesses = new StringBuilder(); // This should ideally be a class variable

        if (currentBookTitle == null) {
            System.out.println("No book selected or title not set");
            userText.setText("");
            return; // Exit the method early
        }
        guessCount++;

        // Add the current guess to all previous guesses (you may want to ensure no duplicate characters)
        for (char c : userInput.toCharArray()) {
            if (allGuesses.indexOf(String.valueOf(c)) < 0) { // Avoid duplicate guesses
                allGuesses.append(c);
            }
        }

        // Update displayed title
        String displayedTitle = updateDisplayedTitle(allGuesses.toString());
        userText.setText(displayedTitle);

        // Check if the guess is correct or if the user has guessed three times
        if (displayedTitle.replace(" ", "").equalsIgnoreCase(currentBookTitle.replace(" ", "")) || guessCount >= 3) {
            bookPane.setVisible(false);
            gridPane.setVisible(true);
            bookShelfBack.setEffect(null); // Remove blur

            if (guessCount >= 3 && !displayedTitle.equalsIgnoreCase(currentBookTitle)) {
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

        tt.play();
    }


    private void fadeRandomImages() {
        Random rand = new Random();
        List<Node> children = new ArrayList<>(gridPane.getChildren());
        int numberOfImagesToFade = 3;

        // Shuffle the list to randomize which images will fade
        Collections.shuffle(children, rand);

        for (int i = 0; i < Math.min(numberOfImagesToFade, children.size()); i++) {
            Node child = children.get(i);
            if (child instanceof ImageView) {
                FadeTransition ft = new FadeTransition(Duration.seconds(1), child);
                ft.setToValue(0); // Fade to transparent
                ft.setOnFinished(event -> {
                    child.setVisible(false); // make the node invisible after fade out
                    // Remove click event
                    child.setOnMouseClicked(null);
                });
                ft.play();
            }
        }
    }
}
