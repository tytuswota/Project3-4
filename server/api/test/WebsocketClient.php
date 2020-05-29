<?php
require "otherlib/BadOpcodeException.php";
require "otherlib/BadUriException.php";
require "otherlib/Base.php";
require "otherlib/ConnectionException.php";
require "otherlib/Client.php";
require "otherlib/Server.php";

use WebSocket\Client;
echo "start \n";

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


$client = new Client("ws://localhost:3000");
$incomingLoginData = file_get_contents("php://input");
$client->send($incomingLoginData);
echo "end \n";