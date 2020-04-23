package model;
/*
Represents a set of banknotes
 */
public class SetOfBanknotes {

    private int tens;
    private int twenties;
    private int fifties;

    public SetOfBanknotes(int tens, int twenties, int fifties){
        this.tens = tens;
        this.twenties = twenties;
        this.fifties = fifties;
    }

    public int getFifties() {
        return fifties;
    }

    public void setFifties(int fifties) {
        this.fifties = fifties;
    }

    public int getTwenties() {
        return twenties;
    }

    public void setTwenties(int twenties) {
        this.twenties = twenties;
    }

    public int getTens() {
        return tens;
    }

    public void setTens(int tens) {
        this.tens = tens;
    }

    public int getTotalAmount(){
        return tens * 10 + twenties * 20 + fifties * 50;
    }
}
