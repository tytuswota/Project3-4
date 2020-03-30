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
byte colPins[COLS] = {A0, 8, 7, 6}; //connect to the column pinouts of the keypad

char password[password_length] = "1234"; // hard-coded pincode
char passdata[password_length] ; // de array waar de invoer in weggezet wordt
byte data_index = 0, pass_index = 0; // bijhouders voor de invoer


String tagHRO = "96 67 C1 32";
// instantie maken van Keypad object
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );


bool rfidMode = true;
bool pasHRO = false;

int ledGroen = 6;
int ledRood = 7;

void setup()
{

  Serial.begin(9600);   // Initiate a serial communication
  SPI.begin();          // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Put your card to the reader...");
  Serial.println();

  pinMode(ledRood, OUTPUT);
  pinMode(ledGroen, OUTPUT);

}

//--------------------------MAIN LOOP -----------------------
void loop()
{
  char key = keypad.getKey();

  //----------------------------------RFID PART---------------------------


  if (rfidMode == true)  //Zoekt naar pas mode
  {
    Serial.println("Zoeken naar pas");
    digitalWrite(ledRood, LOW);
    digitalWrite(ledGroen, LOW);
  }

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
  if (tag.substring(1) == tag) //Wanneer de HRO pas gelezen wordt
  {
    Serial.write("Toets uw HRO pincode in:");
    rfidMode = false;
    pasHRO = true;

  } else
  {
    Serial.write("Verkeerde pas");  //Wanneer een verkeerde pas gelezen wordt.
    rfidMode = true;
    pasHRO = false;
  }
  //==========================================================================

  while (rfidMode == false) {

    Serial.println("Toets uw pincode in: ");
    if (key) {
      Serial.println(key);

      if (key != '#' )
      {
        Serial.println();
        if (data_index <= password_length)
        {
          passdata[data_index] = key; // bewaar ingevoerde karakter
          data_index++;

          counter = 0;
        }
        else
        {
          Serial.println("Data entry exceeds password length");// error: "Data entry exceeds password length"
          clearData(); // ingevoerde code verwijderen uit array
        }
      } else if (key == '#')
      {
        if (!strcmp(passdata, password) ) // er is geen plek waar ze verschillend zijn
        { if ( pasHRO == true)
          {
            Serial.print("Pass Accepted");
            digitalWrite(ledRood, HIGH);
            counter = 0;
            break;
          }
        }
      }
      else  // password nietw goed ingetypt
      {
        counter++;
        Serial.println("Wrong Password"); // error: “Wrong Password”
        Serial.println("-------------------------");
        clearData(); // ingevoerde code verwijderen uit array
      }

      if (key == 'A')
      {
        rfidMode = true;
        clearData(); // ingevoerde code verwijderen uit array
        Serial.println("Pin process afgebroken");
      }
    }
    else
    {
      Serial.println("Something went wrong keypad !");// error: "Something went wrong!"
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
