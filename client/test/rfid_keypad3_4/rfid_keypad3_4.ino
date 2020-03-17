
/*
  RST/Reset   RST          9
  SPI SS      SDA(SS)      10
  SPI MOSI    MOSI         11 / ICSP-4
  SPI MISO    MISO         12 / ICSP-1
  SPI SCK     SCK          13 / ICSP-3

*/
#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

#define SS_PIN 10
#define RST_PIN 2
MFRC522 mfrc522(SS_PIN, RST_PIN);

const int password_length = 4;

String tagHRO = "8A 16 F8 0A";             //Hardcoded tagUID
char correct_pin[password_length] = {'1', '2', '3', '4'}; //Hardcoded password
char passwordOpslag[password_length];
int data_index = 0;

bool rfidMode = true;
bool keyPad = false;
bool pasHRO = true;

int attemptCounter = 0;     //Telt het aantal inlog pogingen

#define led A1

const byte ROWS = 4; //four rows
const byte COLS = 4; //three columnsa
char keys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

// Initializing pins for keypad
byte rowPins[ROWS] = {A0, 8, 7, 6}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {5, 4, 3, 2}; //connect to the column pinouts of the keypad

// Create instance for keypad
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

void setup() {
  Serial.begin(9600);
  mfrc522.PCD_Init();   // Init MFRC522
  mfrc522.PCD_SetAntennaGain(mfrc522.RxGain_max);

  SPI.begin();
  Serial.println("Scan uw pas");

  pinMode(led, OUTPUT);
}

void loop() {

  if (rfidMode == true && pasHRO == true )  //Zoekt naar pas mode
  {
    Serial.println("Scan uw pas");
    // Look for new cards
    if ( ! mfrc522.PICC_IsNewCardPresent()) {
      return;
    }
    // Select one of the cards
    if ( ! mfrc522.PICC_ReadCardSerial()) {
      return;
    }

    //Reading from the card
    String tag = "";
    for (byte j = 0; j < mfrc522.uid.size; j++)
    {
      tag.concat(String(mfrc522.uid.uidByte[j] < 0x10 ? " 0" : " "));
      tag.concat(String(mfrc522.uid.uidByte[j], HEX));
    }
    tag.toUpperCase();
    //Checking the card
    if (tag.substring(1) == tagHRO ) //Wanneer de HRO pas gelezen wordt
    {
      Serial.println("Toets uw pincode in:");
      rfidMode = false;
      keyPad = true;
    }
    else
    {
      Serial.println("De ingevoerde pas is onbekend");  //Wanneer een verkeerde pas gelezen wordt.
      rfidMode = true;
      delay(300);
      Serial.println("Scan uw pas");
    }
  }


  // If RFID mode is false, it will look for keys from keypad
  while (rfidMode == false && keyPad == true) {
    char key = keypad.getKey(); // Storing keys

    if (key) {
      Serial.println(key);
    }

    if ((key >= '0' && key <= '9')) {
      if (data_index <= password_length) {
        {
          passwordOpslag[data_index] = key;
          data_index++;
        }
      } else
      {
        Serial.println("Pincode is te lang");
      }
    } else if (key == '#')
    {
      delay(200);
      if (cmpArray()) // If password is matched
      {
        if ( pasHRO == true)
        {
          Serial.println("Pass Accepted");
          keyPad = false;
          digitalWrite(led, HIGH);
          delay(250);
          clearData();
          break;
        }
      }  else    // If password is not matched
      {
        Serial.println("Wrong Password");
        clearData();
        attemptCounter++;  //Telt ééntje bij de attemptcounter
        Serial.println( attemptCounter);

      }
    } else if (key == '*') {
      backspace();
    }

    if (attemptCounter == 3)
    {
      rfidMode = true;
      pasHRO = false;
      keyPad = false;
      Serial.println("je pas is geblokkeerd");
      delay(500);
      Serial.println("Neem uw pas uit");
      clearData();
      //      break;
    }

    if (pasHRO == false)
    {
      Serial.println("Uw pas is geblokkeerd");
    }
  }
}

bool cmpArray()
{
  for (int i = 0; i < password_length; i++)
  {
    if (passwordOpslag[i] != correct_pin[i])
    {
      return false;
    }
  }
  
  return true;
}

void clearData()
{
  // Serial.println("arraaaaaaaay");
  //  for (int j = 0; j < 5; j++ ) {
  //    Serial.println(passwordOpslag[j]);
  //  }

  for (int i = 0; i < 5; i++) {
    data_index = 0;
    passwordOpslag[i] = 0;
  }
  return;
}

void backspace()
{
  data_index--;
  return;
}
