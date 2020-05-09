<?php
include_once '../../Database/DatabaseConnection.php';
include_once "../../Models/Accounts.php";

// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$incomingData = json_decode(file_get_contents("php://input"));
$accounts = new Accounts();

if($accounts->blockCard($incomingData->bank_account_id)){
    http_response_code(201);
    echo json_encode(array("message"=>"card has been block"));
}else{
    http_response_code(503);
    echo json_encode(array("message"=>"failed to block account"));
}



