package model;

/*
This class manages the withdraw action.
It connects to the server to do the transaction.
 */
public class Withdrawer {

    // Check if balance is enough  and if banknotes available.
    public boolean banknotesAvailable(SetOfBanknotes banknotes){
        try {
        // TODO check if banknotes available
        return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // withdraw the money, returns false if it fails
    public boolean withdraw(SetOfBanknotes banknotes){
        try {
        // TODO add logic for dispenser
        return SessionManager.getSession().withdraw(banknotes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBalanceEnough(int amount){
        try{
            return Double.parseDouble(SessionManager.getSession().getBalance()) >= (double)amount;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
