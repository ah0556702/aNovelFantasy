package org.example.anovelfantasy;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.*;

public class Game {

    public static void show(Scene current) {
        try {
            FXMLLoader loader = new FXMLLoader(Game.class.getResource("game.fxml"));
            Parent root = loader.load();
            current.setRoot(root);
        } catch (IOException e) {
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

//    @FXML
//    private void initialize() throws Exception {
//        updateTestingTextArea();
//        // Load image paths
////        String[] imgPaths = {
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\blueGreenR.png",
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\brownR.png",
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\darkBlueR.png",
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\greyR.png",
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\maroonR.png",
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\orangeR.png",
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\purpleR.png",
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\royalBlueR.png",
////                "C:\\Users\\snide\\IdeaProjects\\aNovelFantasy\\src\\main\\resources\\images\\bookBindings\\silverPurpleR.png"
////        };
//
////        String[] imgPaths = {
////                "../../images/bookBindings/blueGreenR.png",
////                "../../images/bookBindings/brownR.png",
////                "../../images/bookBindings/darkBlueR.png",
////                "../../images/bookBindings/greyR.png",
////                "../../images/bookBindings/maroonR.png",
////                "../../images/bookBindings/orangeR.png",
////                "../../images/bookBindings/purpleR.png",
////                "../../images/bookBindings/royalBlueR.png",
////                "../../images/bookBindings/silverPurpleR.png"
////        };
//
//        String[] imgPaths = {
//                "images/bookBindings/blueGreenR.png",
//                "images/bookBindings/brownR.png",
//                "images/bookBindings/darkBlueR.png",
//                "images/bookBindings/greyR.png",
//                "images/bookBindings/maroonR.png",
//                "images/bookBindings/orangeR.png",
//                "images/bookBindings/purpleR.png",
//                "images/bookBindings/royalBlueR.png",
//                "images/bookBindings/silverPurpleR.png"
//        };
//
//        // Create a list to store Image objects
//        List<Image> images = new ArrayList<>();
//
//        // Load each image into the list
//        for (String path : imgPaths) {
//            URI uri = new File(path).toURI();
//            images.add(new Image(uri.toString()));
//        }
//
//        // Shuffle the images
//        Collections.shuffle(images);
//        int imageIndex = 0;
//        int bookIndex = 0;
//        int numRows = gridPane.getRowConstraints().size();
//        int numCols = gridPane.getColumnConstraints().size();
//
//        // API Call
//        ArrayList<Books> booksList = new ArrayList<>(40);
//        // Calculate the width and height of each grid slot
//        String popularBooksData = BooksAPI.fetchClassicBooks();
//
//        // Parse the JSON data to extract book titles
//        JSONArray items = new JSONObject(popularBooksData).getJSONArray("items");
//        StringBuilder titlesBuilder = new StringBuilder();
//
//        // Within the loop where you're adding books to the grid
//        for (int row = 0; row < numRows; row++) {
//            for (int col = 0; col < numCols; col++) {
//                // Ensure bookIndex wraps around the size of the items JSON array
//                bookIndex %= items.length();
//
//                // Get book information from items JSON array
//                JSONObject item = items.getJSONObject(bookIndex);
//                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
//                String title = volumeInfo.getString("title");
//                JSONArray authorsArray = volumeInfo.optJSONArray("authors");
//                String authors = authorsArray == null ? "Author information not available" : authorsArray.join(", ");
//
//                // Now, ensure that the imageView for the book uses the same bookIndex to fetch the corresponding cover image
//                ImageView imageView = new ImageView(images.get(bookIndex % images.size()));
//                setClickEvent(imageView, title, bookIndex, volumeInfo, authors, bookIndex); // Pass bookIndex to setClickEvent
//
//                gridPane.add(imageView, col, row);
//                bookIndex++;
//            }
//        }
//    }


    @FXML
    private void initialize() throws Exception {
        updateTestingTextArea();

        // Load image paths
        String[] imgPaths = {
                "/images/bookBindings/blueGreenR.png",
                "/images/bookBindings/brownR.png",
                "/images/bookBindings/darkBlueR.png",
                "/images/bookBindings/greyR.png",
                "/images/bookBindings/maroonR.png",
                "/images/bookBindings/orangeR.png",
                "/images/bookBindings/purpleR.png",
                "/images/bookBindings/royalBlueR.png",
                "/images/bookBindings/silverPurpleR.png"
        };

        // Create a list to store Image objects
        List<Image> images = new ArrayList<>();

        // Load each image into the list
        for (String path : imgPaths) {
            Image image = new Image(getClass().getResourceAsStream(path));
            images.add(image);
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
                ImageView imageView = new ImageView(images.get(imageIndex % images.size()));
                setClickEvent(imageView, title, bookIndex, volumeInfo, authors, imageIndex); // Pass imageIndex to setClickEvent

                gridPane.add(imageView, col, row);
                bookIndex++;
                imageIndex++; // Increment imageIndex for the next image
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
    private TabPane tabPane;

    @FXML
    String[] bookImages = {
            "../../images/wholeBook/blueGreen.png",
            "../../images/wholeBook/brown.png",
            "../../images/wholeBook/darkBlue.png",
            "../../images/wholeBook/grey.png",
            "../../images/wholeBook/maroon.png",
            "../../images/wholeBook/orange.png",
            "../../images/wholeBook/purple.png",
            "../../images/wholeBook/royalBlue.png",
            "../../images/wholeBook/silverPurple.png"
    };

    @FXML
    private ImageView wholeBookImg;

    @FXML
    private Label bookDetails;

    @FXML
    private void setClickEvent(ImageView imageView, String title, int bookIndex, JSONObject book, String authors, int imageIndex) {
        imageView.setOnMouseClicked(event -> {
            String description = book.optString("description", "No description available.");

            // Set the current book title for guessing
            currentBookTitle = title;

            // Update the text area with book details
            testing.setText(title + " " + authors + " " + description);
            String imagePath = "/images/wholeBook/royalBlue.png";
            Image wholeBook = new Image(getClass().getResourceAsStream(imagePath));

            // Set the cover image
            wholeBookImg.setImage(wholeBook);

            // Update book details
            bookDetails.setText(String.format("%s\n\nAuthor: %s\n\nSummary:\n%s", title, authors, description));

            // Show and hide appropriate panes
            bookPane.setVisible(true);
            tabPane.setVisible(false);
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
                    if (userInput.toLowerCase().indexOf(Character.toLowerCase(c)) >= 0 || c == ':' || c == '\'' || c == '-') {
                        if (i == 0) {
                            // Display the first letter with its original casing
                            displayedTitle.append(word.charAt(0));
                        } else {
                            displayedTitle.append(c); // Display guessed letters and specific punctuation
                        }
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
    Label scoreLabel;
    private int score = 0;

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
            // Remove the guessed book from the grid on the first wrong guess
            if (!displayedTitle.replace(" ", "").equalsIgnoreCase(currentBookTitle.replace(" ", ""))) {
                decrementVisibleBooksCount();
                removeGuessedBook(); // Remove the guessed book from the grid
                updateScore(0); // Decrement score by 15 for wrong guesses
            } else {
                // Update the score based on the number of attempts
                updateScore(guessCount);
            }

            // Check if there's only one visible book left on the shelf
            if (getVisibleBooksCount() == 1) {
                // Trigger GameOver
                // In the Game class
                GameOver gameOver = new GameOver(score);
                //gameOver.setScore(score); // Assuming `score` is the variable holding the score
                gameOver.show(gridPane.getScene());
                return; // Exit the method early
            }

            bookPane.setVisible(false);
            gridPane.setVisible(true);
            tabPane.setVisible(true);
            bookShelfBack.setEffect(null); // Remove blur

            if (guessCount >= 3 && !displayedTitle.equalsIgnoreCase(currentBookTitle)) {
                // Shake gridPane and fade random ImageViews
                shakeGridPane();
                fadeRandomImages();
            }

            reappearBooks(guessCount);

            // Reset for the next guess
            guessCount = 0;
            userText.setText("");
        }
    }

    private int visibleBooksCount = 0;
    private List<Node> disappearedBooks = new ArrayList<>(); // Track disappeared books

    // Method to decrement the count of visible books
    private void decrementVisibleBooksCount() {
        visibleBooksCount--;
    }

    private int getVisibleBooksCount() {
        int count = 0;
        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView && node.isVisible()) {
                count++;
            }
        }
        return count;
    }

    private void removeGuessedBook() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView && node.isVisible()) {
                disappearedBooks.add(node); // Add the guessed book to the disappeared books list
                decrementVisibleBooksCount();
                break; // Stop after removing the first visible book
            }
        }
    }

    @FXML
    private void updateScore(int attempts) {
        int pointsEarned = 0;
        if (attempts == 1) {
            pointsEarned = 50;
        } else if (attempts == 2) {
            pointsEarned = 30;
        } else if (attempts == 3) {
            pointsEarned = 15;
        }

        // Decrement the score by 15 if the attempt was unsuccessful
        if (pointsEarned == 0) {
            score -= 15;
        } else {
            score += pointsEarned;
        }

        // Ensure the score doesn't go below 0
        if (score < 0) {
            score = 0;
        }

        scoreLabel.setText("Score: " + score);
    }


    private void reappearBooks(int attempts) {
        int booksToReappear = Math.min(3 - attempts, 3); // Calculate number of books to reappear
        if (booksToReappear > 0) {
            Collections.shuffle(disappearedBooks); // Shuffle the disappeared books list
            for (int i = 0; i < Math.min(booksToReappear, disappearedBooks.size()); i++) {
                Node book = disappearedBooks.get(i);
                book.setVisible(true); // Reappear the disappeared book
                incrementVisibleBooksCount();
            }
        }
    }

    private void incrementVisibleBooksCount() {
        visibleBooksCount++;
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
        int numberOfImagesToFade = 5;

        // Add the guessed book to the list of children to fade
        if (!disappearedBooks.isEmpty()) {
            Collections.shuffle(disappearedBooks, rand);
            numberOfImagesToFade--; // Decrement as the guessed book will also be faded
        }

        List<Node> children = new ArrayList<>(gridPane.getChildren());
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

    @FXML Button exitBtn;

    @FXML
    private void exitGame(){
        Platform.exit();
    }
}
