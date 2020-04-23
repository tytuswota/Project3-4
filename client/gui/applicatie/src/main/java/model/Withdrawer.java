package model;
/*
This class manages the withdraw action.
It connects to the server to do the transaction.
 */
public class Withdrawer {

    // Check if balance is enough  and if banknotes available.
    public boolean canWithdraw(SetOfBanknotes banknotes){
        //TODO check if saldo is enough  and if banknotes available
        return true;
    }

    // withdraw the money, returns false if it fails
    public boolean withdraw(SetOfBanknotes banknotes){
        // TODO add logic for dispenser
        return ConnectionManager.getSession().withdraw(banknotes);
    }
}
