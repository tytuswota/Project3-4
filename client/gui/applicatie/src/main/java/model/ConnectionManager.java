package model;

import org.json.*;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

/**
 * ConnectionManager
 * <p>
 * Manages the connection to the JSON API.
 */
public class ConnectionManager {

    private static ConnectionManager session;
    private final String accountname;
    private final String JWT;

    public static void main(String[] args) {
        ConnectionManager con = ConnectionManager.tryLogin("SU-DASB-00000002","1234");
        if(con != null){
            System.out.println("account: " + con.getAccountname() + " ,balance: " + con.getBalance());
            con.withdraw(new SetOfBanknotes(1,2,0));
            System.out.println("account: " + con.getAccountname() + " ,balance: " + con.getBalance());
        }
    }

    // Private default constructor
    private ConnectionManager(String accountname) {
        this.accountname = accountname;
        this.JWT = "";
    }

    // Returns an loginManager when succeeded, else null
    public final static ConnectionManager tryLogin(String cardNumber, String pincode) {
        try {
            JSONObject jsonCardData = new JSONObject();
            jsonCardData.put("card_id", cardNumber);
            jsonCardData.put("pin", pincode);
            JSONObject jsonObj = loadData("Login/login.php", jsonCardData);  // TODO use https connection

            session = new ConnectionManager(jsonObj.getString("bank_account_id"));
            return session;

        } catch (Exception e) {
            System.err.println("error while initialising login");
            System.err.println(e.toString());
        }
        return null;
    }
    public static ConnectionManager getSession(){
        return session;
    }

    public boolean withdraw(SetOfBanknotes banknotes) {
        JSONObject request = new JSONObject();
        request.put("amount", banknotes.getTotalAmount());
        request.put("causer_account_id", this.getAccountname());
        request.put("receiver_account_id", this.getAccountname());
        loadData("TransActions/withdraw.php", request);
        return true;
    }

    // Loads JSONobjest with a post request from the given url
    private static JSONObject loadData(String urlStr, JSONObject jsonObject) {
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


    // Returns balance
    public String getBalance() {
        JSONObject accountData = new JSONObject();
        accountData.put("account_id", this.getAccountname());

        JSONObject jsonObj = loadData("BankAccount/read.php", accountData);

        return jsonObj.getString("account_balance");
    }

    // returns account name
    public String getAccountname() {
        return accountname;
    }

    //for testing
    public static JSONObject createUser(JSONObject request) {
        return loadData("Users/create.php", request);
    }

    public static JSONObject createBankAccount(JSONObject request) {
        return loadData("BankAccount/create.php", request);
    }

    public static JSONObject createTransaction(JSONObject request) {
        return loadData("TransActions/withdraw.php", request);
    }
}
