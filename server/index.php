<?php
define('PROJECT_ROOT_PATH', __DIR__);

$request = $_SERVER['REQUEST_URI'];

echo $request;

switch ($request) {
 case "/api/";
    break;
}