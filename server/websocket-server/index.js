// The land node country code
const COUNTRY_CODE = 'SO';

// The land node bank code
const BANK_CODE = 'GOSB';

// The noob websocket address
const NOOB_ADDRESS = 'ws://145.24.222.206:8085';

// When disconnect try to reconnect timeout (in ms)
const RECONNECT_TIMEOUT = 2 * 1000;

// The error code messages
const codeMessages = {
    '200': 'Success',
    '400': 'Broken message',
    '401': 'Authentication failed / pincode false',
    '402': 'Bank card had not enough balance',
    '403': 'Bank card is blocked',
    '404': 'Something don\'t exists'
};

// Load the websocket library
const WebSocket = require('ws');

// Load the socket io client library
const io = require('socket.io-client');

// Create a new websocket server at port 8080
const wss = new WebSocket.Server({ port: process.env.PORT || 8080 });

// Function that parses account parts
function parseAccountParts(account) {
    const parts = account.split('-');
    return {
        country: parts[0],
        bank: parts[1],
        account: parseInt(parts[2])
    };
}

// Bank connection holder
const connectedBanks = {};

/*// Connect to the NOOB
const noob = io(NOOB_ADDRESS);

// Register with the land code
noob.emit('register', {
    header: {
        country: COUNTRY_CODE
    }
});
noob.once('response', function (response) {
    if (response.code == 201) {
        console.log('Connected to the NOOB');
    } else {
        console.log('Can\'t connect to the NOOB: ', response.message);
        noob.disconnect();
    }
});

// On a balance request
noob.on('balance', function (data) {
    // When bank is connected send through
    if (
        connectedBanks[data.header.receiveBank] !== undefined &&
        connectedBanks[data.header.receiveBank].readyState === WebSocket.OPEN
    ) {
        console.log(data.header.originCountry + '-' + data.header.originBank + ' -> ' +
            data.header.receiveCountry + '-' + data.header.receiveBank + ': balance ' + JSON.stringify(data.body));

        console.log('Sending to ' + data.header.receiveBank + ' client...');

        connectedBanks[data.header.receiveBank].send(JSON.stringify({
            id: Date.now(),
            type: 'balance',
            data: {
                header: data.header,
                body: {
                    account: data.header.receiveCountry + '-' + data.header.receiveBank + '-' + String(parseInt(data.body.account)).padStart(8, '0'),
                    pin: data.body.pin
                }
            }
        }));
    }
});

// On a withdraw request
noob.on('withdraw', function (data) {
    // When bank is connected send through
    if (
        connectedBanks[data.header.receiveBank] !== undefined &&
        connectedBanks[data.header.receiveBank].readyState === WebSocket.OPEN
    ) {
        console.log(data.header.originCountry + '-' + data.header.originBank + ' -> ' +
            data.header.receiveCountry + '-' + data.header.receiveBank + ': payment ' + JSON.stringify(data.body));

        console.log('Sending to ' + data.header.receiveBank + ' client...');

        connectedBanks[data.header.receiveBank].send(JSON.stringify({
            id: Date.now(),
            type: 'payment',
            data: {
                header: data.header,
                body: {
                    fromAccount: data.header.receiveCountry + '-' + data.header.receiveBank + '-' + String(parseInt(data.body.account)).padStart(8, '0'),
                    toAccount: data.header.originCountry + '-' + data.header.originBank + '-00000001',
                    pin: data.body.pin,
                    amount: data.body.amount
                }
            }
        }));
    }
});

// NOOB connection error handler
noob.on('error', function (error) {
    console.log('Error with NOOB connection:', error);
});

// NOOB disconnect handler
noob.on('disconnect', function () {
    console.log('Disconnected from NOOB, try to reconnect in ' + (RECONNECT_TIMEOUT / 1000).toFixed(0) + ' seconds!');
    setTimeout(function () {
        noob.open();
    }, RECONNECT_TIMEOUT);
});
*/
// On client connection listener
wss.on('connection', function (ws) {
    // Bank code holder
    let bankCode;

    // Function witch sends a message back
    function responseMessage(id, type, data) {
        if (ws.readyState === WebSocket.OPEN) {
            console.log(data.header.originCountry + '-' + data.header.originBank + ' -> ' +
                data.header.receiveCountry + '-' + data.header.receiveBank + ': ' + type + '_response ' + JSON.stringify(data.body));

            console.log('Sending to ' + data.header.receiveBank + ' client...');

            ws.send(JSON.stringify({ id: id, type: type + '_response', data: data }));
        }
    }

    // On message listener
    ws.on('message', function (message) {
        try {
            // Parse the message
            const { id, type, data } = JSON.parse(message);

            console.log(data.header.originCountry + '-' + data.header.originBank + ' -> ' +
                data.header.receiveCountry + '-' + data.header.receiveBank + ': ' + type + ' ' + JSON.stringify(data.body));

            // On register message
            if (type === 'register') {
                // Check if country the right one
                // And the bank is not already connected
                if (
                    data.header.originCountry === COUNTRY_CODE &&
                    connectedBanks[data.header.originBank] === undefined
                ) {
                    // Register bank
                    bankCode = data.header.originBank;
                    connectedBanks[bankCode] = ws;
                    console.log(bankCode + ' registered');

                    // Register on connection close listener
                    ws.on('close', function () {
                        connectedBanks[bankCode] = undefined;
                        console.log(bankCode + ' disconnected');
                    });

                    // Send sucess message response
                    responseMessage(id, 'register', {
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
                }

                else {
                    // Send auth failt message response
                    responseMessage(id, 'register', {
                        header: {
                            originCountry: COUNTRY_CODE,
                            originBank: BANK_CODE,
                            receiveCountry: data.header.originCountry,
                            receiveBank: data.header.originBank
                        },
                        body: {
                            code: 401
                        }
                    });

                    // Close the connection
                    ws.close();
                }
            }

            // Else check if the bank is connected
            else if (bankCode !== undefined) {
                // When the message is to another sovjet bank
                if (data.header.receiveCountry === COUNTRY_CODE) {
                    // When the bank is connected send message trough
                    if (
                        connectedBanks[data.header.receiveBank] !== undefined &&
                        connectedBanks[data.header.receiveBank].readyState === WebSocket.OPEN
                    ) {
                        console.log('Sending to ' + data.header.receiveBank + ' client...');
                        connectedBanks[data.header.receiveBank].send(message);
                    }

                    // Or send broken message response
                    else {
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
                        });
                    }
                }

                // When message for foreign bank send to gosbank
                else {
                    // Check if connected to NOOB
                    if (noob.connected) {
                        // When balance message
                        if (type === 'balance') {
                            // Send to NOOB and send the response back
                            console.log('Sending to NOOB...');
                            noob.emit('balance', {
                                header: data.header,
                                body: {
                                    account: String(parseAccountParts(data.body.account).account).padStart(8, '0'),
                                    pin: data.body.pin
                                }
                            });
                            noob.once('response', function (response) {
                                if (response.header.action === 'balance') {
                                    console.log('Response from NOOB: ' + JSON.stringify(response.body));
                                    responseMessage(id, 'balance', response);
                                } else {
                                    console.log('NOOB response action is not balance');
                                }
                            });
                        }

                        // When balance response message
                        if (type === 'balance_response') {
                            data.header.action = 'balance';
                            data.body.message = codeMessages[data.body.code];
                            console.log('Sending to NOOB...');
                            noob.emit('response', data);
                        }

                        // When payment message
                        if (type === 'payment') {
                            // Check to account is 1
                            if (parseAccountParts(data.body.toAccount).account === 1) {
                                // Send to NOOB and send the response back
                                console.log('Sending to NOOB...');
                                noob.emit('withdraw', {
                                    header: data.header,
                                    body: {
                                        account: String(parseAccountParts(data.body.fromAccount).account).padStart(8, '0'),
                                        pin: data.body.pin,
                                        amount: data.body.amount
                                    }
                                });
                                noob.once('response', function (response) {
                                    if (response.header.action === 'withdraw') {
                                        console.log('Response from NOOB: ' + JSON.stringify(response.body));
                                        responseMessage(id, 'payment', response);
                                    } else {
                                        console.log('NOOB response action is not withdraw');
                                    }
                                });
                            }

                            // Send broken message
                            else {
                                responseMessage(id, 'payment', {
                                    header: {
                                        originCountry: COUNTRY_CODE,
                                        originBank: BANK_CODE,
                                        receiveCountry: data.header.originCountry,
                                        receiveBank: data.header.originBank
                                    },
                                    body: {
                                        code: 400
                                    }
                                });
                            }
                        }

                        // When payment response message
                        if (type === 'payment_response') {
                            data.header.action = 'withdraw';
                            data.body.message = codeMessages[data.body.code];
                            console.log('Sending to NOOB...');
                            noob.emit('response', data);
                        }
                    }

                    // Send broken message
                    else {
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
                        });
                    }
                }
            }
        }

            // When a error is thrown
        catch (exception) {
            // Log the exception
            console.log('Error with client', exception);

            // Send broken message back
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
            });

            // Close connection
            ws.close();
        }
    });
});