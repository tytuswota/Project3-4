<?php

// Fetch the translation from th database.
include_once "../../Models/LanguageSystem.php";

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


$incomingLoginData = json_decode(file_get_contents("php://input"));
if(isset($incomingLoginData->id)){
    $result = json_encode(LanguageSystem::getTranslation($incomingLoginData));
}else{
    $result = new stdClass();
    $result = json_encode($result->id="error");
}
echo $result;


