<?php
include_once "../../Models/Accounts.php";
include_once  "CardsController.php";
include_once "BaseController.php";

class AccountController extends BaseController
{
    static function createBankAccount($values,$userId){

        //account id needs to be gen
        $valArray = [
                "bank_account_id"=>$values->bank_account_id,
                "account_balance"=>$values->account_balance,
                "type"=>$values->type,
                "start_date"=>$values->start_date,
                "user_id"=>$userId];


        $accounts = new Accounts();

        $lastRecordStmt = $accounts->getLastBankAccountId();
        $row = $lastRecordStmt->fetch(PDO::FETCH_ASSOC);

        if(!empty($row)){

            $lastAccountId  = $row['bank_account_id'];
            $accountNumbers = substr($lastAccountId, -8);
            $accountNumbers++;
            $newAccountId = substr($lastAccountId, 0, 8);

        }else{
            $newAccountId = "SU-DASB-";
            $accountNumbers = 1;
        }

        for($x = 8 - strlen((string)($accountNumbers)); $x > 0; $x--){
            $newAccountId .= "0";
        }

        $newAccountId .= $accountNumbers;
        $valArray["bank_account_id"] = $newAccountId;

        if($accounts->createAccount($valArray) != false){
            $cardData = $values->card_data;
            print_r($newAccountId);
            return CardsController::createCard($cardData, $newAccountId);
        }
        //select last id

    }


}