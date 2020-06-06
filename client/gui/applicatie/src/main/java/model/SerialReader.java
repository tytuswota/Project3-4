package model;

/*
 * SerialReader
 *
 * Makes a connection with the arduino.
 * We use the fazecasts' JSerial library.
 *  https://fazecast.github.io/jSerialComm/
 *
 * this class fires events when messages are received so multiple classes can make use of this.
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

import com.fazecast.jSerialComm.*;
import org.json.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SerialReader {

    // An instance of serial reader.
    static SerialReader reader;
    // String with the card number.
    private String lastCardNumber;

    // Sets with subscribers to the event.
    private Set<Consumer<String>> KeyPadListeners = new HashSet();
    private Set<Consumer<String>> RFIDListeners = new HashSet();

    // Test method
    public static void main(String[] args) throws IOException, InterruptedException {
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

    // Method to add a keypad listener.
    public void addKeyPadListener(Consumer<String> listener) {
        KeyPadListeners.add(listener);
    }

    // Method to add a RFID listener.
    public void addRFIDListener(Consumer<String> listener) {
        RFIDListeners.add(listener);
    }

    // Method to remove listeners
    public void removeListeners(){
        KeyPadListeners.clear();
        RFIDListeners.clear();
    }

    // Raises the keyPad event.
    private void RaiseKeyPadEvent(String args) {
        KeyPadListeners.forEach(x -> x.accept(args));
    }

    // Raises the RFID event.
    private void RaiseRFIDEvent(String args) {
        RFIDListeners.forEach(x -> x.accept(args));
    }

    // Singleton to get or create an instance of the SerialReader class.
    public static SerialReader GetReader() {
        if (reader == null){
            reader = new SerialReader();
            reader.init();
        }
        return  reader;
    }

    // Initiates the connection.
    private void init() {
        try {
            // Creating a SerialPort class.
            SerialPort comPort = SerialPort.getCommPorts()[0];
            comPort.openPort();
            // Adding data listeners.
            comPort.addDataListener(new SerialPortDataListener() {

                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                // Override the SerialEvent method to customize them.
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

        // Get the last Card number that has been received.
        public String getLastCardNumber(){
            String cardnumber = lastCardNumber;
            lastCardNumber = null;
            return cardnumber;
        }
}