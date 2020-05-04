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
        SessionManager con = SessionManager.tryLogin("SU-DASB-00000002","1234");
        if(con != null){
            System.out.println("account: " + con.getAccountname() + " ,balance: " + con.getBalance());
            con.withdraw(new SetOfBanknotes(1,2,0));
            System.out.println("account: " + con.getAccountname() + " ,balance: " + con.getBalance());
        }
    }

    // Private default constructor
    private SessionManager(String accountname) {
        this.accountname = accountname;
        this.JWT = "";
    }

    // Returns an loginManager when succeeded, else null
    public final static SessionManager tryLogin(String cardNumber, String pincode) {
        try {
            JSONObject jsonCardData = new JSONObject();
            jsonCardData.put("card_id", cardNumber);
            jsonCardData.put("pin", pincode);
            JSONObject jsonObj = loadData("Login/login.php", jsonCardData);  // TODO use https connection

            session = new SessionManager(jsonObj.getString("bank_account_id"));
            return session;

        } catch (Exception e) {
            System.err.println("error while initialising login");
            System.err.println(e.toString());
        }
        return null;
    }
    public static SessionManager getSession(){
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
