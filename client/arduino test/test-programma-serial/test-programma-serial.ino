void setup() {
  Serial.begin(9600);
}

void loop() {
  Serial.println("{\"keypress\":\"1\"}");
  delay(1000);
}
