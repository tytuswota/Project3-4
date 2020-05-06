<?php
include_once "../../Models/Transactions.php";
include_once "../../Controllers/AccountController.php";
include_once "../../Controllers/TransactionController.php";

date_default_timezone_set('Europe/Amsterdam');

$inputData = json_decode(file_get_contents("php://input"));

$amount = $inputData->amount;

$date_time = date("y/m/d G.i:s");

$val = [
    "date_time"=>$date_time,
    "amount"=>$amount,
    "causer_account_id"=>$inputData->receiver_account_id,
    "receiver_account_id"=>$inputData->causer_account_id
];

$trans = new Transactions();
$trans->createTransaction($val);

if(TransactionController::withdraw($inputData->receiver_account_id,$amount)){
    echo "withdraw successful";
}else{
    echo "could not withdraw";
}









