<?php
include_once '../../Database/DatabaseConnection.php';
include_once "../../Models/Accounts.php";

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$accounts = new Accounts();

/*http_response_code(200);*/
$inputData = json_decode(file_get_contents("php://input"));

if(empty($inputData)){
    echo json_encode($accounts->readAccount());
}else{
    $accountId = $inputData->account_id;
    echo $accountId;
}





