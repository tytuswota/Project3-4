// ########### CLIENT CONFIG ###########

// Connect to local running Gosbank server
const LOCAL_DEBUG_MODE = false;

// Your country code always 'SO'
const COUNTRY_CODE = 'SO';

// Your bank code
const BANK_CODE = process.argv[2] || "DASB";

// When disconnect try to reconnect timeout (in ms)
const RECONNECT_TIMEOUT = 2 * 1000;

// ########### CLIENT CODE ###########

const WebSocket = require('ws');
const DasbankSession = require('./DasbankSession.js')
var http = require('http');

var dataFromPhp = "";

function parseAccountParts(account) {
    return {
        country: account.substring(0, 2),
        bank: account.substring(3, 7),
        account: parseInt(account.substring(8))
    };
}

function connectToGosbank(wss) {
    http.createServer((req, res) => {dasbankRequestHandler(req, res)}).listen(8080); //the server object listens on port 8080
    const ws = new WebSocket(LOCAL_DEBUG_MODE ? 'ws://localhost:8080' : 'wss://ws.gosbank.ml/');
    const pendingCallbacks = [];

    function requestMessage(type, data, callback) {
        console.log("in request message");
        const id = Date.now();
        if (callback !== undefined) {
            pendingCallbacks.push({id: id, type: type + '_response', callback: callback});
        }
        ws.send(JSON.stringify({id: id, type: type, data: data}));
    }

    function responseMessage(id, type, data) {
        console.log("in response message");
        ws.send(JSON.stringify({id: id, type: type + '_response', data: data}));
    }

    function requestBalance(account, pin, callback) {
        const toAccountParts = parseAccountParts(account);

        requestMessage('balance', {
            header: {
                originCountry: COUNTRY_CODE,
                originBank: BANK_CODE,
                receiveCountry: toAccountParts.country,
                receiveBank: toAccountParts.bank
            },
            body: {
                account: account,
                pin: pin
            }
        }, callback);
    }

    function requestPayment(fromAccount, toAccount, pin, amount, callback) {
        const formAccountParts = parseAccountParts(fromAccount);
        const toAccountParts = parseAccountParts(toAccount);


        if (formAccountParts.bank !== BANK_CODE) {
            requestMessage('payment', {
                header: {
                    originCountry: COUNTRY_CODE,
                    originBank: BANK_CODE,
                    receiveCountry: formAccountParts.country,
                    receiveBank: formAccountParts.bank
                },
                body: {
                    fromAccount: fromAccount,
                    toAccount: toAccount,
                    pin: pin,
                    amount: amount
                }
            }, callback);
        }

        if (toAccountParts.bank !== BANK_CODE) {
            requestMessage('payment', {
                header: {
                    originCountry: COUNTRY_CODE,
                    originBank: BANK_CODE,
                    receiveCountry: toAccountParts.country,
                    receiveBank: toAccountParts.bank
                },
                body: {
                    fromAccount: fromAccount,
                    toAccount: toAccount,
                    pin: pin,
                    amount: amount
                }
            }, callback);
        }
    }

    ws.on('open', function () {
        let toSent = {
            "id": 1586944886509,
            "type": "register",
            "data": {
                "header": {
                    "originCountry": "SO",
                    "originBank": "DASB",
                    "receiveCountry": "SO",
                    "receiveBank": "GOSB"
                },
                "body": {}
            }
        }
        ws.send(JSON.stringify(toSent));

    });

    ws.on('message', function (message) {

        const {id, type, data} = JSON.parse(message);

        for (var i = 0; i < pendingCallbacks.length; i++) {
            if (pendingCallbacks[i].id === id && pendingCallbacks[i].type === type) {
                pendingCallbacks[i].callback(data);
                pendingCallbacks.splice(i--, 1);
            }
        }

        if (data.body.account != undefined || data.body.toAccount) {
            requestToDasbank(id, type, data);
        }
    });

    function requestToDasbank(id, type, data) {
        let account = data.body.account;
        if (account === undefined){
            account = data.body.fromAccount;
        }
        let pin = data.body.pin;
        let session = new DasbankSession.DasbankSession(account, pin,
            function (code) {

                if (code === '400') {
                    responseMessage(id, type, {
                            header: {
                                originCountry: COUNTRY_CODE,
                                originBank: BANK_CODE,
                                receiveCountry: data.header.originCountry,
                                receiveBank: data.header.originBank
                            },
                            body: {
                                code: 400
                            }
                        }
                    );
                    return;
                }
                if (type == 'payment') {
                    session.createTransAction(data.body.amount, data.body.toAccount, data.body.fromAccount, function (result) {
                        responseMessage(id, 'payment', {
                                header: {
                                    originCountry: COUNTRY_CODE,
                                    originBank: BANK_CODE,
                                    receiveCountry: data.header.originCountry,
                                    receiveBank: data.header.originBank
                                },
                                body: {
                                    code: result
                                }
                            }
                        );
                    });
                }

                if (type === 'balance') {
                    console.log('Balance request for: ' + data.body.account);

                    session.getBalance(function (balance) {
                        responseMessage(id, 'balance', {
                                header: {
                                    originCountry: COUNTRY_CODE,
                                    originBank: BANK_CODE,
                                    receiveCountry: data.header.originCountry,
                                    receiveBank: data.header.originBank
                                },
                                body: {
                                    code: 200,
                                    balance: parseFloat(parseInt(balance).toFixed(2))
                                }
                            }
                        );
                    });
                }
            });
    }

    function dasbankRequestHandler(req, res) {
        console.log("in the open ws function");

        if (req.method === 'POST') {
            let body = '';
            req.on('data', chunk => {
                body += chunk.toString(); // convert Buffer to string
            });
            req.on('end', () => {
                console.log(body);
                ws.send(body);
                res.end('ok');
            });
        }
    }
}

connectToGosbank();
