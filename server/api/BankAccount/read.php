<?php
include_once '../../Database/DatabaseConnection.php';
include_once "../../Models/Accounts.php";

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
include_once '../../libs/php-jwt-master/src/ExpiredException.php';
include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
include_once '../../libs/php-jwt-master/src/JWT.php';
include_once '../Config.php';
include_once '../../Core/WebsocketClient/Websocket.php';

use \Firebase\JWT\JWT;

const BankCode = "DASB";

$accounts = new Accounts();

error_reporting(E_ALL);
ini_set('display_errors', 1);

http_response_code(200);
$inputData = json_decode(file_get_contents("php://input"));

$jwt=isset($inputData->jwt) ? $inputData->jwt : "";
//this should be somewhere else

if($jwt){
    if(strpos($inputData->account_id, BankCode) !== false){
        try {
            // decode jwt
            $decoded = JWT::decode($jwt, config::$key, array('HS256'));

            if(empty($inputData)){
                echo json_encode($accounts->readAccount());
            }else{
                $accountId = $inputData->account_id;
                $account = json_decode($accounts->readAccount($accountId));
                $account[0]->status = 200;
                echo json_encode($account[0]);
            }

            // set user property values here
        }catch (Exception $e){
            echo json_encode(array(
                "status" => "404",
                "message" => "Access denied.",
                "error" => $e->getMessage()
            ));
        }
    }else{
        $webSocketClient = new Websocket();

        $jsonForGos = json_encode(array(
            "type"=>"balance",
            "account"=>$inputData->card_id,
            "pin"=>$inputData->pin
        ));

        $response = $webSocketClient->sendToclient($jsonForGos);

        echo json_encode(
            array(
                "data" => array(
                    "bank_account_id" => $response->account
                ),
                "status" => $response->status,
                "account_balance" => $response->balance
            )
        );
    }

}






