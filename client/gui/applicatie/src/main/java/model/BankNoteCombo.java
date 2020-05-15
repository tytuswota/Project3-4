package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BankNoteCombo {
    private int amount;

    public int[] calBankNoteCombo(int amount){
        this.amount = amount;
        int[] bankNotes = {};
        if(this.amount % 10 == 0 && this.amount <= 100){
            bankNotes[0] = 10;
        }

        if(this.amount % 20 == 0 && this.amount <= 200){
            bankNotes[1] = 20;
        }

        if(this.amount % 50 == 0){
            bankNotes[2] = 50;
        }

        return bankNotes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
