package model;

import org.json.*;
import org.openjfx.App;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

/**
 * ConnectionManager
 * <p>
 * Manages the connection to the JSON API.
 */
public class SessionManager extends ConnectionManager{

    private static SessionManager session;
    private final String accountname;
    private final String JWT;
    private final int status;

    public static void main(String[] args) {
        //System.out.println(SessionManager.getCard("SO-DASB-00000001"));//TODO veranderen naar So
        /*if(con != null){
            System.out.println("account: " + con.getAccountname() + " ,balance: " + con.getBalance());
            con.withdraw(new SetOfBanknotes(1,2,0));
            System.out.println("account: " + con.getAccountname() + " ,balance: " + con.getBalance());
        }*/
    }

    // Private default constructor
    private SessionManager(String accountname, String jwt, int status) {
        this.accountname = accountname;
        this.JWT = jwt;
        this.status = status;
    }

    // Returns an loginManager when succeeded, else null
    public final static SessionManager tryLogin(String cardNumber, String pincode) {
        try {
            JSONObject jsonCardData = new JSONObject();
            jsonCardData.put("card_id", cardNumber);
            jsonCardData.put("pin", pincode);

            System.out.println(jsonCardData);

            JSONObject jsonObj = loadData("Login/login.php", jsonCardData);  // TODO use https connection
            System.out.println(jsonObj);



            session = new SessionManager(jsonObj.getJSONObject("data").getString("bank_account_id"), jsonObj.getString("jwt"), jsonObj.getInt("status"));
            return session;

        } catch (Exception e) {
            System.err.println("error while initialising login");
            System.err.println(e.toString());
        }
        return null;
    }

    public static void blockCard(String bankAccountId){
        JSONObject jsonData = new JSONObject();
        jsonData.put("bank_account_id", bankAccountId);
        System.out.println(jsonData);
        loadData("BankAccount/block.php", jsonData);
    }

    public static JSONObject getCard(String cardId){
        JSONObject cardData = new JSONObject();
        cardData.put("card_id", cardId);
        JSONObject jsonObj = loadData("BankAccount/readCard.php", cardData);
        return jsonObj;
    }

    public static SessionManager getSession(){
        return session;
    }

    public boolean withdraw(SetOfBanknotes banknotes) {
        JSONObject request = new JSONObject();
        request.put("amount", banknotes.getTotalAmount());
        request.put("causer_account_id", this.getAccountname());
        request.put("receiver_account_id", "SO-DASB-00000001");
        request.put("pin", App.pin);
        request.put("jwt", this.JWT);
        System.out.println("withdraw data");
        System.out.println(request);
        System.out.println("withdraw data");
        loadData("TransActions/withdraw.php", request);
        return true;
    }

    public int getStatus() {
        return status;
    }

    // Returns balance
    public String getBalance() {
        JSONObject accountData = new JSONObject();
        accountData.put("account_id", this.getAccountname());
        accountData.put("pin", App.pin);
        accountData.put("jwt", this.JWT);

        JSONObject jsonObj = loadData("BankAccount/read.php", accountData);

        if(jsonObj.getInt("status") == 200){
            return jsonObj.getString("account_balance");
        }

        return "";
    }

    public void blockPass(String dankId){

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

    public static void deleteSession(){
        session = null;
    }
}
