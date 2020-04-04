<?php
include_once "BaseModel.php";

class User extends BaseModel
{
    public function __construct(){
        $this->tableName = "User";
    }

    public function readUser($userId = 0){
        $stmt = $this->read("user_id", $userId);
        $usersArray = array();

        while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            $usersItem = array(
                "User_id" => $row['User_id'],
                "date_of_birth" => $row['date_of_birth'],
                "first_name" => $row['first_name'],
                "prefix" => $row['prefix'],
                "surname" => $row['surname'],
                "country" => $row['country']
            );
            array_push($usersArray, $usersItem);

            http_response_code(200);
            return json_encode($usersArray);
        }
    }

    public function createUser($values){
        return $this->create($values);
    }
}