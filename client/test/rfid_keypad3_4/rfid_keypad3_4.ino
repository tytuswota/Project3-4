#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

#define SS_PIN 10
#define RST_PIN 2
MFRC522 mfrc522(SS_PIN, RST_PIN);

#define password_length 5


String tagHRO = "96 67 C1 32";             //Hardcoded tagUID
char passwordHRO[password_length] = {'1', '2', '3', '4'}; //Hardcoded password
char passwordOpslag[password_length];
byte data_index = 0;



int ledGroen = 6;
int ledRood = 7;

bool rfidMode = true;
bool pasHRO = false;
bool loginHRO = false;


int attemptCounter = 0;     //Telt het aantal inlog pogingen

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
  // put your setup code here, to run once:
  mfrc522.PCD_Init();   // Init MFRC522
  Serial.begin(9600);
  SPI.begin();
  pinMode(ledRood, OUTPUT);
  pinMode(ledGroen, OUTPUT);
}

void loop() {

  if (rfidMode == true)  //Zoekt naar pas mode
  {
    Serial.println("Zoeken naar pas");
    digitalWrite(ledRood, LOW);
    digitalWrite(ledGroen, LOW);

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
      pasHRO = true;
    }
    else
    {
      Serial.println("De ingevoerde pas is onbekend");  //Wanneer een verkeerde pas gelezen wordt.
      rfidMode = true;
      pasHRO = false;
    }
  }

  // If RFID mode is false, it will look for keys from keypad
  while (rfidMode == false) {
    char key = keypad.getKey(); // Storing keys
    if (key)
    {
      Serial.println(key);
      passwordOpslag[data_index] = key; // Storing in password variable wat hier in komt is wat hij later vegerlijkt met jouw ingebakken password.
      data_index++;
    }
    if (  4) // If 4 keys are completed
    {
      delay(200);
      if (!(strncmp(passwordOpslag, passwordHRO, 4))) // If password is matched
      {
        if ( pasHRO == true)
        {
          Serial.println("Pass Accepted");
          digitalWrite(ledRood, HIGH);
          break;
        }
        else    // If password is not matched
        {
          Serial.println("Wrong Password");
          attemptCounter++;  //Telt ééntje bij de attemptcounter
        }
        if (attemptCounter == 3)
        {
          rfidMode = true;
          pasHRO = false;
          Serial.println("je pas is geblokkeerd");
        }
      }
    }
  }
}

void clearData()
{
  while (data_index != 0)
  {
    passwordOpslag[data_index--] = 0;
  }
  return;
}
