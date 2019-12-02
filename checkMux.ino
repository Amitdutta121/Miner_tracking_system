#define cInput A0
#define cSelectPin D6
int inputValue;

void setup()  {
  Serial.begin(9600);
  pinMode(cSelectPin, OUTPUT);
}

void loop()
{
  digitalWrite(cSelectPin, LOW);
  inputValue = analogRead(cInput);
  Serial.print(String(inputValue) + "\t");


  digitalWrite(cSelectPin, HIGH);
  inputValue = analogRead(cInput);
  Serial.print(String(inputValue) + "\t");

  Serial.println();

  delay(100);
}