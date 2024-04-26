package dk.sdu.sem4.pro.rest;

import dk.sdu.sem4.pro.communication.services.IClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class RESTCommunication implements IClient {
    //These variables are used to create the connection to the Transporter.
    static URI BASE_URI = URI.create("http://localhost:8082/v1/status/");
    final URL url;

    public RESTCommunication() {
        try {
            this.url = BASE_URI.toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    public JSONObject receive() {
        try {
            //This part opens a connection to the URL and sets the request method to "GET" in order to receive information.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //This part reads the response from the connection, if there is one.
            return getJsonObject(connection);
        } catch (IOException | JSONException e) {
            // Handle exceptions locally or log them
            e.printStackTrace();
            return null;
        }
    }

    public Integer send(JSONObject jsonObject) {
        try {
            //This part opens a connection to the URL and the request method to "PUT" in order to send commands.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");

            //This part writes the data from the JSONObject to the connection.
            if (jsonObject != null) {
                //This part indicates that the data is in a JSON format.
                connection.setRequestProperty("Content-Type", "application/json");
                //This part indicates that the connection will be used to send data.
                connection.setDoOutput(true);

                //This part sends the actual data to the connection.
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(jsonObject.toString().getBytes());
                    outputStream.flush();
                }
            }

            //This part reads the response from the connection, if there is one.
            JSONObject response = getJsonObject(connection);
            //This part returns the status code, if the response has one. If it doesn't, it returns null.
            if (response != null && response.has("state")) {
                return response.getInt("state");
            } else {
                return null;
            }
        } catch (IOException | JSONException e) {
            // Handle exceptions locally or log them
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject getJsonObject(HttpURLConnection connection) throws IOException, JSONException {
        try {
            //This "if"-statement returns null if there's no response or an error response.
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //This part attempts to read the data from the connection and return it as a JSONObject.
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    return new JSONObject(response.toString());
                }
            } else {
                return null;
            }
        } finally {
            //This part disconnects the connection after the function is finished with reading the data from the connection.
            connection.disconnect();
        }
    }
}
