<?php
include_once "../../Models/Cards.php";
include_once "../../Models/Accounts.php";

include_once '../../libs/php-jwt-master/src/BeforeValidException.php';
include_once '../../libs/php-jwt-master/src/ExpiredException.php';
include_once '../../libs/php-jwt-master/src/SignatureInvalidException.php';
include_once '../../libs/php-jwt-master/src/JWT.php';
include_once '../Config.php';
include_once '../../Core/WebsocketClient/Websocket.php';

use \Firebase\JWT\JWT;

const BankCode = "DASB";

const GOSBANK_CLIENT_API_URL = "http://localhost:8080";

class LoginController
{
    static function login($loginData){
        //sendToclient($loginData);
        // show error reporting
        error_reporting(E_ALL);

        // set your default time-zone
        date_default_timezone_set('Europe/Amsterdam');

       //should be in a config file
        $iat = 1356999524;
        $nbf = 1357000000;

        $iss = "http://dasbank.ml/";

       $account = new Accounts();
       $cards = new Cards();

           if(strpos($loginData->card_id, BankCode) !== false){
               $givenPin = $loginData->pin;
               //need to check if there is a card
               $card = json_decode($cards->readCard($loginData->card_id));
               $hashedPin = $card[0]->pin;
               $active = $card[0]->active;

               if(password_verify($givenPin,$hashedPin)){
                   if($active != 0){
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

                       $jwt = JWT::encode($token,config::$key);

                       echo json_encode(
                           array(
                               "data" => $userData[0],
                               "status" => 200,
                               "jwt" => $jwt,
                           )
                       );

                       //echo "logged in";
                   }else{
                       echo json_encode(
                           array(
                               "data" => array(
                                   "bank_account_id" => $loginData->card_id
                               ),
                               "jwt" => "",
                               "status" => 403
                           ));
                   }
               }else{
                   echo json_encode(
                       array(
                           "data" => array(
                               "bank_account_id" => $loginData->card_id
                           ),
                           "jwt" => "",
                           "status" => 401
                       ));
               }
           }else{
              /* $webSocketClient = new Websocket();

               $jsonForGos = json_encode(array(
                   "type"=>"balance",
                   "account"=>$loginData->card_id,
                   "pin"=>$loginData->pin
               ));*/

               //$response = $webSocketClient->sendToclient($jsonForGos);
               $response = json_decode(file_get_contents(GOSBANK_CLIENT_API_URL + '/gosbank/accounts/' . $loginData->card_id . '?pin=' . $loginData->pin));
               if($response->status !== 400){
                   $token = array(
                       "iss" => $iss,
                       "iat" => $iat,
                       "nbf" => $nbf,
                       "data" => array(
                           "id" => $response->account,
                           "user_id" => $response->account
                       )
                   );

                   $jwt = JWT::encode($token,config::$key);

                   echo json_encode(
                       array(
                           "data" => array(
                               "bank_account_id" => $response->account
                           ),
                           "status" => $response->status,
                           "jwt" => $jwt
                       )
                   );

               }else{
                   echo json_encode(
                       array(
                           "data" => $loginData->card_id,
                           "jwt" => "",
                           "status" => 401
                       )
                   );
               }
           }
    }

}