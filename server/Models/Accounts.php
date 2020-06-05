<?php
include_once "BaseModel.php";

class Accounts extends BaseModel
{
    public function __construct()
    {
        $this->tableName = "BankAccount";
    }

    public function readAccount($bankId = 0)
    {
        $stmt = $this->read("bank_account_id", $bankId);
        $bankAccountArray = array();

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {

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

    public function blockCard($bankAccountId)
    {
        $this->tableName = "Card";
        $name = "bank_account_id";
        $values = [
            "active" => 0
        ];
        $val = $this->update($name, $bankAccountId, $values);
        $this->tableName = "BankAccount";
        return $val;
    }

    public function readCard($cardId = 0)
    {
        $this->tableName = "Card";
        $stmt = $this->read("card_id", $cardId);
        $cardArray = array();

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {

            $cardItem = array(
                "card_id" => $row['card_id'],
                "active" => $row['active'],
                "expiration_data" => $row['expiration_data'],
                "bank_account_id" => $row['bank_account_id'],
            );
            array_push($cardArray, $cardItem);
        }
        $this->tableName = "BankAccount";
        http_response_code(200);
        return json_encode($cardArray);
    }

    public function createAccount($values)
    {

        return $this->create($values);
    }

    public function getLastBankAccountId()
    {
        return $this->getLastRecord('bank_account_id');
    }

    public function updateAccountBalance($id, $newBalance)
    {

        $name = "bank_account_id";
        $values = [
            "account_balance" => $newBalance
        ];
        $this->update($name, $id, $values);
    }

    public function deleteAccount()
    {

    }

}