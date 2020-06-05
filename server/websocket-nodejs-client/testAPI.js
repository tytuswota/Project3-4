
const DasbankSession = require('./DasbankSession.js');

account = 'SO-BANW-00000005';
account2 = 'SO-DASB-00000002';
pin =  '1234';
/*let session = new DasbankSession.DasbankSession(account,pin, () => {
    session.getBalance(
        (e)=>{
            console.log("balance = " + e);
        });
});*/


let session3 = new DasbankSession.DasbankSession(account2,pin, () => {
    session3.createTransAction(1, account2, account, pin,
        (e)=>{
            console.log("statuscode = " + e);
        });
});

