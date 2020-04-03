<?php
include_once "BaseModel.php";

class Accounts extends BaseModel
{
    public function __construct(){

        $this->tableName = "BankAccount";
    }

    public function readAccount($bankId = 0){
        $stmt = $this->read("bank_account_id",$bankId);
        $bankAccountArray = array();

        while($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $bankAccountItem = array(
                "bank_account_id" => $row['bank_account_id'],
                "account_balance" => $row['account_balance'],
                "type" => $row['type'],
                "start_date" => $row['start_date'],
                "end_date" => $row['end_date'],
                "user_id" => $row['user_id']
            );
            array_push($bankAccountArray, $bankAccountItem);

        }

        http_response_code(200);
        return json_encode($bankAccountArray);
    }

    public function createAccount($values){
        return $this->create($values);
    }

    public function deleteAccount(){

    }

}