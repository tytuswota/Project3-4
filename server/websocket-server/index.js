const WebSocket = require('ws');

const wss = new WebSocket.Server({ port: process.env.PORT || 8080 });

const connectedBanks = {};

wss.on('connection', function (ws) {
    let bankCode;

    function responseMessage(id, type, data) {
        if (ws.readyState === WebSocket.OPEN) {
            ws.send(JSON.stringify({ id: id, type: type + '_response', data: data }));
        }
    }

    ws.on('message', function (message) {
        try {
            const { id, type, data } = JSON.parse(message);

            if (
                id === undefined ||
                type === undefined ||
                data === undefined ||
                data.header === undefined ||
                data.header.originCountry === undefined ||
                data.header.originBank === undefined ||
                data.header.receiveCountry === undefined ||
                data.header.receiveBank === undefined ||
                data.body === undefined
            ) {
                responseMessage(id, 'register', {
                    header: {
                        originCountry: 'SU',
                        originBank: 'GOSB',
                        receiveCountry: data.header.originCountry,
                        receiveBank: data.header.originBank
                    },
                    body: {
                        code: 400
                    }
                });
                ws.close();
            }

            else if (type === 'register') {
                if (connectedBanks[data.header.originBank] === undefined) {
                    bankCode = data.header.originBank;
                    connectedBanks[bankCode] = ws;
                    console.log(bankCode + ' registered');

                    ws.on('close', function () {
                        connectedBanks[bankCode] = undefined;
                        console.log(bankCode + ' disconnected');
                    });

                    responseMessage(id, 'register', {
                        header: {
                            originCountry: 'SU',
                            originBank: 'GOSB',
                            receiveCountry: data.header.originCountry,
                            receiveBank: data.header.originBank
                        },
                        body: {
                            code: 200
                        }
                    });
                }

                else {
                    responseMessage(id, 'register', {
                        header: {
                            originCountry: 'SU',
                            originBank: 'GOSB',
                            receiveCountry: data.header.originCountry,
                            receiveBank: data.header.originBank
                        },
                        body: {
                            code: 401
                        }
                    });
                    ws.close();
                }
            }

            else if (bankCode !== undefined) {
                if (data.header.receiveCountry === 'SU') {
                    if (
                        connectedBanks[data.header.receiveBank] !== undefined &&
                        connectedBanks[data.header.receiveBank].readyState === WebSocket.OPEN
                    ) {
                        connectedBanks[data.header.receiveBank].send(message);
                        console.log(data.header.originBank + ' -> ' + data.header.receiveBank + ': ' + type);
                    }

                    else {
                        responseMessage(id, type, {
                            header: {
                                originCountry: 'SU',
                                originBank: 'GOSB',
                                receiveCountry: data.header.originCountry,
                                receiveBank: data.header.originBank
                            },
                            body: {
                                code: 400
                            }
                        });
                    }
                }

                else {
                    // Gosbank supports only Sovjet banks for now!
                    responseMessage(id, type, {
                        header: {
                            originCountry: 'SU',
                            originBank: 'GOSB',
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

        // When a error is created close connection
        catch (exception) {
            console.log(exception);

            responseMessage(id, 'register', {
                header: {
                    originCountry: 'SU',
                    originBank: 'GOSB',
                    receiveCountry: data.header.originCountry,
                    receiveBank: data.header.originBank
                },
                body: {
                    code: 400
                }
            });
            ws.close();
        }
    });
});
