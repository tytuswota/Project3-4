<?php

//for the database connection.
class DatabaseConnection
{
    private $hostName;
    private $dbName;
    private $userName;
    private $password;
    public $conn;

    public function getConnection(){
        $cred = json_decode(file_get_contents("../../database.json"));
        $this->hostName = $cred->hostNmae;
        $this->dbName = $cred->dbName;
        $this->userName = $cred->userName;
        $this->password = $cred->password;

        $this->conn = null;

        try{
            $this->conn = new PDO("mysql:host=" . $this->hostName . ";dbname=". $this->dbName,$this->userName,$this->password);
            $this->conn->exec("set names utf8");
        }catch (PDOException $e){
            echo "Database connection problem " . $e->getMessage();
        }
    }


}