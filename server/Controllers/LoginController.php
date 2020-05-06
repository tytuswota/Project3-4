<?php
include_once "../../Models/Cards.php";
include_once "../../Models/Accounts.php";

include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
include_once '../../libs/php-jwt-master/src/ExpiredException.php';
include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
include_once '../../libs/php-jwt-master/src/JWT.php';

use \Firebase\JWT\JWT;

class LoginController
{

    static function login($loginData){

        // show error reporting
        error_reporting(E_ALL);

        // set your default time-zone
        date_default_timezone_set('Europe/Amsterdam');

       //should be in a config file
        $key = "test";
        $iat = 1356999524;
        $nbf = 1357000000;

        $iss = "http://dasbank.ml/";

       $account = new Accounts();
       $cards = new Cards();

       $givenPin = $loginData->pin;
       $card = json_decode($cards->readCard($loginData->card_id));
       $hashedPin = $card[0]->pin;


       if(password_verify($givenPin,$hashedPin)){

           $userData = json_decode($account->readAccount($loginData->card_id));

           $token = array(
               "iss" => $iss,
               "iat" => $iat,
               "nbf" => $nbf,
               "data" => array(
                   "id" => $userData[0]->bank_account_id,
                   "user_id" => $userData[0]->user_id,
                   "start_date" => $userData[0]->start_date
               )
           );

           $jwt = JWT::encode($token,$key);
           echo json_encode(
               array(
                "message" => $userData[0],
                "jwt" => $jwt
               )
           );

           //echo "logged in";
       }else{
           echo "wrong pin";
       }


    }


}