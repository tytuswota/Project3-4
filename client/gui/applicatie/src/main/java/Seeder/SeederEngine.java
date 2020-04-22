package Seeder;

/*
the seederEngine fills the database with data from the json files.

lacking functionality:
    adding cards
 */

import org.json.JSONArray;
import model.ConnectionManager;
import org.json.JSONObject;

import java.io.FileReader;
import java.util.function.Function;

public class SeederEngine {
    public static void main(String[] args) {
        //createFromFile(ConnectionManager::createUser, "Users.json");
        //createFromFile(ConnectionManager::createBankAccount, "BankAccounts.json");
        createFromFile(ConnectionManager::createTransaction, "Transactions.json");
        //createFromFile(ConnectionManager::, ".json");

    }

    private static void createFromFile(Function<JSONObject, JSONObject> myfunction, String file) {
        JSONArray users = null;
        try (FileReader reader = new FileReader("src/main/java/Seeder/" + file)) {
            char[] inputBuffer = new char[500];
            reader.read(inputBuffer);
            String str = new String(inputBuffer);
            users = new JSONArray(str);
        } catch (Exception e) {
            System.out.println("error occurred while reading file");
        }
        for (int i = 0; i < users.length(); i++) {
            try {
                JSONObject requestBody = users.getJSONObject(i);
                JSONObject a = myfunction.apply(requestBody); // TODO gives a 503 response while the account is made in the database
            } catch (Exception e) {
                System.out.println("error while creating object");
            }
        }

    }

}

