const http = require('http'); //TODO make https

class DasbankSession {
    _jwtToken = '';
    _account_id = '';
    _account_balance = '';

    constructor(cardId, pincode) {
        this.login(cardId, pincode)
    }

    createTransAction(amount,toAccount,fromAccount,handler){

        let data = JSON.stringify(
            {"jwt": this._jwtToken, "receiver_account_id":toAccount, "causer_account_id":fromAccount,"amount":amount}
            );

        const options = {
            hostname: '192.168.33.10',
            port: 80,
            path: '/api/TransActions/withdraw.php',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': data.length
            }
        }

        console.log(data);

        const req = http.request(options, res => {

            res.on('data', d => {
                /*data = JSON.parse(d)
                //console.log(data)
                if (data !== null) {
                    let reponse = data.account_balance;
                    handler(reponse);
                }*/
            })
        });

        req.on('error', error => {
            console.error(error)
        });

        req.write(data);
        req.end();
    }
    getBalance(handler) {
        let data = JSON.stringify({"jwt": this._jwtToken, "account_id": this._account_id});
        //console.log(`data to send ${data}`);
        // todo change port and protecol to https
        const options = {
            hostname: '192.168.33.10',
            port: 80,
            path: '/api/BankAccount/read.php',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': data.length
            }
        }

        const req = http.request(options, res => {
            //console.log(`statusCode: ${res.statusCode}`);

            res.on('data', d => {
                data = JSON.parse(d)
                //console.log(data)
                if (data !== null) {
                    let balance = data.account_balance;
                    handler(balance);
                }
            });
        });

        req.on('error', error => {
            console.error(error)
        });

        req.write(data);
        req.end();
    }

    login(cardId, pincode) {
        console.log("===in the start login function");
        console.log(cardId);
        console.log(pincode);
        let data = JSON.stringify({"card_id": cardId, "pin": pincode});
        // todo change port and protecol to https
        const options = {
            hostname: '192.168.33.10',
            port: 80,
            path: '/api/Login/login.php',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': data.length
            }
        }

        const req = http.request(options, res => {
            //console.log(`statusCode: ${res.statusCode}`);
            console.log("===in the login function");
            res.on('data', d => {
                data = JSON.parse(d)
                //console.log(data)
                if (data.data !== null) {
                    this._account_id = data.data.bank_account_id;
                    this._jwtToken = data.jwt;
                }
            })
        });

        req.on('error', error => {
            console.error(error)
        });

        req.write(data);
        req.end();
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

module.exports = {
    DasbankSession
};