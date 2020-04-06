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
        /*public static void main(String[] args) {
            ConnectionManager connectionManager = ConnectionManager.tryLogin("testCardNumber", 1);
        }*/

        private final String accountname;
        private final String balance;
        private final String JWT;

        // Private default constructor
        private ConnectionManager(String accountname, String balance) {
            this.accountname = accountname;
            this.balance = balance;
            this.JWT = "";
        }

        // Returns an loginManager when succeeded, else null
        public final static ConnectionManager tryLogin(String cardNumber, String pincode) {
            try {
                JSONObject jsonCardData = new JSONObject();
                jsonCardData.put("card_id", cardNumber);
                jsonCardData.put("pin", pincode);
                JSONObject jsonObj = loadData("http://192.168.33.10/api/Login/login.php",jsonCardData); // TODO use https connection

                //data from api
                /*
                * "bank_account_id": "SU-DASB-1",
                   "account_balance": "81.00",
                    "type": "1",
                    "start_date": "2020-02-02",
                    "end_date": null,
                    "user_id": "1"
                * */

                return new ConnectionManager( jsonObj.getString("bank_account_id"), jsonObj.getString("account_balance"));

            } catch (Exception e) {
                System.err.println("error while initialising login");
                System.err.println(e.toString());
            }
            return null;
        }

        public static String userBalance(String userId){
            JSONObject accountData = new JSONObject();
            accountData.put("account_id",userId);

            JSONObject jsonObj = loadData("http://192.168.33.10/api/BankAccount/read.php",accountData);

            return jsonObj.getString("account_balance");
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

        // Returns balance
        public String getBalance() {
            return balance;
        }

        // returns account name
        public String getAccountname() {
            return accountname;
        }

    }
