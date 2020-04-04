<?php
include_once "../../Controllers/UserController.php";
include_once '../../Database/DatabaseConnection.php';
include_once  "../../Models/User.php";

// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$incomingData = json_decode(file_get_contents("php://input"));
if(UserController::makeUser($incomingData)){
    http_response_code(201);
    echo json_encode(array("message"=>"account has been made"));
}else{
    http_response_code(503);
    echo json_encode(array("message"=>"failed to make account"));
}