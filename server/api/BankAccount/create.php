<?php
include_once '../../Database/DatabaseConnection.php';
include_once "../../Models/Accounts.php";

// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
include_once '../../libs/php-jwt-master/src/ExpiredException.php';
include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
include_once '../../libs/php-jwt-master/src/JWT.php';
include_once '../config.php';
use \Firebase\JWT\JWT;

$incomingData = json_decode(file_get_contents("php://input"));
$accounts = new Accounts();

$jwt=isset($inputData->jwt) ? $inputData->jwt : "";

if($jwt){
    try{

        $decoded = JWT::decode($jwt, config::$key, array('HS256'));

        $valArray = [
            "account_balance"=>$incomingData->account_balance,
            "type"=>$incomingData->type,
            "start_data"=>$incomingData->start_date,
            "user_id"=>$incomingData->user_id];

        if($accounts->createAccount($incomingData)){
            http_response_code(201);
            echo json_encode(array("message"=>"account has been made"));
        }else{
            http_response_code(503);
            echo json_encode(array("message"=>"failed to make account"));
        }
    }catch (Exception $e){
        echo json_encode(array(
            "message" => "Access denied.",
            "error" => $e->getMessage()
        ));
    }
}

