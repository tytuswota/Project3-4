<?php

require "otherlib/BadOpcodeException.php";
require "otherlib/BadUriException.php";
require "otherlib/Base.php";
require "otherlib/ConnectionException.php";
require "otherlib/Client.php";
require "otherlib/Server.php";

use WebSocket\Client;

class Websocket
{
    function sendToclient($data){
        //echo "hey";

        //print_r($data);
        $client = new Client("ws://localhost:3000");
        $client->send($data);
        $obj = false;
        try{
            $message = $client->receive();
            $obj = json_decode($message);
        }catch (\WebSocket\ConnectionException $e){
            echo $e;
        }

        $client->close();
        //
        //echo $client->receive();
        return $obj;
    }
}