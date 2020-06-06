package org.openjfx;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.json.JSONObject;

import java.io.IOException;

/**
 *
 * BonController
 *
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class BonController extends BaseController {

    @FXML
    Button noReceipt;

    @FXML
    Button getReceipt;


    @Override
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                printReceipt();

            } else if (key == '*') {
                switchToSimulatie();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void printReceipt() throws IOException {
        Thread receipt = new Thread();

        boolean change = false;
        while (!change) {
            try {
                SerialPort comPort = SerialPort.getCommPort("COM4");
                comPort.setComPortParameters(19200, 8, 1, 0);
                comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

                if(comPort.openPort()){
                    System.out.println("port is open");
                }
                else {
                    System.out.println("port is not open");
                }

                //comPort.openPort();


                JSONObject jsonObject = new JSONObject();
                jsonObject.put("start", "");
                jsonObject.put("amount", BaseController.getTBedrag());
                jsonObject.put("brief1", BanknoteSelection.getBankN1());
                jsonObject.put("brief2", BanknoteSelection.getBankN2());
                jsonObject.put("brief3", BanknoteSelection.getBankN3());
                jsonObject.put("time", BaseController.getDateString());

                System.out.println(BaseController.getDateString());

                for (int i = 0; i < 2; i++) {
                    byte[] stringByteArray = jsonObject.toString().getBytes();
                    String string = new String(stringByteArray);
                    System.out.println(string);
                    comPort.writeBytes(stringByteArray, stringByteArray.length);
                    receipt.sleep(2000);
                }
                comPort.closePort();


                switchToSimulatie();
                change = true;

            } catch (NullPointerException | InterruptedException e) {
                e.printStackTrace();
            }

        }
        receipt.start();
    }


}
