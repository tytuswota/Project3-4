<?php
include_once "BaseModel.php";

class Cards extends BaseModel
{
    public function __construct(){
        $this->tableName = 'Card';
    }

    public function readCard($cardId = 0){
        $stmt = $this->read($cardId);
        $cardsArray = array();

        while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            $cardsItem = array(
                "card_id" => $row['card_id'],
                "active"=> $row['active'],
                "expiration_date" => $row['expiration_date'],
                "bank_account_id" => $row['bank_account_id'],
                "pin" => $row['pin']
            );

            array_push($cardsArray, $cardsItem);
        }

        http_response_code(200);

        return json_encode($cardsArray);
    }

    public function createCard($val){
        $this->create($val);
    }

}