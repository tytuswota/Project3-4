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
public class SessionManager extends ConnectionManager{

    private static SessionManager session;
    private final String accountname;
    private final String JWT;

    public static void main(String[] args) {
        SessionManager con = SessionManager.tryLogin("SU-DASB-00000001","1234");
        if(con != null){
            System.out.println("account: " + con.getAccountname() + " ,balance: " + con.getBalance());
            con.withdraw(new SetOfBanknotes(1,2,0));
            System.out.println("account: " + con.getAccountname() + " ,balance: " + con.getBalance());
        }
    }

    // Private default constructor
    private SessionManager(String accountname, String jwt) {
        this.accountname = accountname;
        this.JWT = jwt;
    }

    // Returns an loginManager when succeeded, else null
    public final static SessionManager tryLogin(String cardNumber, String pincode) {
        try {
            JSONObject jsonCardData = new JSONObject();
            jsonCardData.put("card_id", cardNumber);
            jsonCardData.put("pin", pincode);
            JSONObject jsonObj = loadData("Login/login.php", jsonCardData);  // TODO use https connection
            System.out.println(jsonObj);
            session = new SessionManager(jsonObj.getJSONObject("data").getString("bank_account_id"), jsonObj.getString("jwt"));
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

    public static SessionManager getSession(){
        return session;
    }

    public boolean withdraw(SetOfBanknotes banknotes) {
        JSONObject request = new JSONObject();
        request.put("amount", banknotes.getTotalAmount());
        request.put("causer_account_id", this.getAccountname());
        request.put("receiver_account_id", this.getAccountname());
        request.put("jwt", this.JWT);
        System.out.println(request);
        loadData("TransActions/withdraw.php", request);
        return true;
    }


    // Returns balance
    public String getBalance() {
        JSONObject accountData = new JSONObject();
        accountData.put("account_id", this.getAccountname());
        accountData.put("jwt", this.JWT);

        System.out.println(accountData);

        JSONObject jsonObj = loadData("BankAccount/read.php", accountData);

        return jsonObj.getString("account_balance");
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
}
