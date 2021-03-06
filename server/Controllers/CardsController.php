<?php
include_once "../../Models/Cards.php";

class CardsController
{
    static function createCard($cardData, $accountId){

        $cards = new Cards();

        $val = [
            "card_id" => $accountId,
            "active" => "1",
            "expiration_date" => $cardData->expiration_date,
            "pin" => password_hash($cardData->pin, PASSWORD_BCRYPT),
            "bank_account_id" => $accountId
        ];

        return $cards->createCard($val);
    }

}