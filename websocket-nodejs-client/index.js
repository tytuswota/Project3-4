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

const http = require('http'); //TODO make https
const WebSocket = require('ws');

function parseAccountParts(account) {
    return {
        country: account.substring(0, 2),
        bank: account.substring(3, 7),
        account: parseInt(account.substring(8))
    };
}

function connectToGosbank() {
    const ws = new WebSocket(LOCAL_DEBUG_MODE ? 'ws://localhost:8080' : 'wss://ws.gosbank.ml/');

    const pendingCallbacks = [];

    function requestMessage(type, data, callback) {
        const id = Date.now();
        if (callback !== undefined) {
            pendingCallbacks.push({ id: id, type: type + '_response', callback: callback });
        }
        ws.send(JSON.stringify({ id: id, type: type, data: data }));
    }

    function responseMessage(id, type, data) {
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

                var i = 0;
                setInterval(function () {
                    var q = i++;

                    requestBalance('SU-DASB-00000001', '1234', function (data) {
                        if (data.body.code === 200) {
                            console.log('Balance account ' + q + ': ' + data.body.balance);
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
                }, 1000);
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

        if (type === 'balance') {
            console.log('Balance request for: ' + data.body.account);




             // Fetch balance info from database

            setTimeout(function () {
                responseMessage(id, 'balance', {
                    header: {
                        originCountry: COUNTRY_CODE,
                        originBank: BANK_CODE,
                        receiveCountry: data.header.originCountry,
                        receiveBank: data.header.originBank
                    },
                    body: {
                        code: 200,
                        balance: parseFloat((123).toFixed(2))
                    }
                });
            }, Math.random() * 2000 + 500);
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

// function getBalance(bankAccount) {
//     data = ''
//     // todo change port and protecol to https
//     const options = {
//         hostname: 'dasbank.ml',
//         port: 80,
//         path: 'http://dasbank.ml/api/BankAccount/read.php',
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//             'Content-Length': data.length
//         }
//     }
//
//     const req = http.request(options, res => {
//         console.log(`balance: ${res.balance}`);
//
//         res.on('data', d => {
//             process.stdout.write(d)
//         })
//     });
//
//     req.on('error', error => {
//         console.error(error)
//     });
//
//     req.write(data);
//     req.end();
// }
//
// function login() {
//     data = ''
//     // todo change port and protecol to https
//     const options = {
//         hostname: 'dasbank.ml',
//         port: 80,
//         path: 'http://dasbank.ml/api/Login/login.php"',
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//             'Content-Length': data.length
//         }
//     }
//
//     const req = http.request(options, res => {
//         console.log(`balance: ${res.balance}`);
//
//         res.on('data', d => {
//             process.stdout.write(d)
//         })
//     });
//
//     req.on('error', error => {
//         console.error(error)
//     });
//
//     req.write(data);
//     req.end();
// }
connectToGosbank();
