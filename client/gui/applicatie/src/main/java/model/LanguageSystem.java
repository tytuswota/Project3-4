package model;

import org.json.JSONObject;

public class LanguageSystem extends ConnectionManager{

    private static Language language = Language.RUSSIAN;

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

    public  enum Language {
        NEDERLANDS,
        ENGLISH,
        RUSSIAN
    }
}
