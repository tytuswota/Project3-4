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

http_response_code(201);
$card = json_decode($accounts->readCard($incomingData->card_id));
echo json_encode($card[0]);
