package org.openjfx;

import org.json.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

/**
 * ConnectionManager
 *
 * Manages the connection to the JSON API.
 */
public class ConnectionManager {

        // Test method for loginManager
        public static void main(String[] args) {
            ConnectionManager connectionManager = ConnectionManager.tryLogin("testCardNumber", 1);
        }

        private final String accountname;
        private final int balance;

        // Private default constructor
        private ConnectionManager(String accountname, int balance) {
            this.accountname = accountname;
            this.balance = balance;
        }

        // Returns an loginManager when succeeded, else null
        public final static ConnectionManager tryLogin(String cardNumber, int pincode) {
            try {
                JSONObject jsonToSent = new JSONObject();
                jsonToSent.put("cartNumber", cardNumber);
                jsonToSent.put("pincode", pincode);
                JSONObject obj = loadData("http://www.google.com",jsonToSent); // TODO use https connection
                return new ConnectionManager( obj.getString("accountname"), obj.getInt("balance"));

            } catch (Exception e) {
                System.err.println("error while initialising login");
                System.err.println(e.toString());
            }
            return null;
        }

        // Loads JSONobjest with a post request from the given url
        private static JSONObject loadData(String urlStr, JSONObject jsonObject) {
            try {
                // prepare byteArray to sent
                byte[] out = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                int length = out.length;

                URL url = new URL(urlStr);
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
                System.out.println(str);
                return new JSONObject(str);

            } catch (MalformedURLException e) {
                System.out.println("malformed URL");
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println(e.getMessage());
                System.out.println("error with loading data");
            }
            return null;
        }

        // Returns balance
        public int getBalance() {
            return balance;
        }

        // returns account name
        public String getAccountname() {
            return accountname;
        }

    }
