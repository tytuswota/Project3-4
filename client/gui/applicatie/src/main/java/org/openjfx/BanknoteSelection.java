package org.openjfx;

import javafx.fxml.FXML;

public class BanknoteSelection {
    public static int[][] banknoteArray = null;
    @FXML
    public void initialize() {

        System.out.println("======================================================");

        for(int combo = 0; combo < 20; combo++){
            for(int bill = 0; bill < 3; bill++){
                if(banknoteArray[bill][combo] != 0)
                {
                    System.out.println("bill:" + bill);
                    System.out.println("combo: " + combo);
                    System.out.println("==the value");
                    System.out.println(banknoteArray[bill][combo]);
                    System.out.println("==the value");
                }
            }
        }
        System.out.println("======================================================");
    }

}
