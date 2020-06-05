const http = require('http'); //TODO make https

class DasbankSession {
    _jwtToken = '';
    _account_id = '';
    _account_balance = '';

    constructor(cardId, pincode, handler) {
        this.login(cardId, pincode, handler)
    }

    createTransAction(amount,toAccount,fromAccount,pin,handler){

        let data = JSON.stringify(
            {"jwt": this._jwtToken, "receiver_account_id":toAccount, "causer_account_id":fromAccount,"amount":amount,"pin":pin}
            );

        const options = {
            hostname: 'dasbank.ml',
                port: 80,
                path: '/api/TransActions/withdraw.php',
                method: 'POST',
                headers: {
                'Content-Type': 'application/json',
                    'Content-Length': data.length
            }
        }

        console.log("data to sent in createTransaction" + data);

        const req = http.request(options, function (res) {

            console.log(`statusCode http createTransaction: ${res.statusCode}`);

            let response = '';
            res.on('data', data => {
                response += data;
            });

            res.on('end', function () {
                console.log("create transaction on end" + response);
                if (response === "withdraw successful") {
                    handler(200);
                } else {
                    handler(400);
                }
            });

            /*res.on('error', error => {
                console.error(error)
            });*/

        });


        req.write(data);
        req.end();
    }

    getBalance(pin,handler) {
        let data = JSON.stringify({"jwt": this._jwtToken, "account_id": this._account_id, "pin":pin});
        //console.log(`data to send ${data}`);
        // todo change port and protecol to https
        const options = {
            hostname: 'dasbank.ml',
            port: 80,
            path: '/api/BankAccount/read.php',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': data.length
            }
        }

        const req = http.request(options, res => {
            console.log(`statusCode http getbalance: ${res.statusCode}`);

            res.on('data', d => {
                data = JSON.parse(d)
                //console.log(data)
                if (data !== null) {
                    let balance = parseFloat(data.account_balance);
                    handler(balance);
                }
            });
        });

        req.on('error', error => {
            console.error("error in get balance " + error)
        });

        req.write(data);
        req.end();
    }

    login(cardId, pincode, handler) {
        console.log("===in the start login function");
/*        console.log(cardId);
        console.log(pincode);*/
        let data = JSON.stringify({"card_id": cardId, "pin": pincode});
        console.log("login request " + data);

        // todo change port and protecol to https
        const options = {
            hostname: 'dasbank.ml',
            port: 80,
            path: '/api/Login/login.php',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': data.length
            }
        }

        const req = http.request(options, res => {
            console.log("===in the login function");
            console.log(`statusCode http login: ${res.statusCode}`);

            res.on('data', d => {
                try {
                    console.log("in data" + d.toString())
                    if(d.toString() !== "wrong pin") {
                        data = JSON.parse(d)
                        if (data.data !== null) {
                            this._account_id = data.data.bank_account_id;
                            this._jwtToken = data.jwt;
                            handler('200');
                        }
                    }else {
                        handler('400');
                    }
                }catch (e) {
                    console.log( "error login " +e)
                    handler('400');
                }
            })
        });

        req.on('error', error => {
            console.error("error login "+ error)
        });

        req.write(data);
        req.end();
    }
}



module.exports = {
    DasbankSession
};