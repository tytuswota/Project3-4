package org.openjfx;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.json.JSONObject;

import java.io.IOException;

public class BonController extends BaseController {

    @FXML
    Button noReceipt;

    @FXML
    Button getReceipt;


    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("simulatie");
    }

    @Override
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                SerialPort comPort= SerialPort.getCommPort("COM3");
                comPort.setComPortParameters(19200,8,1,0);
                comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING,0,0);
                comPort.openPort();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("amount", BaseController.getTotBedrag());
                    for(int i=0; i< 3; i++) {
                        byte[] stringByteArray = jsonObject.toString().getBytes();
                        String string = new String(stringByteArray);
                        System.out.println(string);
                        comPort.writeBytes(stringByteArray, stringByteArray.length);
                        Thread.sleep(2000);
                    }

                //todo: logic bonprinter;
                switchToPasUit();


            }else if(key == '*'){
                switchToPasUit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
