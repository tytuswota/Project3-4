<?php
//login with pin
include_once "../../Controllers/LoginController.php";
include_once '../../Database/DatabaseConnection.php';
include_once "../../Models/Cards.php";

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

error_reporting(E_ALL);
ini_set('display_errors', 1);



$incomingLoginData = json_decode(file_get_contents("php://input"));

LoginController::login($incomingLoginData);
//card id
//pin