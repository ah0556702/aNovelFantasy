package org.example.anovelfantasy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BooksAPI {
    public static String fetchApiData(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // reads response
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while((line = reader.readLine()) != null){
            response.append(line);
        }

        reader.close();

        return response.toString();
    }

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    public static String fetchPopularBooks() throws Exception {
        String apiUrl = BASE_URL + "?q=subject:fiction&orderBy=relevance&maxResults=10&key=AIzaSyA90DQsb08hjSgng1KGCJUGvfV98Mketa0";
        return fetchApiData(apiUrl);
    }

    public static String fetchClassicBooks() throws Exception {
        String apiUrl = BASE_URL + "?q=subject:classic&orderBy=relevance&maxResults=10&key=AIzaSyA90DQsb08hjSgng1KGCJUGvfV98Mketa0";
        return fetchApiData(apiUrl);
    }

    public static String fetchFantasyBooks(int numResults) throws Exception {
        String apiUrl = BASE_URL + "?q=subject:fantasy&orderBy=relevance&maxResults=10&key=AIzaSyA90DQsb08hjSgng1KGCJUGvfV98Mketa0";
        return fetchApiData(apiUrl);
    }
}
