import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CricinfoAPIClient {

    public static void main(String[] args) {
        try {
            // Make the API call
            String response = callCricinfoAPI();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String callCricinfoAPI() throws IOException {
        String uri = "https://api.cricapi.com/v1/series?apikey=8c096f5b-dd98-4ebc-b027-a66bd03e4366&offset=0";

        URL url = new URL(uri);

        // Create HttpURLConnection object
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Set request headers
        connection.setRequestProperty("User-Agent", "YourApp/1.0");

        // Get the response code
        int responseCode = connection.getResponseCode();

        // Read the response from the API
        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        }

        // Close the connection
        connection.disconnect();

        // Return the response as a string
        return response.toString();
    }

    public static String callCricinfoAPI(URL url, HttpURLConnection connection) throws IOException {
        // Set request headers
        connection.setRequestProperty("User-Agent", "YourApp/1.0");

        // Get the response code
        int responseCode = connection.getResponseCode();

        // Read the response from the API
        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        }

        // Close the connection
        connection.disconnect();

        // Return the response as a string
        return response.toString();
    }
}
