<?php
include_once ("../Models/User.php");
include_once "AccountController.php";
include_once "BaseController.php";


class UserController extends BaseController
{
    public function makeUser($userData){
        //content needs to be checked first

        $values = [
            "first_name" => $userData->first_name,
            "date_of_birth" => $userData->date_of_birth,
            "prefix" => $userData->prefix,
            "surname" => $userData->surname,
            "country" => $userData->country
        ];

        $user = new User();
        $userId = $user->createUser($values);
        AccountController::createBankAccount($userData->account_data, $userId);

        //create bank account
    }
}