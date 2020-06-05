<?php
include_once '../../Models/Accounts.php';
include_once '../../Core/WebsocketClient/Websocket.php';
const GOSBANK_CLIENT_API_URL = "http://localhost:8080";
const BankCode = "DASB";
const CountryCode = "SO";

function parseAccountParts($account)
{
    $parts = explode('-', $account);
    return [
        'country' => $parts[0],
        'bank' => $parts[1],
        'account' => (int)$parts[2]
    ];
}

class TransactionController extends BaseController
{
    public $accountId;

    static function getMaxWidthDraw($accountId)
    {
        $accounts = new Accounts();
        $accountsValues = json_decode($accounts->readAccount($accountId));
        return $accountsValues[0]->account_balance;
    }

    static function withdraw($causer_account_id, $receiver_account_id, $amount, $pin)
    {
        $causerAccountParts = parseAccountParts($causer_account_id);
        $receiverAccountParts = parseAccountParts($receiver_account_id);

        if ($causerAccountParts['country'] === CountryCode && $causerAccountParts['bank'] === BankCode) {
            $causerAccountBalance = self::getMaxWidthDraw($causer_account_id);
            $resultCauser = $causerAccountBalance - $amount;
            if ($resultCauser >= 0) {
                $accounts = new Accounts();
                $accounts->updateAccountBalance($causer_account_id, $resultCauser);
            } else {
                return 404;
            }
        }

        if (($causerAccountParts['country'] == CountryCode && $causerAccountParts['bank'] == BankCode) && !($receiverAccountParts['country'] == CountryCode && $receiverAccountParts['bank'] == BankCode)) {
            $response = json_decode(file_get_contents(GOSBANK_CLIENT_API_URL . '/api/gosbank/transactions/create?from=' . $causer_account_id . '&to=' . $receiver_account_id . '&pin=' . $pin . '&amount=' . $amount));
            return $response->code;
        }

        //gets money from foreign bank
        if (!($causerAccountParts['country'] == CountryCode && $causerAccountParts['bank'] == BankCode) && ($receiverAccountParts['country'] == CountryCode && $receiverAccountParts['bank'] == BankCode)) {
            $response = json_decode(file_get_contents(GOSBANK_CLIENT_API_URL . '/api/gosbank/transactions/create?from=' . $causer_account_id . '&to=' . $receiver_account_id . '&pin=' . $pin . '&amount=' . $amount));
            $statusCode = $response->code;
            if ($statusCode !== 200) {
                return $statusCode;
            }
        }

        if ($receiverAccountParts['country'] === CountryCode && $receiverAccountParts['bank'] === BankCode) {
            $receiverAccountBalance = self::getMaxWidthDraw($receiver_account_id);
            $resultReceiver = $receiverAccountBalance + $amount;
            if ($resultReceiver >= 0) {
                $accounts = new Accounts();
                $accounts->updateAccountBalance($receiver_account_id, $resultReceiver);
                return 200;
            } else {
                return 404;
            }
        }
    }
}