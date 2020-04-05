<?php
include_once "BaseModel.php";

class Transactions extends BaseModel
{
    public function __construct(){
        $this->tableName = "Transaction";
    }

    public function readTransaction($transActionId = 0){
        $stmt = $this->read("transaction_id",$transActionId);
        $transActionArray = array();

        while($row = $stmt->fetch(PDO::FETCH_ASSOC));

        $transActionItem = array(
            "transaction_id" => $row['transaction_id'],
            "date_time" => $row['date_time'],
            "amount" => $row['amount'],
            "bank_account_id" => $row['bank_account_id']
        );
        array_push($transActionArray, $transActionItem);

        http_response_code(200);
        return json_encode($transActionArray);
    }

    public function createTransaction($values){
        return $this->create($values);
    }

}