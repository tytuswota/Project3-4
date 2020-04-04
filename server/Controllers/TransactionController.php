<?php
include_once '../../Models/Accounts.php';

class TransactionController extends BaseController
{
    static function getMaxWidthDraw($accountId){
        $accounts = new Accounts();
        $accountsValues = json_decode($accounts->readAccount($accountId));

        print_r($accountsValues);
    }
}