<?php

include_once "../../Database/DatabaseConnection.php";

class LanguageSystem
{
    static function getTranslation($request)
    {
        $translationID = $request->id; // for instance: "TextPinWrong"
        $language = $request->language; // for instance: "NEDERLANDS", "ENGLISH"

        // DO query
        $query = "SELECT ? FROM translations WHERE id= ?";

        $database = new DatabaseConnection();
        $database->getConnection();

        $stmt = $database->conn->prepare($query);
        $stmt->bindParam($language, $translationID);

        $stmt->execute();
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        return $row;
    }
}