package model;

import org.json.JSONObject;

/**
 * LanguageSystem
 *
 * This class fetched the translations from the server.
 * It uses the connectionManager to make a connection to the dasbank API.
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class LanguageSystem extends ConnectionManager{

    // Private field used to set the language.
    private static Language language = Language.NEDERLANDS;

    // Method to test the LanguageSystem.
    public static void main(String[] args) {
        System.out.println(language.toString());
        System.out.println(LanguageSystem.getString("other"));
    }

    // Needs the id of and returns the translation.
    public static String getString(String id){
        try {
            JSONObject toSent = new JSONObject();
            toSent.put("id", id);
            JSONObject recieved = ConnectionManager.loadData("LanguageSystem/getTranslation.php", toSent);
            if (recieved != null) {
                return recieved.getString(language.toString().toLowerCase());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    // Method to set the Language
    public static void setLanguage(Language language) {
        LanguageSystem.language = language;
    }

    // Enum with available languages.
    public  enum Language {
        NEDERLANDS,
        ENGLISH,
        RUSSIAN
    }
}
