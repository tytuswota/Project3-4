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

    static function withdraw($accountId, $amount){
        $accountBalance = self::getMaxWidthDraw($accountId);
        if($amount >= 0){
            $result = $accountBalance - $amount;

            if($result > 0){
                $accounts = new Accounts();
                $accounts->updateAccountBalance($accountId,$result);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
}