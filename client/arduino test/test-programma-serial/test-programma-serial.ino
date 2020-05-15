#include <Keypad.h>
/**
 * testprogram for the keypad communication.
 * 
 */
const byte ROWS = 4; //four rows
const byte COLS = 4; //three columnsa
char keys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

// Initializing pins for keypad
byte rowPins[ROWS] = {2, 3, 4, 5}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {6, 7, 8, A0};  //connect to the column pinouts of the keypad

// Create instance for keypad
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );


void setup() {
  Serial.begin(9600);
}

void loop() {
  char key = keypad.getKey();
  if(key){// sent only when a key is present
      Serial.print("{\"keypress\":\"");
      Serial.print(key);
      Serial.println("\"}");
  }
}
