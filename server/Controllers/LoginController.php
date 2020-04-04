<?php
include_once "../../Models/Cards.php";
include_once "../../Models/Accounts.php";

class LoginController
{

    static function login($loginData){
       $account = new Accounts();
       $cards = new Cards();

       $givenPin = $loginData->pin;
       $card = json_decode($cards->readCard($loginData->card_id));
       $hashedPin = $card[0]->pin;

       if(password_verify($givenPin,$hashedPin)){
           echo "logged in";
       }else{
           echo "wrong pin";
       }


    }


}