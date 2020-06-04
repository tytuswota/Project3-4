<?php
include_once '../../Models/Accounts.php';
include_once '../../Core/WebsocketClient/Websocket.php';

const BankCode = "DASB";

class TransactionController extends BaseController
{
    public $accountId;

    static function getMaxWidthDraw($accountId){
        $accounts = new Accounts();
        $accountsValues = json_decode($accounts->readAccount($accountId));
        return $accountsValues[0]->account_balance;
    }

    static function withdraw($causer_account_id,$receiver_account_id, $amount,$pin){

        if(strpos($causer_account_id, BankCode) !== false){
            $causerAccountBalance = self::getMaxWidthDraw($causer_account_id);
            $resultCauser = $causerAccountBalance - $amount;
            if($resultCauser >= 0){
                $accounts = new Accounts();
                $accounts->updateAccountBalance($causer_account_id,$resultCauser);
            }else{
                return false;
            }
        }

        if(strpos($causer_account_id, BankCode) === false || strpos($receiver_account_id, BankCode) === false){
            $webSocketClient = new Websocket();

            /*$jsonForGos = json_encode(array(
                "type"=>"payment",
                "toAccount"=>$receiver_account_id,
                "fromAccount"=>$causer_account_id,
                "pin"=>$pin,
                "amount"=>$amount
            ));

            $response = $webSocketClient->sendToclient($jsonForGos);*/

            $response = json_decode(file_get_contents(GOSBANK_CLIENT_API_URL . '/gosbank/transactions/create?from=' . $causer_account_id . '&to=' . $receiver_account_id . '&pin=' . $pin . '&amount=' . $amount));
            echo $response;
        }

        if(strpos($receiver_account_id, BankCode) !== false){
            $receiverAccountBalance = self::getMaxWidthDraw($receiver_account_id);
            $resultReceiver = $receiverAccountBalance + $amount;
            if($resultReceiver >= 0){
                $accounts = new Accounts();
                $accounts->updateAccountBalance($receiver_account_id,$resultReceiver);
                return true;
            }else{
                return false;
            }
        }
    }
}