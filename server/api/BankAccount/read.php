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
include_once '../config.php';
use \Firebase\JWT\JWT;

$accounts = new Accounts();

http_response_code(200);
$inputData = json_decode(file_get_contents("php://input"));

$jwt=isset($inputData->jwt) ? $inputData->jwt : "";
//this should be somewhere else

if($jwt){
    try {
        // decode jwt
        $decoded = JWT::decode($jwt, config::$key, array('HS256'));
        echo "hello is this working?";

        if(empty($inputData)){
            echo json_encode($accounts->readAccount());
        }else{
            $accountId = $inputData->account_id;
            $account = json_decode($accounts->readAccount($accountId));

            echo json_encode($account[0]);
        }

        // set user property values here
    }catch (Exception $e){
        echo json_encode(array(
            "message" => "Access denied.",
            "error" => $e->getMessage()
        ));
    }
}






