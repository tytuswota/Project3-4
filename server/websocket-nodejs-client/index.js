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

const wss = new WebSocket.Server({port: process.env.Port || 3000});

var dataFromPhp = "";

wss.on('connection', function(ws){

    ws.on('message', function(message){
        dataFromPhp = message;
        connectToGosbank();
    });


});

function parseAccountParts(account) {
    return {
        country: account.substring(0, 2),
        bank: account.substring(3, 7),
        account: parseInt(account.substring(8))
    };
}

function connectToGosbank(message) {
    const ws = new WebSocket(LOCAL_DEBUG_MODE ? 'ws://localhost:8080' : 'wss://ws.gosbank.ml/');
    const pendingCallbacks = [];
    const data = message;

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

        if(type == 'balance'){
            const {type, account, pin} = JSON.parse(dataFromPhp);
        }

        if(type == 'payment'){
            const {type, fromAccount, toAccount, pin, amount} = JSON.parse(dataFromPhp);
        }

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
                            console.log('Balance account ' + account + ': ' + data.body.balance);
                        }
                        else {
                            console.log('Balance error: ' + data.body.code);
                        }
                    });
                }

                if(type == 'transaction'){
                    requestPayment(fromAccount, toAccount, pin, amount, function(){
                        if(data.body.code == 200){
                            console.log('Payment accepted');
                        }else{
                            console.log('Payment error: ' + data.body.code);
                        }
                    });
                }

                /*var i = 0;

                setInterval(function () {
                    let account = 'SU-DASB-00000001';
                    let pin = '1234';
                    requestBalance(account, pin, function (data) {
                        if (data.body.code === 200) {
                            console.log('Balance account ' + account + ': ' + data.body.balance);
                        }
                        else {
                            console.log('Balance error: ' + data.body.code);
                        }
                    });

                    requestPayment(
                        'SU-DASB-00000001',
                        'SU-DASB-00000002',
                        '1234', 10,function(){
                        if (data.body.code === 200) {
                            console.log('Payment accepted');
                        }
                        else {
                            console.log('Payment error: ' + data.body.code);
                        }
                    });
                }, Math.random() * 1000 + 500);*/
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

        if(type == 'transaction'){
            console.log('Transaction request from: ' + data.body.account);

            let account = data.body.account;
            let pin = data.body.pin;
            let session = new DasbankSession.DasbankSession(account, pin);

            setTimeout(()=>{
                session.createTransAction();
            });
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
