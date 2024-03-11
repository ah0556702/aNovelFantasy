package org.example.anovelfantasy;

import org.example.anovelfantasy.BooksAPI;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Books {
    private String title;
    private String author;
    private String summary;

    // Constructor
    public Books(String title, String author, String summary) {
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // Method to fetch data from the API and populate Book objects
    public static ArrayList<Books> fetchBooksFromAPI(String apiUrl) {
        ArrayList<Books> books = new ArrayList<>();
        try {
            String apiResponse = BooksAPI.fetchApiData(apiUrl); // Assuming BooksAPI.fetchApiData() fetches the API response
            JSONObject jsonObject = new JSONObject(apiResponse);
            JSONArray results = jsonObject.getJSONObject("results").getJSONArray("lists").getJSONObject(0).getJSONArray("books");

            for (int i = 0; i < results.length(); i++) {
                JSONObject bookJson = results.getJSONObject(i);
                String title = bookJson.getString("title");
                String author = bookJson.getString("author");
                String summary = bookJson.optString("description", ""); // Assuming the summary is provided under the "description" key

                Books book = new Books(title, author, summary);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
