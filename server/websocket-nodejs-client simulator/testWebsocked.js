const WebSocket = require('ws');
const LOCAL_DEBUG_MODE = false;
const ws = new WebSocket(LOCAL_DEBUG_MODE ? 'ws://localhost:8080' : 'wss://ws.gosbank.ml/');

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

ws.on('open', function () {
    let toSent = {
        "id": Date.now(),
        "type": "register",
        "data": {
            "header": {
                "originCountry": "SO",
                "originBank": "BANW",
                "receiveCountry": "SO",
                "receiveBank": "GOSB"
            },
            "body": {}
        }
    }

    ws.send(JSON.stringify(toSent));
/*    toSent = {
        "id": 1586944886593,
        "type": "balance",
        "data": {
            "header": {
                "originCountry": "SO",
                "originBank": "BANQ",
                "receiveCountry": "SO",
                "receiveBank": "DASB"
            },
            "body": {
                "account": "SO-BANQ-00000005",
                "pin": "1234"
            }
        }
    }

    ws.send(JSON.stringify(toSent));*/

    toSent = {
        "id": Date.now(),
        "type": "payment",
        "data": {
            "header": {
                "originCountry": "SO",
                "originBank": "BANW",
                "receiveCountry": "SO",
                "receiveBank": "DASB"
            },
            "body": {
                "fromAccount": "SO-DASB-00000002",
                "toAccount": "SO-DASW-00000002",
                "pin": "1234",
                "amount": 4.56
            }
        }
    }

    ws.send(JSON.stringify(toSent));

});

ws.on('message', function(message){
    console.log(message);
});