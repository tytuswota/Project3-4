
const DasbankSession = require('./DasbankSession.js');

account = 'SO-BANQ-00000005';
account2 = 'SO-DASB-00000002';
pin =  '1234';
let session = new DasbankSession.DasbankSession(account,pin, () => {
    session.getBalance(
        (e)=>{
            console.log("balance = " + e);
        });
});

/*let session3 = new DasbankSession.DasbankSession(account,pin, () => {
    session.createTransAction(1, account2, account,
        (e)=>{
            console.log("statuscode = " + e);
        });
});*/

