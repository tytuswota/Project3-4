package model;

import org.json.JSONObject;

public class LanguageSystem extends ConnectionManager{

    private static Language language = Language.NEDERLANDS;

    public static void main(String[] args) {
        System.out.println(language.toString());
    }

    // Needs the id of and returns the translation.
    public static String getString(String id){
        JSONObject toSent = new JSONObject();
        toSent.put("id", id);
        toSent.put("language", language.toString());
        JSONObject recieved = ConnectionManager.loadData("LanguageSystem/getTranslation.php",toSent);
        if(recieved != null){
            return recieved.getString(id);
        }
        return id;
    }

    public  enum Language {
        NEDERLANDS,
        ENGLISH
    }
}
