package model;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConnectionManager {

    // Loads JSONobjest with a post request from the given url
    protected static JSONObject loadData(String urlStr, JSONObject jsonObject) {
        try {
            // prepare byteArray to sent
            byte[] out = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            URL url = new URL("http://dasbank.ml/api/" + urlStr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            // sent json object to the server
            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            InputStream stream = http.getInputStream();
            String str = new String(stream.readAllBytes());

            JSONObject jsonUserData = new JSONObject(str);

            return jsonUserData;

        } catch (MalformedURLException e) {
            System.out.println("malformed URL");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            System.out.println("error with loading data");
        }
        return null;
    }


}
