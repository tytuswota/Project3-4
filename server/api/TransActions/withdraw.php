<?php
include_once "../../Models/Transactions.php";
include_once "../../Controllers/AccountController.php";
include_once "../../Controllers/TransactionController.php";

date_default_timezone_set('Europe/Amsterdam');

include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
include_once '../../libs/php-jwt-master/src/ExpiredException.php';
include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
include_once '../../libs/php-jwt-master/src/JWT.php';
include_once '../Config.php';
use \Firebase\JWT\JWT;

$inputData = json_decode(file_get_contents("php://input"));

$jwt=isset($inputData->jwt) ? $inputData->jwt : "";

if($jwt){
    try{
        $decoded = JWT::decode($jwt, config::$key, array('HS256'));

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
    }catch (Exception $e){
        echo json_encode(array(
            "message" => "Access denied.",
            "error" => $e->getMessage()
        ));
    }
}else{
    echo "error no token";
}









