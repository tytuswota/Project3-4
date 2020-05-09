<?php

include_once "../../Database/DatabaseConnection.php";

class LanguageSystem
{
    static function getTranslation($request)
    {
        $translationID = $request->id; // for instance: "TextPinWrong"

        // DO query
        $query = "SELECT * FROM Translations WHERE id= ?";

        $database = new DatabaseConnection();
        $database->getConnection();

        $stmt = $database->conn->prepare($query);
        $stmt->execute(array( $translationID));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        return $row;
    }
}