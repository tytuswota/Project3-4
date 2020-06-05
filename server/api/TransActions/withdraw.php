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
include_once '../../Core/WebsocketClient/Websocket.php';

use \Firebase\JWT\JWT;

$inputData = json_decode(file_get_contents("php://input"));

$jwt = isset($inputData->jwt) ? $inputData->jwt : "";

if ($jwt) {
    try {
        $decoded = JWT::decode($jwt, config::$key, array('HS256'));

        $amount = $inputData->amount;

        $date_time = date("y/m/d G.i:s");

        $val = [
            "date_time" => $date_time,
            "amount" => $amount,
            "receiver_account_id" => $inputData->receiver_account_id,
            "causer_account_id" => $inputData->causer_account_id
        ];

        $trans = new Transactions();
        $trans->createTransaction($val);

        $statusCode = TransactionController::withdraw($inputData->causer_account_id, $inputData->receiver_account_id, $amount, $inputData->pin);
        echo json_encode(array(
            "status" => $statusCode
        ));
    } catch (Exception $e) {
        echo json_encode(array(
            "message" => "Access denied.",
            "error" => $e->getMessage()
        ));
    }
} else {
    echo json_encode(array(
        "message" => "Access denied.",
        "error" => "no token"
    ));
}









