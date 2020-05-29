const http = require('http'); //TODO make https

class DasbankSession {
    _jwtToken = '';
    _account_id = '';
    _account_balance = '';

    constructor(cardId, pincode) {
        this.login(cardId, pincode)
    }

    getBalance(handler) {
        let data = JSON.stringify({"jwt": this._jwtToken, "account_id": this._account_id});
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

    withdraw(handler, account) {
        let data = JSON.stringify({"jwt": this._jwtToken, "causer_account_id": account, "receiver_account_id":this._account_id});
        //console.log(`data to send ${data}`);
        // todo change port and protecol to https
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

        const req = http.request(options, res => {
            console.log(`statusCode: ${res.statusCode}`);

            res.on('data', d => {
                // TODO Add all status codes.
                if (String.compare(d,"withdraw successful") )
                {
                    handler("200");
                }else {
                    handler("400")
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
        let data = JSON.stringify({"card_id": cardId, "pin": pincode});
        // todo change port and protecol to https
        const options = {
            hostname: 'www.dasbank.ml',
            port: 80,
            path: '/api/Login/login.php',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': data.length
            }
        }

        const req = http.request(options, res => {
            console.log(`statusCode: ${res.statusCode}`);

            res.on('data', d => {
                try {
                    data = JSON.parse(d)
                    console.log(data)
                    if (data.data !== null) {
                        this._account_id = data.data.bank_account_id;
                        this._jwtToken = data.jwt;
                    }
                }catch (e) {
                    console.log(e)
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