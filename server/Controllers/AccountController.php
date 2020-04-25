<?php
include_once "../../Models/Accounts.php";
include_once  "CardsController.php";
include_once "BaseController.php";

class AccountController extends BaseController
{
    static function createBankAccount($values,$userId){

        $valArray = [
                "bank_account_id"=>$values->bank_account_id,
                "account_balance"=>$values->account_balance,
                "type"=>$values->type,
                "start_date"=>$values->start_date,
                "user_id"=>$userId];


        $accounts = new Accounts();
        $accountId = $accounts->createAccount($valArray);

        $cardData = $values->card_data;
        return CardsController::createCard($cardData, $accountId);
    }


}