
#include <Wire.h>
#include "Adafruit_Thermal.h"
#include "adalogo.h"
#include "adaqrcode.h"
#include <Arduino_JSON.h>
//char mString[4];
String inByte;



#include "SoftwareSerial.h"
#define TX_PIN 7 
#define RX_PIN 6 


SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial);     // Pass addr to printer constructor

void setup() {
  mySerial.begin(9600);
  Serial.begin(19200);

}

void loop() {
  
   if (Serial.available()) { 
    inByte += (char)Serial.read();
        JSONVar myob = JSON.parse(inByte);
    
       if(myob.hasOwnProperty("start")){ // wanneer er een json met start binnen komt vanuit de GUI

        printer.begin();
        
        printer.inverseOn();
        printer.printBitmap(adalogo_width, adalogo_height, adalogo_data);
        printer.inverseOff();
        
        printer.setLineHeight(50);
        printer.println("transactie:");
        printer.setLineHeight();

        printer.justify('R');
        printer.println("withdraw");

        printer.justify('L');
        printer.setLineHeight(50);
        printer.println("briefgeld:");
        printer.setLineHeight();
        
        printer.justify('R'); 
       
        printer.print("$10 ");
        printer.print((const char*) myob["brief1"]);
        printer.println("x");
        
        printer.setLineHeight(50);
        printer.print("$20 ");
        printer.print((const char*) myob["brief2"]);
        printer.println("x");
        
        printer.setLineHeight(50);
        printer.print("$50 ");
        printer.print((const char*) myob["brief3"]);        
        printer.println("x");
        printer.setLineHeight();
  
        printer.justify('R');
        printer.setLineHeight(30);
        printer.underlineOn();
        printer.println("         ");
        printer.underlineOff();
        printer.setLineHeight();

        printer.justify('L');
        printer.setLineHeight(50);
        printer.println("totaal:");
        printer.setLineHeight();

        printer.justify('R');
        printer.print("$");
        printer.println((const char*) myob["amount"]);
        printer.setLineHeight();

        printer.underlineOn();
        printer.println("                           ");
        printer.underlineOff();

        printer.setLineHeight(50);
        printer.justify('L');
        printer.setLineHeight();
        printer.println("country: Netherlands");
        printer.println("City: Rotterdam");
        printer.println("");
        printer.print("Date: ");
        printer.println((const char*) myob["time"]);

        printer.underlineOn();
        printer.println("                           ");
        printer.underlineOff();

        printer.boldOn();
        printer.justify('C');
        printer.println("Wij wensen u een fijne dag.");
        printer.println("Bedankt en tot ziens.");
        printer.boldOff();

        printer.println("");
        printer.println("");
        printer.println("");
        printer.println("");
        
        printer.sleep();      // Tell printer to sleep
        delay(3000L);         // Sleep for 3 seconds
        printer.wake();       // MUST wake() before printing again, even if reset
        printer.setDefault();
       }
           
   }
   else{
     JSONVar myob = JSON.parse(inByte);
    
    if (JSON.typeof(myob) == "undefined") {
    return;
    
    }
        delay(500);
        
   }
   
}







 
 
  
