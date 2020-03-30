<?php

include_once "../Database/DatabaseConnection.php";

class BaseModel
{
    public $tableName;

    protected function create($values){

        $query = "INSERT INTO " . $this->tableName . " SET ";

        $i = 0;
        foreach($values as $key => $value){
            if($i === 0){
                $query .= $key . "=:" . $i;
            }else{
                $query .= ", ". $key . "=:" . $i;
            }
            $i++;
        }

        $database = new DatabaseConnection();
        $database->getConnection();
        $stmt = $database->conn->prepare($query);

        array_map('changeValue', $values);
        function changeValue($val){
            htmlspecialchars(strip_tags($val));
        }

        $i = 0;
        foreach($values as $key => &$value){
            $index = ':'.$i;
            $stmt->bindParam($index,$value);
            $i++;
        }
        if($stmt->execute()){
            return true;
        }
        return false;
    }

    public function read($key ,$where){
        if($where == 0){
            $query = "SELECT * FROM " . $this->tableName;
        }else{
            $query = "SELECT * FROM" . $this->tableName . "WHERE " . $key . "=" . $where;
        }
        $database = new DatabaseConnection();
        $database->getConnection();

        $stmt = $database->conn->prepare($query);
        $stmt->execute();

        return $stmt;
    }

    protected function update(){

    }

    protected function delete(){

    }
}
?>