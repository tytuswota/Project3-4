package model;

/*
 *   https://fazecast.github.io/jSerialComm/
 */

import com.fazecast.jSerialComm.*;
import org.json.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SerialReader {

    static SerialReader reader;
    private String lastCardNumber;
    private Set<Consumer<String>> KeyPadListeners = new HashSet();
    private Set<Consumer<String>> RFIDListeners = new HashSet();

    // Test method
    public static void main(String[] args) throws InterruptedException {
        SerialReader reader = SerialReader.GetReader();
        reader.addKeyPadListener((x) -> System.out.println("key " + x));
        reader.addRFIDListener((x) -> System.out.println("rfid " + x));
        SerialReader r = SerialReader.GetReader();
        r.addKeyPadListener((x) -> System.out.println("other " + x));
        r.addRFIDListener((x) -> System.out.println("other " + x));
        while (true) {
            Thread.sleep(1000);
        }
    }

    public void addKeyPadListener(Consumer<String> listener) {
        KeyPadListeners.add(listener);
    }

    public void addRFIDListener(Consumer<String> listener) {
        RFIDListeners.add(listener);
    }

    public void removeListeners(){
        KeyPadListeners.clear();
        RFIDListeners.clear();
    }

    private void RaiseKeyPadEvent(String args) {
        KeyPadListeners.forEach(x -> x.accept(args));
    }

    private void RaiseRFIDEvent(String args) {
        RFIDListeners.forEach(x -> x.accept(args));
    }

    public static SerialReader GetReader() {
        if (reader == null){
            reader = new SerialReader();
            reader.init();
        }
        return  reader;
    }

    private void init() {
        try {
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

                    if (json.has("keypress")) {
                        RaiseKeyPadEvent((json.getString("keypress")));
                    }

                    if (json.has("rfid")) {
                        lastCardNumber = json.getString("rfid");
                        RaiseRFIDEvent(lastCardNumber);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("error while initialising SerialReader");
        }
    }

        public String getLastCardNumber(){
            String cardnumber = lastCardNumber;
            lastCardNumber = null;
            return cardnumber;

        }
}