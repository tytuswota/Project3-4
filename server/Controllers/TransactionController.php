<?php
include_once '../../Models/Accounts.php';


class TransactionController extends BaseController
{
    public $accountId;

    static function getMaxWidthDraw($accountId){
        $accounts = new Accounts();
        $accountsValues = json_decode($accounts->readAccount($accountId));
        return $accountsValues[0]->account_balance;
    }

    static function withdraw($causer_account_id,$receiver_account_id, $amount){
        $causerAccountBalance = self::getMaxWidthDraw($causer_account_id);
        $receiverAccountBalance = self::getMaxWidthDraw($receiver_account_id);

        if($amount > 0){
            $resultCauser = $causerAccountBalance - $amount;
            $resultReceiver = $receiverAccountBalance + $amount;

            if($resultCauser >= 0 && $resultReceiver >= 0){
                $accounts = new Accounts();
                $accounts->updateAccountBalance($causer_account_id,$resultCauser);
                $accounts->updateAccountBalance($receiver_account_id,$resultReceiver);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
}