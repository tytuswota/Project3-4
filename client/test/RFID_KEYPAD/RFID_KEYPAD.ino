#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

#define password_length 5 // 4 cijfers en een NULL voor keypad
#define SS_PIN 10
#define RST_PIN 2
#define ACCESS_DELAY 2000
#define DENIED_DELAY 1000
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.

int counter = 0;

const byte ROWS = 4; //four rows
const byte COLS = 4; //three columnsa
char keys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

byte rowPins[ROWS] = {5, 4, 3, 2}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {1, 0, 7, 6}; //connect to the column pinouts of the keypad

char passwordHRO[password_length] = "4321"; // hard-coded pincode
char passdata[password_length] ; // de array waar de invoer in weggezet wordt
byte data_index = 0, pass_index = 0; // bijhouders voor de invoer
// instantie maken van Keypad object
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );
boolean foundCard = false;
boolean foundCardFirst = false;
void setup()
{

  Serial.begin(9600);   // Initiate a serial communication
  SPI.begin();          // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Put your card to the reader...");
  Serial.println();

}

//--------------------------MAIN LOOP -----------------------
void loop()
{
  char key = keypad.getKey();

  //----------------------------------RFID PART---------------------------
  // Look for new cards
  if (mfrc522.PICC_IsNewCardPresent())
  {
    Serial.println("################");
    foundCardFirst = true;
  }
  if (!mfrc522.PICC_IsNewCardPresent())
  {
    Serial.println("==============");
    foundCard = false;
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial())
  {
    return;
  }

  if(foundCardFirst && !foundCard){
    foundCardFirst = false;
    foundCard = true;
    //Show UID on serial monitor
    Serial.print("UID tag :");
    String content = "";
    byte letter;
    for (byte i = 0; i < mfrc522.uid.size; i++)
    {
      Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
      Serial.print(mfrc522.uid.uidByte[i], HEX);
      content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
      content.concat(String(mfrc522.uid.uidByte[i], HEX));
    }
    Serial.println();
    Serial.print("Message : ");
    content.toUpperCase();
    if (content.substring(1) == "CA 55 E4 0B") //change here the UID of the card.
    {
      Serial.println("In the if ");
    }
  }
  
}
  // clearData functie haalt alle characters uit de passdata array en zet data_index weer op 0
  void clearData()
  {
    while (data_index != 0)
    {
      passdata[data_index--] = 0;
    }
    return;
  }
