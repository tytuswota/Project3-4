
const DasbankSession = require('./DasbankSession.js');

account = 'SU-DASB-00000001';
account2 = 'SU-DASB-00000002';
pin =  '1234';
let session = new DasbankSession.DasbankSession(account,pin, () => {
    session.getBalance(
        (e)=>{
            console.log("balance = " + e);
        });
});

// transaction failed
let session = new DasbankSession.DasbankSession(account,pin, () => {
    session.createTransAction(1, account2, account,
        (e)=>{
            console.log("statuscode = " + e);
        });
});

