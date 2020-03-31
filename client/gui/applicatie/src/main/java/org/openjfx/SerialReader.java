package org.openjfx;

/*
 *   https://fazecast.github.io/jSerialComm/
 */

import com.fazecast.jSerialComm.*;
import org.json.*;

public class SerialReader {

    public static void main(String[] args) throws InterruptedException {
        SerialReader reader = new SerialReader();
        while(true){
            Thread.sleep(1000);
        }
    }

    public SerialReader (){
        StartListerning();
    }

    private void StartListerning(){
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(newData, newData.length);
                System.out.println("Read " + numRead + " bytes.");
                System.out.println(new String(newData));
                JSONObject json = new JSONObject(new String(newData));
                if( json.has("keypress")){
                    KeyPressEvent(json.getString("keypress"));
                }
            }
        });
    }

    protected void KeyPressEvent(String key){
        System.out.print("key " + key);
    }
}