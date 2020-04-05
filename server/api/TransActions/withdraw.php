<?php
include_once "../../Models/Transactions.php";
include_once "../../Controllers/AccountController.php";
include_once "../../Controllers/TransactionController.php";

date_default_timezone_set('Europe/Amsterdam');
$inputData = json_decode(file_get_contents("php://input"));

$account_id = $inputData->account_id;
$amount = $inputData->amount;
$date_time = date("m/d/y G.i:s");
$val = [
    "date_time"=>$date_time,
    "amount"=>$amount,
    "causer_account_id"=>$account_id,
    "receiver_account_id"=>$account_id
];

$trans = new Transactions();
$trans->createTransaction($val);

if(TransactionController::withdraw($account_id,$amount)){
    echo "withdraw successful";
}else{
    echo "could not withdraw";
}









