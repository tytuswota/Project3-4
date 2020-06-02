// ########### CLIENT CONFIG ###########

// Connect to local running Gosbank server
const LOCAL_DEBUG_MODE = true;

// Your country code always 'SU'
const COUNTRY_CODE = 'SU';

// Your bank code
const BANK_CODE = process.argv[2] || "DASB";

// When disconnect try to reconnect timeout (in ms)
const RECONNECT_TIMEOUT = 2 * 1000;

// ########### CLIENT CODE ###########

const WebSocket = require('ws');
const DasbankSession = require('./DasbankSession.js')

const wsServer = new WebSocket.Server({port: process.env.Port || 3000});

var dataFromPhp = "";

wsServer.on('connection', function(wss){
    wss.on('message', function(message){

        dataFromPhp = message;
        connectToGosbank(wss);
    });


});

function parseAccountParts(account) {
    return {
        country: account.substring(0, 2),
        bank: account.substring(3, 7),
        account: parseInt(account.substring(8))
    };
}

function connectToGosbank(wss) {
    const ws = new WebSocket(LOCAL_DEBUG_MODE ? 'ws://localhost:8080' : 'wss://ws.gosbank.ml/');
    const pendingCallbacks = [];

    function requestMessage(type, data, callback) {
        console.log("in request message");
        const id = Date.now();
        if (callback !== undefined) {
            pendingCallbacks.push({ id: id, type: type + '_response', callback: callback });
        }
        ws.send(JSON.stringify({ id: id, type: type, data: data }));
    }

    function responseMessage(id, type, data) {
        console.log("in response message");
        ws.send(JSON.stringify({ id: id, type: type + '_response', data: data }));
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
        console.log("in the open ws function");
        const {type, account, fromAccount, toAccount, pin, amount} = JSON.parse(dataFromPhp);

        /*console.log("type: " + type);
        console.log("accountId: " + account);
        console.log("pin: " + pin);*/

        requestMessage('register', {
            header: {
                originCountry: COUNTRY_CODE,
                originBank: BANK_CODE,
                receiveCountry: 'SU',
                receiveBank: 'GOSB'
            },
            body: {}
        }, function (data) {
            if (data.body.code === 200) {
                console.log('Connected with Gosbank with bank code: ' + BANK_CODE);

                if(type === 'balance'){
                    requestBalance(account, pin, function (data) {
                        if (data.body.code === 200) {
                            var json = new Object();
                            json.status = data.body.code;
                            json.dataType = "balance";
                            json.account = account;
                            json.balance = data.body.balance;
                            wss.send(JSON.stringify(json));
                        }
                        else {
                            var json = new Object();
                            json.status = data.body.code;
                            json.dataType = "balance error"
                            wss.send(JSON.stringify(json));
                        }
                    });
                }

                //receiver acount id = to_acount
                //causer account id = from_account

                if(type == 'payment'){
                    requestPayment(fromAccount, toAccount, pin, amount, function(data){
                        if(data.body.code == 200){
                            console.log('Payment accepted');
                        }else{
                            console.log('Payment error: ' + data.body.code);
                        }
                    });
                }

            }
            else {
                console.log('Error with connecting to Gosbank, reason: ' + data.body.code);
            }
        });
    });

    ws.on('message', function (message) {
        const { id, type, data } = JSON.parse(message);

        for (var i = 0; i < pendingCallbacks.length; i++) {
            if (pendingCallbacks[i].id === id && pendingCallbacks[i].type === type) {
                pendingCallbacks[i].callback(data);
                pendingCallbacks.splice(i--, 1);
            }
        }

        if(type == 'payment'){

            let account = data.body.fromAccount;
            let pin = data.body.pin;
            let session3 = new DasbankSession.DasbankSession(account, pin);

            setTimeout(()=>{
                session3.createTransAction(data.body.amount,data.body.toAccount,data.body.fromAccount,function(data){
                    responseMessage(id, 'balance', {
                            header: {
                                originCountry: COUNTRY_CODE,
                                originBank: BANK_CODE,
                                receiveCountry: data.header.originCountry,
                                receiveBank: data.header.originBank
                            },
                            body: {
                                code: 200
                            }
                        }
                    );
                });
            }, 2000);
        }

        if (type === 'balance') {
            console.log('Balance request for: ' + data.body.account);
            //todo
            let account = data.body.account;
            let pin = data.body.pin;
            let session2 = new DasbankSession.DasbankSession(account,pin);

            setTimeout(()=> {
                session2.getBalance(function (balance) {
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
            }, 2000)


        }

        if (type === 'payment') {
            console.log('Payment request for: ' + data.body.toAccount);

            // Add payment to database

            setTimeout(function () {
                responseMessage(id, 'payment', {
                    header: {
                        originCountry: COUNTRY_CODE,
                        originBank: BANK_CODE,
                        receiveCountry: data.header.originCountry,
                        receiveBank: data.header.originBank
                    },
                    body: {
                        code: 200
                    }
                });
            }, Math.random() * 2000 + 500);
        }
    });

    ws.on('close', function () {
        console.log('Disconnected, try to reconnect in ' + (RECONNECT_TIMEOUT / 1000).toFixed(0) + ' seconds!');
        setTimeout(connectToGosbank, RECONNECT_TIMEOUT);
    });

    // Ingnore connecting errors reconnect in the close handler
    ws.on('error', function (error) {});
}

//connectToGosbank();
